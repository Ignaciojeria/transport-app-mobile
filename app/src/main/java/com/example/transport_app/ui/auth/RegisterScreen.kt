package com.example.transport_app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transport_app.domain.RegisterRequest
import com.example.transport_app.ui.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onRegisterSuccess: () -> Unit = {}
) {
    val registerResponse by viewModel.registerResponse.collectAsState()
    val registeredEmail by viewModel.registeredEmail.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    // Form states
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Error states
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    // Visibility states
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(registerResponse) {
        registerResponse?.let { response ->
            isLoading = false
            if (!response.message.startsWith("Error:", ignoreCase = true)) {
                println("Email registrado almacenado: $registeredEmail")
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Registro exitoso")
                }
                onRegisterSuccess()
            } else {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(response.message)
                }
            }
        }
    }

    Text(
        text = "Regístrate con tu usuario y contraseña o ingresa utilizando tu cuenta de Google",
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Email field - Removemos supportingText y usamos solo isError para visual
    OutlinedTextField(
        value = email,
        onValueChange = { email = it; emailError = null },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        isError = emailError != null,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF5138EE),
            errorBorderColor = Color.Red
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Password field
    OutlinedTextField(
        value = password,
        onValueChange = { password = it; passwordError = null },
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = passwordError != null,
        trailingIcon = {
            TextButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = if (passwordVisible) "Ocultar" else "Mostrar",
                    fontSize = 11.sp,
                    color = Color(0xFF5138EE)
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF5138EE),
            errorBorderColor = Color.Red
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Confirm Password field
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it; confirmPasswordError = null },
        label = { Text("Confirmar Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = confirmPasswordError != null,
        trailingIcon = {
            TextButton(
                onClick = { confirmPasswordVisible = !confirmPasswordVisible },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = if (confirmPasswordVisible) "Ocultar" else "Mostrar",
                    fontSize = 11.sp,
                    color = Color(0xFF5138EE)
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF5138EE),
            errorBorderColor = Color.Red
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Register button
    Button(
        onClick = {
            val isValid = validateFields(
                email, password, confirmPassword,
                onEmailError = { emailError = it },
                onPasswordError = { passwordError = it },
                onConfirmPasswordError = { confirmPasswordError = it }
            )

            if (isValid) {
                isLoading = true
                viewModel.register(RegisterRequest(email, password))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5138EE)),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White
            )
        } else {
            Text("Crear Cuenta", fontSize = 18.sp)
        }
    }

    // Mostramos los errores en un lugar separado para no afectar el espaciado
    if (emailError != null || passwordError != null || confirmPasswordError != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            emailError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
            passwordError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
            confirmPasswordError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
        }
    }

    // Agregamos el SnackbarHost al final para mostrar mensajes
    SnackbarHost(hostState = snackbarHostState)
}

private fun validateFields(
    email: String,
    password: String,
    confirmPassword: String,
    onEmailError: (String) -> Unit,
    onPasswordError: (String) -> Unit,
    onConfirmPasswordError: (String) -> Unit
): Boolean {
    var isValid = true

    // Validación de Email
    if (email.isBlank()) {
        onEmailError("El email es requerido")
        isValid = false
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        onEmailError("Email inválido")
        isValid = false
    }

    // Validación de Contraseña
    if (password.isBlank()) {
        onPasswordError("La contraseña es requerida")
        isValid = false
    } else if (password.length < 6) {
        onPasswordError("La contraseña debe tener al menos 6 caracteres")
        isValid = false
    }

    // Validación de Confirmación de Contraseña
    if (confirmPassword.isBlank()) {
        onConfirmPasswordError("La confirmación de contraseña es requerida")
        isValid = false
    } else if (confirmPassword != password) {
        onConfirmPasswordError("Las contraseñas no coinciden")
        isValid = false
    }

    return isValid
}
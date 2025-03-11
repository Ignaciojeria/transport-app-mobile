package com.example.transport_app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    val registeredEmail by viewModel.registeredEmail.collectAsState() // ✅ Corrección aquí
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

    // ✅ Manejo del registro en LaunchedEffect sin @Composable error
    LaunchedEffect(registerResponse) {
        registerResponse?.let { response ->
            isLoading = false
            if (!response.message.startsWith("Error:", ignoreCase = true)) {
                println("Email registrado almacenado: $registeredEmail") // ✅ Debug
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

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Regístrate con tu usuario y contraseña o ingresa utilizando tu cuenta de Google",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it, color = Color.Red) } },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF5138EE),
                    errorBorderColor = Color.Red
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password field
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError != null,
                supportingText = { passwordError?.let { Text(it, color = Color.Red) } },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirm Password field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; confirmPasswordError = null },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError != null,
                supportingText = { confirmPasswordError?.let { Text(it, color = Color.Red) } },
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
        }
    }
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

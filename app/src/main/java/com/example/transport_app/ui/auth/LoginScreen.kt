package com.example.transport_app.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun LoginScreen() {
    Text(
        text = "Inicia sesión con tu usuario y contraseña o ingresa utilizando tu cuenta de google",
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Email field
    var email by remember { mutableStateOf("") }
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF5138EE)
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Password field
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            focusedBorderColor = Color(0xFF5138EE)
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Forgot password
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { /* TODO: Handle forgot password */ }
        ) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color.Black
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Login button
    Button(
        onClick = { /* TODO: Handle login */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF5138EE)
        )
    ) {
        Text(
            text = "Entrar",
            fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Or continue with
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "O inicia sesión con",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Google login button
    Button(
        onClick = { /* TODO: Handle Google login */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Note: You would need to add the Google icon to your project resources
            // Icon(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Google")
            Text(
                text = "G",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Continue with Google",
                fontSize = 16.sp
            )
        }
    }
}
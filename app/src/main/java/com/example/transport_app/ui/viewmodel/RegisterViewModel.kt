package com.example.transport_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transport_app.api.RegisterApiService
import com.example.transport_app.domain.RegisterRequest
import com.example.transport_app.domain.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val registerApiService = RegisterApiService()

    private val _registerResponse = MutableStateFlow<RegisterResponse?>(null)
    val registerResponse = _registerResponse.asStateFlow()

    private val _registeredEmail = MutableStateFlow<String?>(null) // Guarda el email
    val registeredEmail = _registeredEmail.asStateFlow()

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            val response = registerApiService.registerUser(request)
            _registerResponse.value = response

            if (!response.message.startsWith("Error:", ignoreCase = true)) {
                _registeredEmail.value = request.email // Guardar email solo si el registro es exitoso
            }
        }
    }
}

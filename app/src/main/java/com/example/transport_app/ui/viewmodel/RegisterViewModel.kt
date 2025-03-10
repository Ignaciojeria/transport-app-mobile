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

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            val response = registerApiService.registerUser(request)
            _registerResponse.value = response
        }
    }
}
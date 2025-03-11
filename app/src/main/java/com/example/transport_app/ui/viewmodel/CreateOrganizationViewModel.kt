package com.example.transport_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transport_app.api.CreateOrganizationApiService
import com.example.transport_app.domain.CreateOrganizationRequest
import com.example.transport_app.domain.CreateOrganizationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateOrganizationViewModel : ViewModel() {
    private val createOrganizationApiService = CreateOrganizationApiService()

    private val _organizationResponse = MutableStateFlow<CreateOrganizationResponse?>(null)
    val organizationResponse = _organizationResponse.asStateFlow()

    fun createOrganization(name: String, country: String, email: String) {
        viewModelScope.launch {
            val request = CreateOrganizationRequest(
                email = email,
                name = name // ✅ Se agrega `name` al request body
            )
            val response = createOrganizationApiService.createOrganization(email, name, country)
            _organizationResponse.value = response
        }
    }
}

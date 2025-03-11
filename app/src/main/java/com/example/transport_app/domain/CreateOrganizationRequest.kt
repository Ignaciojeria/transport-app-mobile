package com.example.transport_app.domain

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrganizationRequest(
    val email: String,
    val name: String,
)
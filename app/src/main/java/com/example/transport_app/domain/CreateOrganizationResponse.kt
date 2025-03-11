package com.example.transport_app.domain

import kotlinx.serialization.Serializable


@Serializable
data class CreateOrganizationResponse(
    val organizationKey: String,
    val message: String,
)
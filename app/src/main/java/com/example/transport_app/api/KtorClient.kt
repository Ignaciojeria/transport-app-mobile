package com.example.transport_app.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

// Configurar Ktor Client
val client = HttpClient {
    install(ContentNegotiation) {
        json() // Usa JSON para serialización
    }
}
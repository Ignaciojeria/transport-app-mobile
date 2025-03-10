package com.example.transport_app.api

import com.example.transport_app.domain.RegisterRequest
import com.example.transport_app.domain.RegisterResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class RegisterApiService {
    suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post("https://einar-main-f0820bc.d2.zuplo.dev/register") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }

                if (response.status == HttpStatusCode.OK) {
                    response.body()
                } else if (response.status == HttpStatusCode.InternalServerError) {
                    // Intentar extraer el mensaje de error del cuerpo de la respuesta
                    try {
                        val errorBody = response.bodyAsText()
                        if (errorBody.contains("user with the provided email already exists")) {
                            RegisterResponse("Error: El email ya está registrado. Intenta con otro email.")
                        } else {
                            RegisterResponse("Error en el servidor: ${response.status.description}")
                        }
                    } catch (e: Exception) {
                        RegisterResponse("Error en el servidor: ${response.status.description}")
                    }
                } else {
                    RegisterResponse("Error: ${response.status.description}")
                }
            } catch (e: Exception) {
                RegisterResponse("Error: ${e.message}")
            }
        }
    }
}
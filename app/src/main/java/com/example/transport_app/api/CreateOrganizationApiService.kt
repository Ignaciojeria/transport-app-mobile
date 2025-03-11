package com.example.transport_app.api

import com.example.transport_app.domain.CreateOrganizationRequest
import com.example.transport_app.domain.CreateOrganizationResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateOrganizationApiService {

    suspend fun createOrganization(email: String, name: String, country: String): CreateOrganizationResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post("https://einar-main-f0820bc.d2.zuplo.dev/organizations") {
                    contentType(ContentType.Application.Json)
                    header("country", country) // ✅ Ahora se envía en el header
                    setBody(CreateOrganizationRequest(email = email, name = name)) // ✅ Enviamos el name y el email
                }

                if (response.status == HttpStatusCode.OK) {
                    response.body()
                } else if (response.status == HttpStatusCode.InternalServerError) {
                    try {
                        val errorBody = response.bodyAsText()
                        CreateOrganizationResponse("", "Error en el servidor: $errorBody")
                    } catch (e: Exception) {
                        CreateOrganizationResponse("", "Error en el servidor: ${response.status.description}")
                    }
                } else {
                    CreateOrganizationResponse("", "Error: ${response.status.description}")
                }
            } catch (e: Exception) {
                CreateOrganizationResponse("", "Error: ${e.message}")
            }
        }
    }
}

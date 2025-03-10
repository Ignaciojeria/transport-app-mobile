package com.example.transport_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transport_app.ui.auth.AuthenticationScreen
import com.example.transport_app.ui.CreateOrganizationScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "authentication"
    ) {
        composable("authentication") {
            AuthenticationScreen(navController)
        }

        composable("createOrganization") {
            CreateOrganizationScreen(
                onCreateClick = { organizationName, selectedCountry ->
                    // Aquí podrías guardar la organización y luego redirigir a otra pantalla
                }
            )
        }
    }
}

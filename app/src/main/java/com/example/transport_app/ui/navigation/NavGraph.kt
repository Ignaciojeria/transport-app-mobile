package com.example.transport_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transport_app.ui.auth.AuthenticationScreen
import com.example.transport_app.ui.CreateOrganizationScreen
import com.example.transport_app.ui.viewmodel.RegisterViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val registerViewModel: RegisterViewModel = viewModel() // ✅ Se comparte entre pantallas

    NavHost(
        navController = navController,
        startDestination = "authentication"
    ) {
        composable("authentication") {
            AuthenticationScreen(navController, registerViewModel)
        }

        composable("createOrganization") {
            CreateOrganizationScreen(
                registerViewModel = registerViewModel, // ✅ Se pasa el ViewModel con el email registrado
                onSuccess = { navController.popBackStack() } // ✅ Navega hacia atrás tras éxito
            )
        }
    }
}

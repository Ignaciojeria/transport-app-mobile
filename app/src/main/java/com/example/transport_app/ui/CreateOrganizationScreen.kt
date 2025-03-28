package com.example.transport_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.example.transport_app.ui.viewmodel.CreateOrganizationViewModel
import com.example.transport_app.ui.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrganizationScreen(
    registerViewModel: RegisterViewModel,
    createOrganizationViewModel: CreateOrganizationViewModel = viewModel(),
    onSuccess: () -> Unit = {}
) {
    val registeredEmail by registerViewModel.registeredEmail.collectAsState()
    val organizationResponse by createOrganizationViewModel.organizationResponse.collectAsState()

    var organizationName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("") }
    val countries = listOf("Colombia", "México", "Argentina", "Chile", "Perú", "Ecuador", "Venezuela", "Brasil")

    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(organizationResponse) {
        organizationResponse?.let { response ->
            if (response.organizationKey.isNotEmpty()) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Organización creada con éxito")
                }
                onSuccess()
            } else {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(response.message)
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Crea tu organización",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Logo de la organización
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("https://ignaciojeria.github.io/_astro/logo.CQIbniWh.svg")
                    .build(),
                contentDescription = "Logo de la organización",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(percent = 50)),
                contentScale = ContentScale.Fit,
                imageLoader = imageLoader
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo para el nombre de la organización
            OutlinedTextField(
                value = organizationName,
                onValueChange = { organizationName = it },
                label = { Text("Nombre de tu organización") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF5138EE)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown para seleccionar el país
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCountry.ifEmpty { "País de operación logística" },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color(0xFF5138EE)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = { Text(country) },
                            onClick = {
                                selectedCountry = country
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para crear la organización
            Button(
                onClick = {
                    registeredEmail?.let { email ->
                        createOrganizationViewModel.createOrganization(
                            name = organizationName,
                            country = selectedCountry,
                            email = email
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5138EE)),
                enabled = organizationName.isNotEmpty() && selectedCountry.isNotEmpty() && registeredEmail != null
            ) {
                Text(
                    text = "Crear Organización",
                    fontSize = 18.sp
                )
            }
        }
    }
}

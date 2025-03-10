/app
├── /data
│    ├── /network   <-- 🔹 Cliente HTTP y llamadas a la API
│    │    ├── KtorClient.kt
│    │    ├── AuthApiService.kt  <-- 🔹 Aquí va `registerUser`
│    ├── /repository  <-- 🔹 Manejo de datos y cacheo si se requiere
│    │    ├── AuthRepository.kt
├── /domain  <-- Lógica de negocio (si aplica Clean Architecture)
│    ├── models/
│    │    ├── RegisterRequest.kt
│    │    ├── RegisterResponse.kt
├── /ui
│    ├── /auth
│    │    ├── RegisterScreen.kt  <-- 🔹 Pantalla de registro (Jetpack Compose)
│    ├── /viewmodel
│    │    ├── AuthViewModel.kt  <-- 🔹 Manejo de estado

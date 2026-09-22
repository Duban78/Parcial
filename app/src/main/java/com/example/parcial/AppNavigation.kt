package com.example.parcial.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcial.CasosScreen
import com.example.parcial.ui.screens.HomeScreen
import com.example.parcial.viewmodel.CasoViewModel

@Composable
fun AppNavigation(
    casoViewModel: CasoViewModel = viewModel()
) {
    var currentScreen by rememberSaveable { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> HomeScreen(
            onNavigateToCases = { currentScreen = "my_cases" },
            onNavigateToNewCase = { currentScreen = "new_case" }
        )
        "my_cases" -> {
            CasosScreen(viewModel = casoViewModel)
        }
        "new_case" -> {
            // Próximamente: Pantalla para registrar un nuevo caso
        }
    }
}
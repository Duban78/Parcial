package com.example.parcial.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcial.CasosScreen
import com.example.parcial.DetalleCasoScreen
import com.example.parcial.EditarCasoScreen
import com.example.parcial.NuevoCasoScreen
import com.example.parcial.ui.screens.HomeScreen
import com.example.parcial.viewmodel.CasoViewModel

@Composable
fun AppNavigation(
    casoViewModel: CasoViewModel = viewModel()
) {
    var currentScreen by rememberSaveable { mutableStateOf("home") }
    var casoSeleccionadoId by rememberSaveable { mutableStateOf("") }

    when (currentScreen) {
        "home" -> HomeScreen(
            onNavigateToCases = { currentScreen = "my_cases" },
            onNavigateToNewCase = { currentScreen = "new_case" }
        )
        "my_cases" -> CasosScreen(
            viewModel = casoViewModel,
            onVerDetalle = { id ->
                casoSeleccionadoId = id
                currentScreen = "detalle_caso"
            },
            onVolverInicio = { currentScreen = "home" }
        )
        "new_case" -> NuevoCasoScreen(
            viewModel = casoViewModel,
            onVolver = { currentScreen = "home" }
        )
        "detalle_caso" -> DetalleCasoScreen(
            casoId = casoSeleccionadoId,
            viewModel = casoViewModel,
            onVolver = { currentScreen = "my_cases" },
            onEditar = { id ->
                casoSeleccionadoId = id
                currentScreen = "editar_caso"
            }
        )
        "editar_caso" -> EditarCasoScreen(
            casoId = casoSeleccionadoId,
            viewModel = casoViewModel,
            onVolver = { currentScreen = "detalle_caso" }
        )
    }
}
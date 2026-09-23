package com.example.parcial.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcial.CasosScreen
import com.example.parcial.DetalleCasoScreen
import com.example.parcial.EditarCasoScreen
import com.example.parcial.NuevoCasoScreen
import com.example.parcial.ui.screens.HomeScreen
import com.example.parcial.viewmodel.CasoViewModel

// Mismos colores que ya usas en tus pantallas — el fondo se pinta aquí una sola vez
private val NavyDark = Color(0xFF0D1B2A)
private val NavyMid = Color(0xFF1B263B)

@Composable
fun AppNavigation(
    casoViewModel: CasoViewModel = viewModel()
) {
    var currentScreen by rememberSaveable { mutableStateOf("home") }
    var casoSeleccionadoId by rememberSaveable { mutableStateOf("") }

    // Este Box envuelve TODO lo que se muestre en el "when" de abajo,
    // así que cualquier pantalla queda automáticamente sobre este fondo.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(NavyDark, NavyMid),
                    startY = 0f,
                    endY = 900f
                )
            )
    ) {
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
}
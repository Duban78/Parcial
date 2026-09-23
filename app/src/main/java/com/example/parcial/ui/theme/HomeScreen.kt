package com.example.parcial.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

// Colores base "detective" — ajústalos a tu ParcialTheme si ya tienes una paleta definida
private val NavyDark = Color(0xFF0D1B2A)
private val NavyMid = Color(0xFF1B263B)
private val AccentBlue = Color(0xFF3E92CC)

@Composable
fun HomeScreen(
    onNavigateToCases: () -> Unit,
    onNavigateToNewCase: () -> Unit
) {
    val hora = remember { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val saludo = when (hora) {
        in 5..11 -> "Buenos días, Detective"
        in 12..18 -> "Buenas tardes, Detective"
        else -> "Buenas noches, Detective"
    }

    // Controla si ya se debe mostrar el contenido, para disparar la animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

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
        // --- Capas decorativas de fondo (para que no se vea vacío) ---

        // Blob difuso superior derecho
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .offset(x = 90.dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(AccentBlue.copy(alpha = 0.20f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Blob difuso inferior izquierdo
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-100).dp, y = 60.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF5C6BC0).copy(alpha = 0.16f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Lupa gigante como marca de agua, para llenar el espacio vacío bajo las tarjetas
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.05f),
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.Center)
                .offset(y = 160.dp)
                .rotate(-15f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { -40 }
            ) {
                Column {
                    Text(
                        text = saludo,
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "La verdad siempre deja rastro",
                        color = Color(0xFF8FA6C8),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Grid 2x2 de tarjetas — Estadísticas y Casos cerrados quedan sin acción
            // hasta que crees esas pantallas y las agregues al "when" de AppNavigation
            val tarjetas = listOf(
                Tarjeta("Mis casos", "Revisa y gestiona tus investigaciones", Icons.Default.List, onNavigateToCases),
                Tarjeta("Nuevo caso", "Registra un caso para comenzar", Icons.Default.AddCircle, onNavigateToNewCase),
                Tarjeta("Estadísticas", "Tu actividad en números", Icons.Default.Info) {},
                Tarjeta("Casos cerrados", "Revisa los casos finalizados", Icons.Default.CheckCircle) {}
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                tarjetas.chunked(2).forEachIndexed { filaIndex, fila ->
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(400, delayMillis = 150 * (filaIndex + 1))) +
                                slideInVertically(tween(400, delayMillis = 150 * (filaIndex + 1))) { 60 }
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            fila.forEach { tarjeta ->
                                TarjetaDinamica(tarjeta = tarjeta, modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class Tarjeta(
    val titulo: String,
    val descripcion: String,
    val icono: ImageVector,
    val onClick: () -> Unit
)

@Composable
private fun TarjetaDinamica(tarjeta: Tarjeta, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    // La tarjeta se "hunde" ligeramente al presionarla — feedback táctil visual
    val scale by animateFloatAsState(targetValue = if (pressed) 0.95f else 1f, label = "scaleTarjeta")

    Card(
        onClick = tarjeta.onClick,
        interactionSource = interactionSource,
        modifier = modifier
            .scale(scale)
            .height(120.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = NavyMid.copy(alpha = 0.85f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (pressed) 2.dp else 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = tarjeta.icono,
                contentDescription = tarjeta.titulo,
                tint = AccentBlue,
                modifier = Modifier.size(26.dp)
            )
            Column {
                Text(
                    text = tarjeta.titulo,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = tarjeta.descripcion,
                    color = Color(0xFF8FA6C8),
                    fontSize = 11.sp
                )
            }
        }
    }
}
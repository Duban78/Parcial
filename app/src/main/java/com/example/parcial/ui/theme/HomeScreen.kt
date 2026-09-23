package com.example.parcial.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial.ui.theme.AccentBlue
import com.example.parcial.ui.theme.FondoDetective
import com.example.parcial.ui.theme.NavyMid
import com.example.parcial.ui.theme.TextoClaro
import java.util.Calendar

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

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    FondoDetective {
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
                        color = TextoClaro,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            val tarjetas = listOf(
                Tarjeta("Mis casos", "Revisa y gestiona tus investigaciones", Icons.Default.List, onNavigateToCases),
                Tarjeta("Nuevo caso", "Registra un caso para comenzar", Icons.Default.AddCircle, onNavigateToNewCase)
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
                    color = TextoClaro,
                    fontSize = 11.sp
                )
            }
        }
    }
}
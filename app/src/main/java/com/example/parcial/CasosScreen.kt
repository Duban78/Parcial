package com.example.parcial

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.parcial.viewmodel.CasoViewModel

// Colores propios de esta pantalla — si más adelante quieres compartirlos
// con HomeScreen, ahí decides sacarlos a un archivo común.
private val NavyDark = Color(0xFF0D1B2A)
private val NavyMid = Color(0xFF1B263B)
private val AccentBlue = Color(0xFF3E92CC)

@Composable
fun CasosScreen(
    viewModel: CasoViewModel,
    onVerDetalle: (String) -> Unit
) {
    val listaCasos = viewModel.listaCasos
    val textoBusqueda = viewModel.textoBusqueda

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
        // Blob decorativo para que no se vea plano
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .offset(x = 90.dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(AccentBlue.copy(alpha = 0.18f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(350)) + slideInVertically(tween(350)) { -30 }
            ) {
                Text(
                    text = "Gestión de Casos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { viewModel.actualizarTextoBusqueda(it) },
                label = { Text("Buscar por título o cliente...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = AccentBlue)
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = AccentBlue,
                    unfocusedLabelColor = Color(0xFF8FA6C8),
                    focusedBorderColor = AccentBlue,
                    unfocusedBorderColor = Color(0xFF8FA6C8),
                    cursorColor = AccentBlue
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(listaCasos) { index, caso ->
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(300, delayMillis = 60 * index)) +
                                slideInVertically(tween(300, delayMillis = 60 * index)) { 40 }
                    ) {
                        CasoCard(
                            titulo = caso.titulo,
                            cliente = caso.cliente,
                            estado = caso.estado,
                            onClick = { onVerDetalle(caso.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CasoCard(
    titulo: String,
    cliente: String,
    estado: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (pressed) 0.97f else 1f, label = "scaleCaso")

    val colorEstado = when {
        estado.contains("investigaci", ignoreCase = true) -> Color(0xFFE9A93B) // ámbar
        estado.contains("cerrado", ignoreCase = true) -> Color(0xFF4CAF7D)     // verde
        else -> AccentBlue
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyMid.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (pressed) 2.dp else 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cliente: $cliente",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCBD5E1)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Chip de estado con color según el valor
            Box(
                modifier = Modifier
                    .background(colorEstado.copy(alpha = 0.18f), shape = RoundedCornerShape(50))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = estado,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorEstado,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
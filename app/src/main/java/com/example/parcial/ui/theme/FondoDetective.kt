package com.example.parcial.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ── Paleta única de la app ──
// Cualquier pantalla que necesite estos colores los importa desde aquí,
// en vez de volver a declararlos.
val NavyDark = Color(0xFF0D1B2A)
val NavyMid = Color(0xFF1B263B)
val AccentBlue = Color(0xFF3E92CC)
val TextoClaro = Color(0xFF8FA6C8)

/**
 * Fondo degradado + blobs decorativos, reutilizable en cualquier pantalla.
 *
 * Uso:
 * @Composable
 * fun MiPantalla() {
 *     FondoDetective {
 *         // tu contenido de siempre va aquí adentro, tal cual
 *     }
 * }
 */
@Composable
fun FondoDetective(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(NavyDark, NavyMid),
                    startY = 0f,
                    endY = 900f
                )
            )
    ) {
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

        content()
    }
}
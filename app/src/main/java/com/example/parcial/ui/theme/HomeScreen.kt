package com.example.parcial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial.ui.components.OptionCard

val DarkBackground = Color(0xFF0F172A)
val AccentBlue = Color(0xFF38BDF8)

@Composable
fun HomeScreen(
    onNavigateToCases: () -> Unit,
    onNavigateToNewCase: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        // Encabezado de la app
        Text(
            text = "CaseTrack",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Your cases, always under control",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Saludo
        Text(
            text = "Hello, Detective",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "The truth always leaves a trace",
            fontSize = 14.sp,
            color = AccentBlue
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Primera Fila
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OptionCard(
                title = "My Cases",
                subtitle = "Review and manage your investigations",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToCases
            )
            OptionCard(
                title = "+ New Case",
                subtitle = "Register a case to start",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToNewCase
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Segunda Fila
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OptionCard(
                title = "Statistics",
                subtitle = "Your activity in numbers",
                modifier = Modifier.weight(1f),
                onClick = { }
            )
            OptionCard(
                title = "Closed Cases",
                subtitle = "Review completed cases",
                modifier = Modifier.weight(1f),
                onClick = { }
            )
        }
    }
}
package com.example.parcial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial.modelo.Caso
import com.example.parcial.viewmodel.CasoViewModel

val DarkBackground = Color(0xFF0F172A)
val AccentBlue = Color(0xFF38BDF8)

@Composable
fun CreateCaseScreen(
    viewModel: CasoViewModel,
    onBack: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var client by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Register New Case",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Enter details to start investigation",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo: Título
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Case Title") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = AccentBlue,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = AccentBlue,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Cliente
        OutlinedTextField(
            value = client,
            onValueChange = { client = it },
            label = { Text("Client Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = AccentBlue,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = AccentBlue,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Descripción
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = AccentBlue,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = AccentBlue,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón Guardar
        Button(
            onClick = {
                if (title.isNotBlank() && client.isNotBlank()) {
                    val nuevoCaso = Caso(
                        id = "C${System.currentTimeMillis().toString().takeLast(3)}",
                        titulo = title,
                        descripcion = description,
                        cliente = client,
                        fechaInicio = "2026-09-21",
                        estado = "En investigación",
                        hallazgos = mutableListOf(),
                        evidencias = mutableListOf()
                    )
                    viewModel.agregarCaso(nuevoCaso)
                    onBack() // Vuelve a la pantalla anterior
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text(
                text = "Save Case",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBackground
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Cancelar
        OutlinedButton(
            onClick = { onBack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Cancel",
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }
    }
}
package com.example.parcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.parcial.modelo.Caso
import com.example.parcial.viewmodel.CasoViewModel
import com.example.parcial.ui.theme.FondoDetective   // 👈 nuevo import

// Colores propios de esta pantalla, coherentes con el resto de la app
private val AccentBlue = Color(0xFF3E92CC)
private val TextoClaro = Color(0xFFCBD5E1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoCasoScreen(
    viewModel: CasoViewModel,
    onVolver: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("En investigación") }
    var errorMensaje by remember { mutableStateOf("") }

    val opcionesEstado = listOf("En investigación", "Cerrado")

    val coloresCampo = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedLabelColor = AccentBlue,
        unfocusedLabelColor = TextoClaro,
        focusedBorderColor = AccentBlue,
        unfocusedBorderColor = TextoClaro,
        cursorColor = AccentBlue
    )

    FondoDetective {   // 👈 todo el Scaffold ahora vive adentro
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo Caso", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título *") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción *") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    colors = coloresCampo
                )

                OutlinedTextField(
                    value = cliente,
                    onValueChange = { cliente = it },
                    label = { Text("Cliente / Solicitante *") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )

                OutlinedTextField(
                    value = fechaInicio,
                    onValueChange = { fechaInicio = it },
                    label = { Text("Fecha de inicio (ej: 2026-09-01) *") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo
                )

                Text("Estado:", style = MaterialTheme.typography.labelLarge, color = AccentBlue)
                opcionesEstado.forEach { opcion ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = estado == opcion,
                            onClick = { estado = opcion },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = AccentBlue,
                                unselectedColor = TextoClaro
                            )
                        )
                        Text(
                            text = opcion,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
                        )
                    }
                }

                if (errorMensaje.isNotEmpty()) {
                    Text(
                        text = errorMensaje,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (titulo.isBlank() || descripcion.isBlank() || cliente.isBlank() || fechaInicio.isBlank()) {
                            errorMensaje = "Por favor completa todos los campos obligatorios."
                        } else {
                            val nuevoCaso = Caso(
                                id = "C${System.currentTimeMillis()}",
                                titulo = titulo,
                                descripcion = descripcion,
                                cliente = cliente,
                                fechaInicio = fechaInicio,
                                estado = estado
                            )
                            viewModel.agregarCaso(nuevoCaso)
                            onVolver()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                ) {
                    Text("Guardar caso")
                }
            }
        }
    }
}
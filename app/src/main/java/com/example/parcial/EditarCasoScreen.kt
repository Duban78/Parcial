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
import com.example.parcial.ui.theme.AccentBlue        // 👈 reutiliza la paleta única
import com.example.parcial.ui.theme.TextoClaro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCasoScreen(
    casoId: String,
    viewModel: CasoViewModel,
    onVolver: () -> Unit
) {
    val caso = viewModel.listaCasos.find { it.id == casoId }

    if (caso == null) {
        Text("Caso no encontrado")
        return
    }

    var titulo by remember { mutableStateOf(caso.titulo) }
    var descripcion by remember { mutableStateOf(caso.descripcion) }
    var cliente by remember { mutableStateOf(caso.cliente) }
    var fechaInicio by remember { mutableStateOf(caso.fechaInicio) }
    var estado by remember { mutableStateOf(caso.estado) }
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

    FondoDetective {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Editar Caso", color = Color.White) },
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
                            val casoActualizado = caso.copy(
                                titulo = titulo,
                                descripcion = descripcion,
                                cliente = cliente,
                                fechaInicio = fechaInicio,
                                estado = estado
                            )
                            viewModel.editarCaso(casoActualizado)
                            onVolver()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                ) {
                    Text("Guardar cambios")
                }
            }
        }
    }
}
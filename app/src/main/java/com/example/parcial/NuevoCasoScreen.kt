package com.example.parcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial.modelo.Caso
import com.example.parcial.viewmodel.CasoViewModel

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Caso") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
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
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = cliente,
                onValueChange = { cliente = it },
                label = { Text("Cliente / Solicitante *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { fechaInicio = it },
                label = { Text("Fecha de inicio (ej: 2026-09-01) *") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Estado:", style = MaterialTheme.typography.labelLarge)
            opcionesEstado.forEach { opcion ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = estado == opcion,
                        onClick = { estado = opcion }
                    )
                    Text(
                        text = opcion,
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar caso")
            }
        }
    }
}
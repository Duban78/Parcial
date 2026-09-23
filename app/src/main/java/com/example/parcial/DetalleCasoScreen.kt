package com.example.parcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial.viewmodel.CasoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCasoScreen(
    casoId: String,
    viewModel: CasoViewModel,
    onVolver: () -> Unit,
    onEditar: (String) -> Unit
) {
    val caso = viewModel.listaCasos.find { it.id == casoId }

    if (caso == null) {
        Text("Caso no encontrado")
        return
    }

    var tabSeleccionado by remember { mutableStateOf(0) }
    val tabs = listOf("Resumen", "Hallazgos", "Evidencias", "Cierre")

    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarDialogoCierre by remember { mutableStateOf(false) }
    var descripcionCierre by remember { mutableStateOf(caso.descripcionCierre) }

    var textoHallazgo by remember { mutableStateOf("") }
    var textoEvidencia by remember { mutableStateOf("") }

    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("Eliminar caso") },
            text = { Text("¿Estás seguro de que deseas eliminar este caso?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarCaso(caso.id)
                    mostrarDialogoEliminar = false
                    onVolver()
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(caso.titulo) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditar(caso.id) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { mostrarDialogoEliminar = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = tabSeleccionado) {
                tabs.forEachIndexed { index, titulo ->
                    Tab(
                        selected = tabSeleccionado == index,
                        onClick = { tabSeleccionado = index },
                        text = { Text(titulo) }
                    )
                }
            }

            when (tabSeleccionado) {
                0 -> {
                    // Tab Resumen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Título", style = MaterialTheme.typography.labelSmall)
                        Text(caso.titulo, style = MaterialTheme.typography.bodyLarge)
                        Divider()
                        Text("Descripción", style = MaterialTheme.typography.labelSmall)
                        Text(caso.descripcion, style = MaterialTheme.typography.bodyLarge)
                        Divider()
                        Text("Cliente", style = MaterialTheme.typography.labelSmall)
                        Text(caso.cliente, style = MaterialTheme.typography.bodyLarge)
                        Divider()
                        Text("Fecha de inicio", style = MaterialTheme.typography.labelSmall)
                        Text(caso.fechaInicio, style = MaterialTheme.typography.bodyLarge)
                        Divider()
                        Text("Estado", style = MaterialTheme.typography.labelSmall)
                        Text(caso.estado, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                1 -> {
                    // Tab Hallazgos
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        caso.hallazgos.forEach { hallazgo ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(hallazgo.descripcion, style = MaterialTheme.typography.bodyMedium)
                                    Text("Fecha: ${hallazgo.fecha}", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = textoHallazgo,
                            onValueChange = { textoHallazgo = it },
                            label = { Text("Nuevo hallazgo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                if (textoHallazgo.isNotBlank()) {
                                    viewModel.agregarHallazgoACaso(caso.id, textoHallazgo)
                                    textoHallazgo = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar hallazgo")
                        }
                    }
                }
                2 -> {
                    // Tab Evidencias
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        caso.evidencias.forEach { evidencia ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(evidencia.titulo, style = MaterialTheme.typography.bodyMedium)
                                    Text("Tipo: ${evidencia.tipo}", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = textoEvidencia,
                            onValueChange = { textoEvidencia = it },
                            label = { Text("Nueva evidencia") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                if (textoEvidencia.isNotBlank()) {
                                    viewModel.agregarEvidenciaACaso(caso.id, textoEvidencia)
                                    textoEvidencia = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar evidencia")
                        }
                    }
                }
                3 -> {
                    // Tab Cierre
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Estado actual: ${caso.estado}", style = MaterialTheme.typography.bodyLarge)
                        OutlinedTextField(
                            value = descripcionCierre,
                            onValueChange = { descripcionCierre = it },
                            label = { Text("Descripción de cierre") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                        Button(
                            onClick = {
                                viewModel.cerrarCaso(caso.id, descripcionCierre)
                                onVolver()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = caso.estado != "Cerrado"
                        ) {
                            Text(if (caso.estado == "Cerrado") "Caso ya cerrado" else "Cerrar caso")
                        }
                    }
                }
            }
        }
    }
}
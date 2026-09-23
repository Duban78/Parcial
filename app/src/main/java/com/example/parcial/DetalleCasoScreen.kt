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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.parcial.ui.theme.AccentBlue
import com.example.parcial.ui.theme.FondoDetective
import com.example.parcial.ui.theme.NavyMid
import com.example.parcial.ui.theme.TextoClaro
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
        FondoDetective {
            Text("Caso no encontrado", color = Color.White, modifier = Modifier.padding(16.dp))
        }
        return
    }

    var tabSeleccionado by remember { mutableStateOf(0) }
    val tabs = listOf("Resumen", "Hallazgos", "Evidencias", "Cierre")

    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarDialogoCierre by remember { mutableStateOf(false) }
    var descripcionCierre by remember { mutableStateOf(caso.descripcionCierre) }

    var textoHallazgo by remember { mutableStateOf("") }
    var textoEvidencia by remember { mutableStateOf("") }

    val coloresCampo = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedLabelColor = AccentBlue,
        unfocusedLabelColor = TextoClaro,
        focusedBorderColor = AccentBlue,
        unfocusedBorderColor = TextoClaro,
        cursorColor = AccentBlue
    )

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

    FondoDetective {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(caso.titulo, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { onEditar(caso.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                        }
                        IconButton(onClick = { mostrarDialogoEliminar = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
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
            ) {
                TabRow(
                    selectedTabIndex = tabSeleccionado,
                    containerColor = Color.Transparent,
                    contentColor = AccentBlue
                ) {
                    tabs.forEachIndexed { index, titulo ->
                        Tab(
                            selected = tabSeleccionado == index,
                            onClick = { tabSeleccionado = index },
                            text = {
                                Text(
                                    titulo,
                                    color = if (tabSeleccionado == index) AccentBlue else TextoClaro
                                )
                            }
                        )
                    }
                }

                when (tabSeleccionado) {
                    0 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Título", style = MaterialTheme.typography.labelSmall, color = AccentBlue)
                            Text(caso.titulo, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Divider(color = TextoClaro.copy(alpha = 0.25f))
                            Text("Descripción", style = MaterialTheme.typography.labelSmall, color = AccentBlue)
                            Text(caso.descripcion, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Divider(color = TextoClaro.copy(alpha = 0.25f))
                            Text("Cliente", style = MaterialTheme.typography.labelSmall, color = AccentBlue)
                            Text(caso.cliente, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Divider(color = TextoClaro.copy(alpha = 0.25f))
                            Text("Fecha de inicio", style = MaterialTheme.typography.labelSmall, color = AccentBlue)
                            Text(caso.fechaInicio, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Divider(color = TextoClaro.copy(alpha = 0.25f))
                            Text("Estado", style = MaterialTheme.typography.labelSmall, color = AccentBlue)
                            Text(caso.estado, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            caso.hallazgos.forEach { hallazgo ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = NavyMid.copy(alpha = 0.85f))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            hallazgo.descripcion,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White
                                        )
                                        Text(
                                            "Fecha: ${hallazgo.fecha}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextoClaro
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = textoHallazgo,
                                onValueChange = { textoHallazgo = it },
                                label = { Text("Nuevo hallazgo") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = coloresCampo
                            )
                            Button(
                                onClick = {
                                    if (textoHallazgo.isNotBlank()) {
                                        viewModel.agregarHallazgoACaso(caso.id, textoHallazgo)
                                        textoHallazgo = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                            ) {
                                Text("Agregar hallazgo")
                            }
                        }
                    }
                    2 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            caso.evidencias.forEach { evidencia ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = NavyMid.copy(alpha = 0.85f))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            evidencia.titulo,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White
                                        )
                                        Text(
                                            "Tipo: ${evidencia.tipo}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = TextoClaro
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = textoEvidencia,
                                onValueChange = { textoEvidencia = it },
                                label = { Text("Nueva evidencia") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = coloresCampo
                            )
                            Button(
                                onClick = {
                                    if (textoEvidencia.isNotBlank()) {
                                        viewModel.agregarEvidenciaACaso(caso.id, textoEvidencia)
                                        textoEvidencia = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                            ) {
                                Text("Agregar evidencia")
                            }
                        }
                    }
                    3 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                "Estado actual: ${caso.estado}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                            OutlinedTextField(
                                value = descripcionCierre,
                                onValueChange = { descripcionCierre = it },
                                label = { Text("Descripción de cierre") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                colors = coloresCampo
                            )
                            Button(
                                onClick = {
                                    viewModel.cerrarCaso(caso.id, descripcionCierre)
                                    onVolver()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = caso.estado != "Cerrado",
                                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                            ) {
                                Text(if (caso.estado == "Cerrado") "Caso ya cerrado" else "Cerrar caso")
                            }
                        }
                    }
                }
            }
        }
    }
}
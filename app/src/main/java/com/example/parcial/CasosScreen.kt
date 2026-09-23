package com.example.parcial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial.viewmodel.CasoViewModel

@Composable
fun CasosScreen(
    viewModel: CasoViewModel,
    onVerDetalle: (String) -> Unit
) {
    val listaCasos = viewModel.listaCasos
    val textoBusqueda = viewModel.textoBusqueda

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Gestión de Casos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { viewModel.actualizarTextoBusqueda(it) },
            label = { Text("Buscar por título o cliente...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaCasos) { caso ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onVerDetalle(caso.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = caso.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Cliente: ${caso.cliente}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Estado: ${caso.estado}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
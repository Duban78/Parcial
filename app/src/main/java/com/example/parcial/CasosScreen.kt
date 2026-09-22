package com.example.parcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial.viewmodel.CasoViewModel

@Composable
fun CasosScreen(viewModel: CasoViewModel) {
    val listaCasos = viewModel.listaCasos
    val textoBusqueda = viewModel.textoBusqueda

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Gestión de Casos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Barra de búsqueda vinculada al ViewModel
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { viewModel.actualizarTextoBusqueda(it) },
            label = { Text("Buscar por título o cliente...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista dinámica de casos
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaCasos) { caso ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
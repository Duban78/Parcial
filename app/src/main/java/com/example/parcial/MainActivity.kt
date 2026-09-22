package com.example.parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.parcial.ui.theme.ParcialTheme
import com.example.parcial.viewmodel.CasoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTheme {
                // Instanciamos el ViewModel directamente sin dependencias extra
                val viewModel = CasoViewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Aplicamos el padding aquí mismo para no modificar CasosScreen
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CasosScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
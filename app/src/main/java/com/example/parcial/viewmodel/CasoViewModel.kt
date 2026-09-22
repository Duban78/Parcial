package com.example.parcial.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.parcial.data.CasoRepository
import com.example.parcial.modelo.Caso
import com.example.parcial.modelo.Evidencia
import com.example.parcial.modelo.Hallazgo

class CasoViewModel : ViewModel() {

    var listaCasos by mutableStateOf(CasoRepository.obtenerCasos())
        private set

    var textoBusqueda by mutableStateOf("")
        private set

    fun actualizarTextoBusqueda(texto: String) {
        textoBusqueda = texto
        listaCasos = if (texto.isEmpty()) {
            CasoRepository.obtenerCasos()
        } else {
            CasoRepository.buscarCasos(texto)
        }
    }

    fun agregarCaso(caso: Caso) {
        CasoRepository.agregarCaso(caso)
        listaCasos = CasoRepository.obtenerCasos().toList()
    }

    fun agregarHallazgoACaso(casoId: String, descripcionTexto: String) {
        val caso = CasoRepository.obtenerCasoPorId(casoId)
        if (caso != null) {
            val nuevoHallazgo = Hallazgo(
                id = "H${caso.hallazgos.size + 1}",
                descripcion = descripcionTexto,
                fecha = "Hoy"
            )
            caso.hallazgos.add(nuevoHallazgo)
            listaCasos = CasoRepository.obtenerCasos().toList()
        }
    }

    fun agregarEvidenciaACaso(casoId: String, tituloTexto: String) {
        val caso = CasoRepository.obtenerCasoPorId(casoId)
        if (caso != null) {
            val nuevaEvidencia = Evidencia(
                id = "E${caso.evidencias.size + 1}",
                titulo = tituloTexto,
                tipo = "Digital"
            )
            caso.evidencias.add(nuevaEvidencia)
            listaCasos = CasoRepository.obtenerCasos().toList()
        }
    }
}
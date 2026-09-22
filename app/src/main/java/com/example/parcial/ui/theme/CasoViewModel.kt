package com.example.parcial.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.parcial.data.CasoRepository
import com.example.parcial.model.Caso
import com.example.parcial.model.Evidencia
import com.example.parcial.model.Hallazgo

class CasoViewModel : ViewModel() {

    // Lista observable para la UI
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
        listaCasos = CasoRepository.obtenerCasos()
    }

    fun actualizarCaso(caso: Caso) {
        CasoRepository.actualizarCaso(caso)
        listaCasos = CasoRepository.obtenerCasos()
    }

    fun eliminarCaso(id: String) {
        CasoRepository.eliminarCaso(id)
        listaCasos = CasoRepository.obtenerCasos()
    }

    fun agregarHallazgoACaso(casoId: String, descripcion: String, fecha: String) {
        val caso = CasoRepository.obtenerCasoPorId(casoId)
        if (caso != null) {
            val nuevoHallazgo = Hallazgo(
                id = "H${caso.hallazgos.size + 1}",
                descripcion = descripcion,
                fecha = fecha
            )
            caso.hallazgos.add(nuevoHallazgo)
            actualizarCaso(caso)
        }
    }

    fun agregarEvidenciaACaso(casoId: String, titulo: String, tipo: String) {
        val caso = CasoRepository.obtenerCasoPorId(casoId)
        if (caso != null) {
            val nuevaEvidencia = Evidencia(
                id = "E${caso.evidencias.size + 1}",
                titulo = titulo,
                tipo = tipo
            )
            caso.evidencias.add(nuevaEvidencia)
            actualizarCaso(caso)
        }
    }
}
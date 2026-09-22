package com.example.parcial.data

import com.example.parcial.modelo.Caso
import com.example.parcial.modelo.Evidencia
import com.example.parcial.modelo.Hallazgo

object CasoRepository {
    private val listaCasos = mutableListOf(
        Caso(
            id = "C1",
            titulo = "Robo en oficina central",
            descripcion = "Investigación sobre acceso no autorizado y sustracción de equipos.",
            cliente = "Empresa Tecnológica S.A.",
            fechaInicio = "2026-09-01",
            estado = "En investigación",
            hallazgos = mutableListOf(
                Hallazgo("H1", "Puerta trasera forzada", "2026-09-02")
            ),
            evidencias = mutableListOf(
                Evidencia("E1", "Video de seguridad cámara 4", "Digital")
            )
        ),
        Caso(
            id = "C2",
            titulo = "Fraude financiero interno",
            descripcion = "Auditoría de transacciones sospechosas en el departamento de contabilidad.",
            cliente = "Financiera Global",
            fechaInicio = "2026-09-10",
            estado = "En investigación",
            hallazgos = mutableListOf(),
            evidencias = mutableListOf()
        )
    )

    fun obtenerCasos(): List<Caso> = listaCasos

    fun obtenerCasoPorId(id: String): Caso? = listaCasos.find { it.id == id }

    fun buscarCasos(query: String): List<Caso> {
        return listaCasos.filter {
            it.titulo.contains(query, ignoreCase = true) ||
                    it.cliente.contains(query, ignoreCase = true)
        }
    }

    fun agregarCaso(caso: Caso) {
        listaCasos.add(caso)
    }
}
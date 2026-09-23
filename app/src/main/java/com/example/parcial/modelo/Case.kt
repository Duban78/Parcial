package com.example.parcial.modelo

data class Hallazgo(
    val id: String,
    val descripcion: String,
    val fecha: String
)

data class Evidencia(
    val id: String,
    val titulo: String,
    val tipo: String
)

data class Caso(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val cliente: String,
    val fechaInicio: String,
    var estado: String,
    var descripcionCierre: String = "",
    val hallazgos: MutableList<Hallazgo> = mutableListOf(),
    val evidencias: MutableList<Evidencia> = mutableListOf()
)
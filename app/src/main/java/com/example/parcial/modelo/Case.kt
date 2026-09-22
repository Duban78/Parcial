package com.example.parcial.model

data class Caso(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val cliente: String,
    val fechaInicio: String,
    val estado: String, // "En investigación" o "Cerrado"
    val hallazgos: MutableList<Hallazgo> = mutableListOf(),
    val evidencias: MutableList<Evidencia> = mutableListOf()
)
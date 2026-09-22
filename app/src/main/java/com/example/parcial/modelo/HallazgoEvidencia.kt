package com.example.parcial.model

data class Hallazgo(
    val id: String,
    val descripcion: String,
    val fecha: String
)

data class Evidencia(
    val id: String,
    val titulo: String,
    val tipo: String // Ej: "Foto", "Documento", "Audio"
)
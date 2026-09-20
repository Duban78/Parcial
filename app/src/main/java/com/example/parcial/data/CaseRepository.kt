package com.example.parcial.data

import com.example.parcial.model.Caso

object CasoRepository {

    // Lista en memoria con casos de prueba basados en los mockups del parcial
    private val listaCasos = mutableListOf(
        Caso(
            id = "W001",
            titulo = "Robo en la galería",
            descripcion = "Robo de varias piezas de arte en la galería central. Se sospecha de un grupo organizado.",
            cliente = "Galería de Arte Moderno",
            fechaInicio = "12 mar 2025",
            estado = "En investigación"
        ),
        Caso(
            id = "B002",
            titulo = "Fraude corporativo",
            descripcion = "Desvío de fondos no autorizado en las cuentas principales.",
            cliente = "Financiera del Norte",
            fechaInicio = "15 mar 2025",
            estado = "En investigación"
        ),
        Caso(
            id = "C003",
            titulo = "Desaparición de persona",
            descripcion = "Investigación sobre la desaparición de un ejecutivo.",
            cliente = "Familia Pérez",
            fechaInicio = "20 feb 2025",
            estado = "Cerrado"
        )
    )

    // 1. Obtener la lista completa de casos
    fun obtenerCasos(): List<Caso> = listaCasos

    // 2. Obtener un caso específico por su ID
    fun obtenerCasoPorId(id: String): Caso? {
        return listaCasos.find { it.id == id }
    }

    // 3. Crear/Agregar un nuevo caso
    fun agregarCaso(caso: Caso) {
        listaCasos.add(caso)
    }

    // 4. Editar un caso existente
    fun actualizarCaso(casoActualizado: Caso) {
        val indice = listaCasos.indexOfFirst { it.id == casoActualizado.id }
        if (indice != -1) {
            listaCasos[indice] = casoActualizado
        }
    }

    // 5. Eliminar un caso por ID
    fun eliminarCaso(id: String) {
        listaCasos.removeAll { it.id == id }
    }

    // 6. Buscar casos por título o nombre del cliente
    fun buscarCasos(texto: String): List<Caso> {
        if (texto.isEmpty()) return listaCasos
        return listaCasos.filter {
            it.titulo.contains(texto, ignoreCase = true) ||
                    it.cliente.contains(texto, ignoreCase = true)
        }
    }
}
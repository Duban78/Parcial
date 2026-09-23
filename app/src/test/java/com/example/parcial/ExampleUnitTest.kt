package com.example.parcial

import com.example.parcial.data.CasoRepository
import com.example.parcial.modelo.Caso
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    @Before
    fun limpiarRepositorio() {
        // Reiniciamos con casos de prueba antes de cada test
        CasoRepository.agregarCaso(
            Caso(
                id = "TEST1",
                titulo = "Caso de prueba",
                descripcion = "Descripción de prueba",
                cliente = "Cliente de prueba",
                fechaInicio = "2026-09-01",
                estado = "En investigación"
            )
        )
    }

    @Test
    fun agregarCaso_aumentaLaLista() {
        val cantidadAntes = CasoRepository.obtenerCasos().size
        CasoRepository.agregarCaso(
            Caso(
                id = "TEST2",
                titulo = "Nuevo caso",
                descripcion = "Descripción",
                cliente = "Cliente",
                fechaInicio = "2026-09-01",
                estado = "En investigación"
            )
        )
        val cantidadDespues = CasoRepository.obtenerCasos().size
        assertTrue(cantidadDespues > cantidadAntes)
    }

    @Test
    fun buscarCasos_retornaResultadosCorrecto() {
        val resultados = CasoRepository.buscarCasos("prueba")
        assertTrue(resultados.isNotEmpty())
    }

    @Test
    fun buscarCasos_sinCoincidencias_retornaListaVacia() {
        val resultados = CasoRepository.buscarCasos("xyzxyzxyz")
        assertTrue(resultados.isEmpty())
    }

    @Test
    fun obtenerCasoPorId_retornaCasoCorrecto() {
        val caso = CasoRepository.obtenerCasoPorId("TEST1")
        assertNotNull(caso)
        assertEquals("Caso de prueba", caso?.titulo)
    }

    @Test
    fun editarCaso_actualizaElTitulo() {
        val casoOriginal = CasoRepository.obtenerCasoPorId("TEST1")
        assertNotNull(casoOriginal)
        val casoEditado = casoOriginal!!.copy(titulo = "Título actualizado")
        CasoRepository.editarCaso(casoEditado)
        val casoActualizado = CasoRepository.obtenerCasoPorId("TEST1")
        assertEquals("Título actualizado", casoActualizado?.titulo)
    }

    @Test
    fun eliminarCaso_eliminaDelaLista() {
        CasoRepository.eliminarCaso("TEST1")
        val caso = CasoRepository.obtenerCasoPorId("TEST1")
        assertNull(caso)
    }
}
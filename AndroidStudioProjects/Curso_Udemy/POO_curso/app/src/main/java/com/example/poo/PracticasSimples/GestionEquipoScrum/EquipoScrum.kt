package com.example.poo.PracticasSimples.GestionEquipoScrum

fun main() {
    // Añadimos varias tareas a la lista del ScrumMaster
    ScrumMaster.registrarTarea(Tarea("Buscar vuelo", 4, 1.0))
    ScrumMaster.registrarTarea(Tarea("Buscar hotel", 9, 2.1))
    ScrumMaster.registrarTarea(Tarea("Buscar actividades", 10, 3.2))
    ScrumMaster.registrarTarea(Tarea("Buscar transporte", 2, 3.0))
    ScrumMaster.registrarTarea(Tarea("Buscar comida", 1, 2.2))

    // Con el '=' simplificamos la función e indicamos que va a devolver en este caso un Double
    fun sumarHoras(tarea: Tarea) = tarea.tiempoRealHoras
    println(
        "Total de horas del SRUM (v1): ${
            ScrumMaster.getListaTareas().calcularMetricaAcumulada(::sumarHoras)
        }"
    )

    println(
        "Total de horas del SRUM (v2): ${
            ScrumMaster.getListaTareas().calcularMetricaAcumulada { it -> it.tiempoRealHoras }
        }"
    )
    ScrumMaster.revisarTareasCriticas(5)
    ScrumMaster.getListaTareas().filter { it -> it.tiempoRealHoras < 3 }
        .map { it -> "Tarea optima: ${it.descripcion}" }.forEach(::println)
}

interface Productivo {
    fun obtenerPuntuajeBase(): Int
    fun esTareaCritica(nivelCritico: Int): Boolean
}

class TareaException(mensaje: String) : Exception(mensaje)

data class Tarea(
    var descripcion: String,
    var complejidadPuntos: Int,
    var tiempoRealHoras: Double
) : Productivo {

    init {
        if (complejidadPuntos > 10 || complejidadPuntos < 0) {
            throw TareaException("La complejidad no es válida")
        }
    }

    override fun obtenerPuntuajeBase(): Int {
        return this.complejidadPuntos
    }

    override fun esTareaCritica(nivelCritico: Int): Boolean {
        return this.complejidadPuntos > nivelCritico
    }
}

object ScrumMaster {
    private var listaTareas = mutableListOf<Tarea>()

    internal fun getListaTareas(): List<Tarea> {
        return this.listaTareas
    }

    fun registrarTarea(tarea: Tarea) {
        listaTareas.add(tarea)
    }

    fun revisarTareasCriticas(nivelCritico: Int) {
        listaTareas.filter { it -> it.esTareaCritica(nivelCritico) }.forEach(::println)
    }
}

fun List<Tarea>.calcularMetricaAcumulada(fn: (Tarea) -> Double): Double {
    return this.sumOf { it -> fn(it) }
}
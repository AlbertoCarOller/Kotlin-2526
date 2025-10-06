package com.example.poo.SistemaReportesYAnalisisAcademico

fun main() {
    try {
        var listaEstudiante = listOf<Estudiante>(
            Estudiante("Antonio", "Acceso a datos", 10.0, 40),
            Estudiante("Alberto", "Programación multimedia", 10.0, 90),
            Estudiante("Lolitogoku", "Programación", 8.0, 10),
            Estudiante("Paco", "Proyecto Intermodular", 2.0, 100)
        )
        println("Promedio de calificaiones: ${listaEstudiante.obtenerPromedio { it -> it.calificacion }}")
        println("Promedio de asistencia: ${listaEstudiante.obtenerPromedio { it -> it.asistenciaPorcetaje.toDouble() }}")

    } catch (e: SistemaReportesException) {
        println(e.message)
    }
}

interface Reportable {
    fun generarResumen(): String
}

class SistemaReportesException(mensaje: String) : Exception(mensaje)

data class Estudiante(
    var nombre: String,
    var materia: String,
    var calificacion: Double,
    var asistenciaPorcetaje: Int
) : Reportable {

    init {
        if (asistenciaPorcetaje > 100 || asistenciaPorcetaje < 0) {
            throw SistemaReportesException("El porcentaje no es válido")
        }
        if (calificacion > 10 || calificacion < 0) {
            throw SistemaReportesException("La calificación no es correcta");
        }
    }

    override fun generarResumen(): String {
        return "$nombre -> $calificacion"
    }
}

object GestorReportes {
    fun imprimirReportes(list: List<Estudiante>) {
        list.forEach { it -> it.generarResumen() }
    }
}

/**
 * Esta función extendida de orden superior va a obtener el promedio de calificaciones de todos
 * los estudiantes de una lista
 */
fun List<Estudiante>.obtenerPromedio(selectorValor: (Estudiante) -> Double): Double {
    return this.map { selectorValor(it) }.average()
}
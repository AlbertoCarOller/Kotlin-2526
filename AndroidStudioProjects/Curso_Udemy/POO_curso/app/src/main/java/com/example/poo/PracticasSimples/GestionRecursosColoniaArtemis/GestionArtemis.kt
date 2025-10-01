package com.example.poo.PracticasSimples.GestionRecursosColoniaArtemis

fun main() {
    var alarmaCentral = AlarmaCentral
    // Creamos una lista de sensores
    var listaSensores = listOf(
        SensorRecurso("Agua", 60.9, 70.8),
        SensorRecurso("Oxígeno", 90.33, 56.9),
        SensorRecurso("Comida", 34.1, 60.99),
        SensorRecurso("Enfermedades", 44.67, 67.67)
    )
    alarmaCentral.analizarRiesgos(listaSensores)
    println("Suma del umbral de todos los sensores: ${listaSensores.sumarMetricaTotal { it -> it.nivelActual }}")
    // Filtramos por los sensores que su umbral estén por debajo de 50 e imprimamos un mensaje
    listaSensores.filter { it.nivelActual < 50 }.map { it -> it.nombre }
        .forEach { it -> println("$it estable") }
}

interface Monitoreable {
    fun obtenerNivelCritico(): Double
    fun estaEnRiesgo(): Boolean
}

data class SensorRecurso(var nombre: String, var nivelActual: Double, var umbralCritico: Double) :
    Monitoreable {
    override fun obtenerNivelCritico(): Double {
        return umbralCritico
    }

    override fun estaEnRiesgo(): Boolean {
        return this.nivelActual <= umbralCritico
    }
}

object AlarmaCentral {
    fun activarAlertaGlobal(mensaje: String) {
        println(mensaje.uppercase())
    }

    fun analizarRiesgos(sensores: List<SensorRecurso>) {
        if (sensores.any { it -> it.estaEnRiesgo() }) {
            activarAlertaGlobal("alerta, se ha detectado umbral crítico")
        }
    }
}

/**
 * Creamos una función de orden superior extendida, esta sumará los valores devueltos
 * por 'selectorMetrica'
 */
fun List<SensorRecurso>.sumarMetricaTotal(selectorMetrica: (SensorRecurso) -> Double): Double {
    return this.sumOf { it -> selectorMetrica(it) }
}
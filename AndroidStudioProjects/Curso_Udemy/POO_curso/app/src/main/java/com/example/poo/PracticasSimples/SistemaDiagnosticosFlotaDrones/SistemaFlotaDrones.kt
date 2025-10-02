package com.example.poo.PracticasSimples.SistemaDiagnosticosFlotaDrones

fun main() {
    // Creamos una lista de drones
    var flota = listOf(
        Drone("CV987", "Chelu", 102.49, 56),
        Drone("SS717", "Atisbedo", 56.2, 67),
        Drone("QX651", "Serapio", 89.23, 45),
        Drone("CV987", "Chelu", 34.51, 44)
    )
    // Mostramos por consola un mensaje deregistro de cada dron de la flota
    flota.forEach { CentroControl.registrarNuevoVuelo(it) }
    // Mostramos el promedio de la batería de la flota
    println("Promedio de la batería de la flota: ${CentroControl.obtenerPromedioBateria(flota)}")
    flota.sumarMettrica { it -> it.tiempoVueloHoras }.forEach { it ->
        println(
            "ID: ${it.key}," +
                    " Horas de vuelo: ${it.value}"
        )
    }

    // Hacemos una función de prueba para pasar directamente a una función extendida de orden superior
    fun nivelBateria(drone: Drone) = drone.nivelBateria.toDouble()
    /* Le ponemos :: indicando que recibe el objeto Dron que esté pasando por el flujo y entre
     paréntesis por esto mismo, porque no le decimos explicitamente el objeto */
    flota.sumarMettrica(::nivelBateria).forEach(::println)
}

interface Diagnosticable {
    fun obtenerEstadoBateria(): Int
    fun requiereMantenimiento(): Boolean
}

data class Drone(
    var id: String,
    var modelo: String,
    var tiempoVueloHoras: Double,
    var nivelBateria: Int
) : Diagnosticable {
    override fun obtenerEstadoBateria(): Int {
        return this.nivelBateria
    }

    override fun requiereMantenimiento(): Boolean {
        return this.nivelBateria < 20
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Drone

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

object CentroControl {
    fun registrarNuevoVuelo(drone: Drone) {
        println("Se ha registado el drone ${drone.id}")
    }

    fun obtenerPromedioBateria(flota: List<Drone>): Int {
        return flota.map { it -> it.obtenerEstadoBateria() }.average().toInt()
    }
}

fun List<Drone>.sumarMettrica(selector: (Drone) -> Double): Map<String, Double> {
    return this.groupBy { it -> it.id }.mapValues { it -> it.value.sumOf { selector(it) } }
}
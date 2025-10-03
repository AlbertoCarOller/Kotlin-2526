package com.example.poo.PracticasSimples.GestionFlotasTransporte

fun main() {
    // Añadimos vehículos al 'TerminalCentral'
    TerminalCentral.registrarVehiculo(
        Autobus(
            "98345R",
            "Ruta del bacalao", 45, 25
        )
    )
    TerminalCentral.registrarVehiculo(
        Autobus(
            "6107E",
            "Ruta b78", 23, 15
        )
    )
    TerminalCentral.registrarVehiculo(
        Autobus(
            "0097T",
            "Ruta 66", 120, 30
        )
    )
    TerminalCentral.registrarVehiculo(
        Autobus(
            "2807V",
            "Ruta Atisbedo", 7, 10
        )
    )
    TerminalCentral.registrarVehiculo(
        Autobus(
            "6153A",
            "Ruta Serapio", 45, 20
        )
    )
    println(
        "Km totales de flota: ${
            TerminalCentral.getListaVehiculos()
                .calcularMetricaFlota { it -> it.distanciaRutaKm.toDouble() }
        }"
    )
    TerminalCentral.reportarEstadoRutas()
}

interface AsignableARuta {
    var obtenerCapacidadMaxima: () -> Int
    var esRutaLarga: (Int) -> Boolean
}

abstract class VehiculoTransporte(open var idFlota: String, open var ruta: String) : AsignableARuta

/* Para que una data class herede atributos de otra clase debe de hacer un override
 y que los atributos sean open */
data class Autobus(
    override var idFlota: String,
    override var ruta: String,
    var distanciaRutaKm: Int,
    var capacidadPasajeros: Int
) :
    VehiculoTransporte(idFlota, ruta) {
    override var obtenerCapacidadMaxima: () -> Int = { this.capacidadPasajeros }
    override var esRutaLarga: (Int) -> Boolean = { it < this.distanciaRutaKm }
}

object TerminalCentral {
    private var listaVehiculos = mutableListOf<Autobus>()

    internal fun getListaVehiculos(): List<Autobus> {
        return this.listaVehiculos
    }

    fun registrarVehiculo(autobus: Autobus) {
        listaVehiculos.add(autobus)
    }

    fun reportarEstadoRutas() {
        listaVehiculos.filter { it -> it.esRutaLarga(30) }.forEach { it -> println(it.idFlota) }
    }
}

fun List<Autobus>.calcularMetricaFlota(fn: (Autobus) -> Double): Double {
    return this.sumOf { it -> fn(it) }
}
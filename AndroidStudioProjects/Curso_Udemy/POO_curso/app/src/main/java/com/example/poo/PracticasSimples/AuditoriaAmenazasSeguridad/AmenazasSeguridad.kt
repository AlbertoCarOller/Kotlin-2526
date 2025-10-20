package com.example.poo.PracticasSimples.AuditoriaAmenazasSeguridad

fun main() {
    var listaEventoSeguridad = listOf(
        IntentoAcceso(
            "Punto Rojo", "20/10/2025", 4.3, 7.3,
            false
        ),
        IntentoAcceso(
            "Rana gustavo", "21/10/2023", 6.3, 4.3,
            false
        ),
        InfeccionMalware(
            "Malware nuclear", "09/04/2024", 0.5, 9.7,
            "Ransomware"
        ),
        InfeccionMalware(
            "Lolitogoku", "29/05/2025", 9.8, 0.2,
            "Lolito"
        )
    )

    println(
        "Total: %.2f".format(
            listaEventoSeguridad.auditarRiesgoAcumulado(
                { it -> it.probabilidadExito >= 5 },
                { it.obtenerGravedadFinal() }, { it -> it.gravedadBase })
        )
    )
}

abstract class EventoSeguridad(
    var nombreAmenza: String,
    var fechaDeteccion: String,
    var gravedadBase: Double,
    var probabilidadExito: Double,
) {
    abstract fun calcularAjusteGravedad(): Double
    fun obtenerGravedadFinal(): Double {
        return gravedadBase + calcularAjusteGravedad()
    }
}

class IntentoAcceso(
    nombreAmenza: String,
    fechaDeteccion: String,
    gravedadBase: Double,
    probabilidadExito: Double,
    var esFuerzaBruta: Boolean
) : EventoSeguridad(nombreAmenza, fechaDeteccion, gravedadBase, probabilidadExito) {
    override fun calcularAjusteGravedad(): Double {
        return if (esFuerzaBruta) {
            2.0
        } else {
            0.5
        }
    }
}

class InfeccionMalware(
    nombreAmenza: String,
    fechaDeteccion: String,
    gravedadBase: Double,
    probabilidadExito: Double,
    var tipoMalware: String
) : EventoSeguridad(nombreAmenza, fechaDeteccion, gravedadBase, probabilidadExito) {
    override fun calcularAjusteGravedad(): Double {
        return if (tipoMalware == "Ransomware") {
            4.0
        } else {
            1.5
        }
    }
}

fun List<EventoSeguridad>.auditarRiesgoAcumulado(
    condicion: (EventoSeguridad) -> Boolean,
    extractorValor: (EventoSeguridad) -> Double,
    extractorFactor: (EventoSeguridad) -> Double
): Double {
    return this.filter { it -> condicion(it) }
        .sumOf { it -> extractorValor(it) * extractorFactor(it) }
}
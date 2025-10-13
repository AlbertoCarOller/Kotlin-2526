package com.example.poo.PracticasSimples.EstudioProduccion

fun main() {
    try {
        // Hacemos una lista de contenido digital
        var listaContenidoDigital = listOf(
            ArticuloBlog("Piramides de Giza", 75, 0.3, 2.4, 20),
            ArticuloBlog("Piramides de Cancun", 120, 0.7, 3.5, 40),
            VideoTutorial("Confgurar tu Mac", 10, 0.2, 2.7, false),
            VideoTutorial("Tutorial con Doraemon", 15, 1.3, 2.2, true)
        )

        println(
            "Costo total de rentables: %.2f".format(
                listaContenidoDigital
                    .auditarRentabilidad(
                        { it -> it.duracionMinutos < 60 },
                        { it -> it.calcularCostoProduccion() })
            )
        )

        listaContenidoDigital.obtenerReporteRelevancia(
            { it -> it.factorRelevancia >= 2.5 },
            { it -> "Relevante: ${it.nombre}" }, { it -> "Irrelevante: ${it.nombre}" })
            .forEach(::println)

        println("Nombre por minutos: ${listaContenidoDigital.nombrePorDuracion()}")
    } catch (e: ContenidoDigitalException) {
        println(e.message)
    }
}

typealias condicionContenidoDigital = (ContenidoDigital) -> Boolean
typealias contenidoDigitalToDouble = (ContenidoDigital) -> Double
typealias contenidoDigitalToString = (ContenidoDigital) -> String

class ContenidoDigitalException(mensaje: String) : Exception(mensaje)

abstract class ContenidoDigital(
    open var nombre: String,
    open var duracionMinutos: Int,
    open var costoBaseMinuto: Double,
    open var factorRelevancia: Double
) {
    /* El bloque init{} en este caso da error cuando se intenta validar el factor de relevancia
    * de las subclases, ya que esta es abstracta y de esta no se valida, aún no se han declado bien las
    * varibles de las subclases cuando init{} actúa */
    /*init {
        if (factorRelevancia < 1 || factorRelevancia > 5) {
            throw ContenidoDigitalException("El factor de relevancia no es valido de $nombre")
        }
    }*/

    abstract fun calcularComplejidadAdicional(): Double

    fun calcularCostoProduccion(): Double {
        return (duracionMinutos * costoBaseMinuto) + calcularComplejidadAdicional()
    }
}

data class ArticuloBlog(
    override var nombre: String,
    override var duracionMinutos: Int,
    override var costoBaseMinuto: Double,
    override var factorRelevancia: Double,
    var numPaginas: Int
) : ContenidoDigital(nombre, duracionMinutos, costoBaseMinuto, factorRelevancia) {

    init {
        if (factorRelevancia < 1 || factorRelevancia > 5) {
            throw ContenidoDigitalException("El factor de relevancia no es valido de $nombre")
        }
    }

    override fun calcularComplejidadAdicional(): Double {
        return 50.0 + (numPaginas * 10.0)
    }
}

data class VideoTutorial(
    override var nombre: String,
    override var duracionMinutos: Int,
    override var costoBaseMinuto: Double,
    override var factorRelevancia: Double,
    var esAnimacion3D: Boolean
) : ContenidoDigital(nombre, duracionMinutos, costoBaseMinuto, factorRelevancia) {

    init {
        if (factorRelevancia < 1 || factorRelevancia > 5) {
            throw ContenidoDigitalException("El factor de relevancia no es valido de $nombre")
        }
    }

    override fun calcularComplejidadAdicional(): Double {
        return if (esAnimacion3D) {
            300.0
        } else {
            100.0
        }
    }
}

fun List<ContenidoDigital>.auditarRentabilidad(
    criterioRentable: condicionContenidoDigital,
    extractorMetrica: contenidoDigitalToDouble
): Double {
    return this.filter(criterioRentable).sumOf(extractorMetrica)
}

fun List<ContenidoDigital>.obtenerReporteRelevancia(
    condicionExclusiva: condicionContenidoDigital,
    exclusivo: contenidoDigitalToString,
    noExclusivo: contenidoDigitalToString
): List<String> {
    return this.map { it ->
        var s = noExclusivo(it)
        if (condicionExclusiva(it)) {
            s = exclusivo(it)
        }
        s
    }
}

fun List<ContenidoDigital>.nombrePorDuracion(): Map<String, Int> {
    return this.groupBy { it -> it.nombre }
        .mapValues { it -> it.value.sumOf { it -> it.duracionMinutos } }
}
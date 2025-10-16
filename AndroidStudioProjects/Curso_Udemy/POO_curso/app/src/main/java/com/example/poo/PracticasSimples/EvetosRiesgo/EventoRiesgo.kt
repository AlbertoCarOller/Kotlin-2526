package com.example.poo.PracticasSimples.EvetosRiesgo

fun main() {
    // Creamos una lista de eventos de riesgo
    var listaEvento = listOf(
        Sismo("Sismo Catrina", "EEUU", 23.6, 3.3, 1.2),
        Sismo("Sismo Chelu", "España", 99.9, 5.0, 4.6),
        Inundacion("Inundacion Lolitogoku", "España", 67.98, 4.5, 3.3),
        Inundacion("Inundacion Tonio", "Bolivia", 100.0, 1.5, 2.0)
    )

    println(
        "Total eventos condicionados: %.2f".format(
            listaEvento.auditarRiesgoAcumulado(
                { it -> it.probabilidadPorcentaje >= 44.8 },
                { it -> it.probabilidadPorcentaje },
                { it -> it.obtenerImpactoNeto() })
        )
    )
}

abstract class EventoRiesgo(
    open var nombre: String,
    open var zona: String,
    open var probabilidadPorcentaje: Double,
    open var factorImpactoBase: Double
) {
    abstract fun calcularAjusteImpacto(): Double

    fun obtenerImpactoNeto(): Double {
        return this.factorImpactoBase * calcularAjusteImpacto()
    }
}

data class Sismo(
    override var nombre: String,
    override var zona: String,
    override var probabilidadPorcentaje: Double,
    override var factorImpactoBase: Double,
    var magnitud: Double
) : EventoRiesgo(nombre, zona, probabilidadPorcentaje, factorImpactoBase) {

    override fun calcularAjusteImpacto(): Double {
        return magnitud * 0.5
    }
}

data class Inundacion(
    override var nombre: String,
    override var zona: String,
    override var probabilidadPorcentaje: Double,
    override var factorImpactoBase: Double,
    var nivelAguaMetros: Double
) : EventoRiesgo(nombre, zona, probabilidadPorcentaje, factorImpactoBase) {
    override fun calcularAjusteImpacto(): Double {
        return if (nivelAguaMetros >= 2) {
            2.0

        } else {
            0.5
        }
    }
}

fun List<EventoRiesgo>.auditarRiesgoAcumulado(
    condicion: (EventoRiesgo) -> Boolean,
    extractorValor: (EventoRiesgo) -> Double,
    extratorFactor: (EventoRiesgo) -> Double
): Double {
    return this.filter { it -> condicion(it) }
        .sumOf { it -> extratorFactor(it) * extractorValor(it) }
}
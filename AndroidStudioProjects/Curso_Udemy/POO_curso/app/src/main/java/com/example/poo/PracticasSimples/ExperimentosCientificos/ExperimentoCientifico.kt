package com.example.poo.PracticasSimples.ExperimentosCientificos

fun main() {
    // Creamos una lista de experimentos
    var listaExperimentos = listOf(
        EstudioCampo("Chocolate volador", 4, 3.0, 23.4, 4),
        EstudioCampo("Vainilla explosiva", 8, 7.0, 68.94, 7),
        PruebaLaboratorio(
            "Prueba raton volador", 23, 5.7, 190.0,
            true
        ),
        PruebaLaboratorio(
            "Portal a Marte", 45, 10.0, 700.99,
            false
        )
    )

    println(
        "Total puntuacon filtrados: %.2f".format(
            listaExperimentos.auditarPuntuacionTotal(
                { it -> it.duracionHoras > 20 },
                { it -> it.puntuacionBaseResultado })
        )
    )

    println("Nombre por duracion: ${listaExperimentos.nombrePorDuracion()}")
}

abstract class Experimento(
    open var nombre: String,
    open var duracionHoras: Int,
    open var factorRiesgo: Double,
    open var puntuacionBaseResultado: Double
) {
    abstract fun calcularAjusteRiesgo(): Double

    fun obtenerRiesgoNeto(): Double {
        return factorRiesgo * calcularAjusteRiesgo()
    }
}

data class EstudioCampo(
    override var nombre: String,
    override var duracionHoras: Int,
    override var factorRiesgo: Double,
    override var puntuacionBaseResultado: Double,
    var tamanoMuestra: Int
) : Experimento(nombre, duracionHoras, factorRiesgo, puntuacionBaseResultado) {

    override fun calcularAjusteRiesgo(): Double {
        return 0.5 - (tamanoMuestra * 0.1)
    }
}

data class PruebaLaboratorio(
    override var nombre: String,
    override var duracionHoras: Int,
    override var factorRiesgo: Double,
    override var puntuacionBaseResultado: Double,
    var usoSustanciasPeligrosas: Boolean
) : Experimento(nombre, duracionHoras, factorRiesgo, puntuacionBaseResultado) {

    override fun calcularAjusteRiesgo(): Double {
        return if (usoSustanciasPeligrosas) {
            3.0
        } else {
            0.0
        }
    }
}

fun List<Experimento>.auditarPuntuacionTotal(
    condicion: (Experimento) -> Boolean,
    extractorValor: (Experimento) -> Double
): Double {
    return this.filter { it -> condicion(it) }.sumOf { it -> extractorValor(it) }
}

fun List<Experimento>.nombrePorDuracion(): Map<String, Int> {
    return this.groupBy { it -> it.nombre }
        .mapValues { it -> it.value.sumOf { it -> it.duracionHoras } }
}
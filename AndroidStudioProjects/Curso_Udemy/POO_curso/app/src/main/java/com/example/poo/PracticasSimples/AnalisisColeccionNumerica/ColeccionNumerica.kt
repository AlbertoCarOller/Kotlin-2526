package com.example.poo.PracticasSimples.AnalisisColeccionNumerica

fun main() {
    var listaPuntosDatos = listOf<PuntoDato>(
        PuntoDato(45, 1.2),
        PuntoDato(15, 0.8),
        PuntoDato(87, 2.2),
        PuntoDato(11, 1.1),
        PuntoDato(90, 0.43)
    )
    println("Valor principal total: ${listaPuntosDatos.sumarMetricaPersonalizada(::extraerValorPrincipal)}")
    println("Valor ajustado total: ${listaPuntosDatos.sumarMetricaPersonalizada { it -> it.valorPrincipal * it.multiplicadorDeRiesgo }}")
}

fun extraerValorPrincipal(puntoDato: PuntoDato) = puntoDato.valorPrincipal.toDouble()

class PuntoDatoException(var mensaje: String) : Exception(mensaje)
data class PuntoDato(var valorPrincipal: Int, var multiplicadorDeRiesgo: Double) {
    init {
        if (valorPrincipal < 1 || valorPrincipal > 100) {
            throw PuntoDatoException("El valor principal no es válido")
        }
    }
}

fun List<PuntoDato>.sumarMetricaPersonalizada(fn: (PuntoDato) -> Double): Double {
    return this.sumOf { it -> fn(it) }
}
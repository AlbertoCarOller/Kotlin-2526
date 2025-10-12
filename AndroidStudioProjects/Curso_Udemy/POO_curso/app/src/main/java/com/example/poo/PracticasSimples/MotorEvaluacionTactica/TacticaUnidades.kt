package com.example.poo.PracticasSimples.MotorEvaluacionTactica

fun main() {
    var listaUnidadTactica = listOf(
        UnidadTactica("Unidad A", 50, 50, 34.6, false),
        UnidadTactica("Unidad Ariete", 100, 65, 100.0, true),
        UnidadTactica("Unidad Refuerzo", 70, 45, 50.0, false),
        UnidadTactica("Unidad Escudo", 15, 100, 100.0, true)
    )

    println(
        "Potencial total de combate: %.2f"
            .format(listaUnidadTactica.calcularPotencialCombateTotal { it -> it.puntosAtaque.toDouble() })
    )

    listaUnidadTactica.obtenerReporteMoral(
        { it -> it.moral <= 50 },
        { it -> formateoMoralBaja(it) },
        { it -> formateoMoralAlta(it) })
        .forEach(::println)

    println(
        "Filtrados y calcular probabilidad: %.2f".format(
            listaUnidadTactica.filtrarYCalcularProbabilidad(
                { it -> it.esLider },
                { it -> it.puntosAtaque * it.moral },
                { it -> it.moral })
        )
    )
}

fun formateoMoralAlta(unidadTactica: UnidadTactica) = "Moral alta: ${unidadTactica.nombre}"
fun formateoMoralBaja(unidadTactica: UnidadTactica) = "Moral baja: ${unidadTactica.nombre}"

typealias unidadTacticaToDouble = (UnidadTactica) -> Double
typealias unidadTacticaToString = (UnidadTactica) -> String
typealias condicionUnidadTactica = (UnidadTactica) -> Boolean

data class UnidadTactica(
    var nombre: String,
    var puntosAtaque: Int,
    var puntosDefensa: Int,
    var moral: Double,
    var esLider: Boolean
)

fun List<UnidadTactica>.calcularPotencialCombateTotal(calcularPotencial: unidadTacticaToDouble): Double {
    return this.sumOf { it -> calcularPotencial(it) }
}

fun List<UnidadTactica>.obtenerReporteMoral(
    criterioDesmoralizacion: condicionUnidadTactica,
    formateoAlerta: unidadTacticaToString,
    formateoEstable: unidadTacticaToString
): List<String> {
    return this.map { it ->
        var s = formateoEstable(it)
        if (criterioDesmoralizacion(it)) {
            s = formateoAlerta(it)
        }
        s
    }.toList()
}

fun List<UnidadTactica>.filtrarYCalcularProbabilidad(
    criterioFiltro: condicionUnidadTactica,
    extractorProbabilidad: unidadTacticaToDouble,
    extractorPeso: unidadTacticaToDouble
): Double {
    return (this.filter { it -> criterioFiltro(it) }.sumOf { it -> extractorProbabilidad(it) } /
            this.filter { it -> criterioFiltro(it) }.sumOf { it -> extractorPeso(it) })
}
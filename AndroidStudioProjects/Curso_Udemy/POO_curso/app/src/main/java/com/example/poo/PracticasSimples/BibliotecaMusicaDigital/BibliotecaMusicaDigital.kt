package com.example.poo.PracticasSimples.BibliotecaMusicaDigital

fun main() {
    // Creamos una lista de pistas
    var listaPistas = listOf(
        PistaMusical(
            "Wao", "Sech", 156,
            2023, 45.5, true
        ),
        PistaMusical(
            "Flatline", "Justin Bieber", 201,
            2012, 67.7, false
        ),
        PistaMusical(
            "Heartbroken", "Justin Bieber", 189,
            2013, 78.8, true
        ),
        PistaMusical(
            "911", "Sech", 134,
            2020, 99.6, false
        )
    )
    println("Suma métrica: ${listaPistas.calcularMetricaAcumulada { it -> it.duracionSegundos.toDouble() }}")
    listaPistas.obtenerAnalisisAntiguedad(
        { it -> it.esExplicita },
        { it -> "Explicita: ${it.titulo}" },
        { it -> "No explicita: ${it.titulo}" }).forEach(::println)

    println(
        "Obtener promedio: %.2f".format(
            listaPistas.filtrarYObtenerPromedio(
                { it -> it.esExplicita },
                { it -> it.popularidadScore },
                { it -> it.duracionSegundos.toDouble() })
        )
    )
}

typealias pistaMusicalToDouble = (PistaMusical) -> Double
typealias condicionPistaMusical = (PistaMusical) -> Boolean
typealias pistaMusicalToString = (PistaMusical) -> String

data class PistaMusical(
    var titulo: String, var artista: String, var duracionSegundos: Int,
    var anioLanzamiento: Int, var popularidadScore: Double, var esExplicita: Boolean
)

fun List<PistaMusical>.calcularMetricaAcumulada(extractorMetrica: pistaMusicalToDouble): Double {
    return this.sumOf { it -> extractorMetrica(it) }
}

fun List<PistaMusical>.obtenerAnalisisAntiguedad(
    condicion: condicionPistaMusical,
    formateoTrue: pistaMusicalToString,
    formateoFalse: pistaMusicalToString
): List<String> {
    return this.map { it ->
        var s = formateoFalse(it)
        if (condicion(it)) {
            s = formateoTrue(it)
        }
        s
    }.toList()
}

fun List<PistaMusical>.filtrarYObtenerPromedio(
    criterioFiltro: condicionPistaMusical,
    extractorValor: pistaMusicalToDouble,
    extractorPeso: pistaMusicalToDouble
): Double {
    return (this.filter { it -> criterioFiltro(it) }
        .sumOf { it -> extractorValor(it) * extractorPeso(it) }
            / this.filter { it -> criterioFiltro(it) }.sumOf { it -> extractorPeso(it) })
}
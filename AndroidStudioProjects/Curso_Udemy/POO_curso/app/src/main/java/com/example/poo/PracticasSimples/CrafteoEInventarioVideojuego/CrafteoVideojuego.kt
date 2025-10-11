package com.example.poo.PracticasSimples.CrafteoEInventarioVideojuego

fun main() {
    var listaRecurso = listOf(
        Recurso("Pan", 4, 34.4, 2, true),
        Recurso("Oro", 2, 78.3, 7, false),
        Recurso("Diamante", 3, 87.4, 8, false),
        Recurso("Tomate", 6, 24.9, 5, true)
    )

    println(
        "Total (stock * valor): %.2f".format(
            listaRecurso
                .calcularValorTotalStock(::stockPorRereza)
        )
    )

    listaRecurso.obtenerAnalisisUtilidad(
        { it -> it.esConsumible },
        { it -> "Consumible: ${it.nombre}" }, { it -> "No consumible: ${it.nombre}" })
        .forEach(::println)

    println(
        "Promedio: %.2f".format(
            listaRecurso.filtrarYObtenerPromedio(
                { it -> !it.esConsumible },
                { it -> it.cantidadStock * it.valorBase },
                { it -> it.valorBase * it.cantidadStock })
        )
    )
}

typealias recursoToDouble = (Recurso) -> Double
typealias condicionRecurso = (Recurso) -> Boolean
typealias recursoToString = (Recurso) -> String

// Creamos una función simple, se crea como si fuera una varible, devolviendo lo calculado (Double)
fun stockPorRereza(recurso: Recurso) = recurso.cantidadStock * recurso.nivelRareza.toDouble()

class RecursoException(mensaje: String) : Exception(mensaje)

data class Recurso(
    var nombre: String,
    var cantidadStock: Int,
    var valorBase: Double, // -> Se refiere al peso
    var nivelRareza: Int, // -> Se refiere al valor
    var esConsumible: Boolean
) {
    init {
        if (nivelRareza < 1 || nivelRareza > 10) {
            throw RecursoException("El nivel de rareza no es válido")
        }
    }
}

fun List<Recurso>.calcularValorTotalStock(calcularValorUnitario: recursoToDouble): Double {
    return this.sumOf { it -> calcularValorUnitario(it) }
}

fun List<Recurso>.obtenerAnalisisUtilidad(
    condicion: condicionRecurso,
    cumple: recursoToString,
    noCumple: recursoToString
): List<String> {
    return this.map { it ->
        var s = noCumple(it)
        if (condicion(it)) {
            s = cumple(it)
        }
        s
    }
}

fun List<Recurso>.filtrarYObtenerPromedio(
    filtro: condicionRecurso,
    extractorValor: recursoToDouble,
    extractorPeso: recursoToDouble
): Double {
    return (this.filter { it -> filtro(it) }
        .sumOf { it -> extractorValor(it) } / this.sumOf { it -> extractorPeso(it) })
}
package com.example.poo.PracticasSimples.CalculadoraNutricional

fun main() {
    // Creamos los ingredientes
    var leche = Ingrediente("Leche", 2.2, 120)
    var azucar = Ingrediente("Azucar", 4.3, 285)
    var cacao = Ingrediente("Cacao", 5.2, 87)
    var almendras = Ingrediente("Almendras", 0.6, 59)
    // Creamos una receta
    var receta = Receta(
        listOf(leche, azucar, cacao, almendras)
    )
    // Devolvemos las calorías rotales de una receta
    println("Calorias totales de recceta: ${receta.calcularTotal { it -> (it.cantidadGramos * it.caloriasPorGramo) }}")

    // Mostramos una lista de los nombres de los ingredientes que tengan más de 100 gramos
    receta.listaIngredientes.filter { it -> it.cantidadGramos > 100 }.map { it -> it.nombre }
        .forEach { it -> println(it) }

    // Declaramos una lambda para dado un ingrediente calculemos las calorias totales
    var caloriasTotales: (Ingrediente) -> Double = { it -> it.cantidadGramos * it.caloriasPorGramo }
    println("Calorias totales de ${cacao.nombre}: ${caloriasTotales(cacao)}")
}

// Creamos una data class de ingrediente para almacenar información básica de estos
data class Ingrediente(var nombre: String, var caloriasPorGramo: Double, var cantidadGramos: Int)

// Creamos la clase Receta
class Receta(var listaIngredientes: List<Ingrediente>) {
    fun calcularTotal(extractor: (Ingrediente) -> Double): Double {
        return listaIngredientes.sumOf { it -> extractor(it) }
    }
}
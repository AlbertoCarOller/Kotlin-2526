package com.example.poo.PracticasSimples.SistemaEvaluacionRecetas

fun main() {
    // Añadimos ingredientes a la lista que contiene 'ConsejoNutricional'
    ConsejoNutricional.agregarIngrediente(Ingrediente("Cebolla", 4.4, 26))
    ConsejoNutricional.agregarIngrediente(Ingrediente("Zanahoria", 2.2, 63))
    ConsejoNutricional.agregarIngrediente(Ingrediente("Naranja", 3.2, 22))
    ConsejoNutricional.agregarIngrediente(Ingrediente("Plátano", 5.32, 12))
    ConsejoNutricional.agregarIngrediente(Ingrediente("Pescado", 3.33, 31))
    ConsejoNutricional.agregarIngrediente(Ingrediente("Chocolate", 20.34, 70))
    ConsejoNutricional.agregarIngrediente(Ingrediente("Vainilla", 10.0, 43))

    // Recorremos los ingredientes y vemos si son aptos para dietas o no
    ConsejoNutricional.getListaIngredientes()
        .forEach { it -> ConsejoNutricional.darRecomendacion(it) }

    // Sumamos el total de calorías de todos los ingredientes
    println(
        "Total de gramos de todos los ingredientes: ${
            ConsejoNutricional.getListaIngredientes()
                .calcularTotal { it -> it.cantidadGramos * it.caloriasPorGramo }
        }"
    )

    // Imprimimos el nombre de los ingredientes que sean aptos
    println("-----Ingredientes aptos-----")
    ConsejoNutricional.getListaIngredientes().filter { it -> it.esAptoParaDieta(150) }
        .map { it -> it.nombre }.forEach { it -> println(it) }
}

interface Evaluable {
    fun obtenerPuntuajeBaseToxico(): Int
    fun esAptoParaDieta(maxCalorias: Int): Boolean
}

data class Ingrediente(var nombre: String, var caloriasPorGramo: Double, var cantidadGramos: Int) :
    Evaluable {
    override fun obtenerPuntuajeBaseToxico(): Int {
        return 5
    }

    override fun esAptoParaDieta(maxCalorias: Int): Boolean {
        return (this.cantidadGramos * caloriasPorGramo) < maxCalorias
    }
}

object ConsejoNutricional {
    private var listaIngredientes = mutableListOf<Ingrediente>()

    internal fun getListaIngredientes(): List<Ingrediente> {
        return this.listaIngredientes
    }

    fun darRecomendacion(ingrediente: Ingrediente) {
        if (ingrediente.esAptoParaDieta(200)) {
            println("${ingrediente.nombre} es apto para una dieta")
        } else {
            println("${ingrediente.nombre} no es apto para una dieta")
        }
    }

    fun agregarIngrediente(ingrediente: Ingrediente) {
        listaIngredientes.add(ingrediente)
    }
}

fun List<Ingrediente>.calcularTotal(selectorValor: (Ingrediente) -> Double): Double {
    return this.sumOf { it -> selectorValor(it) }
}
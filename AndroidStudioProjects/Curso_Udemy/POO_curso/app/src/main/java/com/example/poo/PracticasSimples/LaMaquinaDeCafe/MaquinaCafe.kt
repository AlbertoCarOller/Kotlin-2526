package com.example.poo.PracticasSimples.LaMaquinaDeCafe

import java.lang.Exception

fun main() {
    try {
        var listaIngredientes =
            listOf<Ingrediente>(
                Liquido("Chocolate", 2, 37, 3.4),
                Polvo("Vainilla en polvo", 1, true, 2.2),
                Polvo("Plátano en polvo", 8, false, 1.9),
                Liquido("Leche de almendras", 1, 28, 5.9)
            )
        Cafetera.prepararBebida(listaIngredientes)
        println("Coste total por unidad: ${Cafetera.calcularTotal(listaIngredientes)}")
    } catch (e: MaquinaCafeException) {
        println("Error: ${e.message}")
    }
}

// Creamos una excepción personalizada
class MaquinaCafeException(mensaje: String) : Exception(mensaje)

interface Preparable {
    var costePorUnidad: Double
    fun preparar()
}

abstract class Ingrediente() : Preparable {
    protected var nombre: String = ""
    protected var cantidadEnStock: Int = 0

    protected var sinStock = false

    // Creamos el segundo constructor simplemente para probarlo
    constructor(nombreS: String, cantidadStock: Int) : this() {
        if (nombreS.isEmpty() || nombreS.isBlank()) {
            throw MaquinaCafeException("El nombre no es valido")
        }
        this.nombre = nombreS
        if (cantidadStock < 0) {
            this.cantidadEnStock = 0
            sinStock = true
        } else {
            this.cantidadEnStock = cantidadStock
        }
    }

    internal fun getSinStock(): Boolean {
        return this.sinStock
    }

    fun mostrarEstado() {
        println("Nombre: ${this.nombre}, Stock: ${this.cantidadEnStock}")
    }
}

class Liquido(
    nombre: String,
    cantidadEnStock: Int,
    private var temperatura: Int,
    override var costePorUnidad: Double
) : Ingrediente(nombre, cantidadEnStock) {

    override fun preparar() {
        if (cantidadEnStock == 0) {
            println("No se ha podido vertir $nombre, no queda")
            sinStock = true
        } else {
            println("Se está vertiendo $nombre a temperatura $temperatura")
            cantidadEnStock--
        }
    }
}

class Polvo(
    nombre: String, cantidadEnStock: Int, private var esSaludable: Boolean,
    override var costePorUnidad: Double
) : Ingrediente(nombre, cantidadEnStock) {

    override fun preparar() {
        if (cantidadEnStock == 0) {
            println("No se ha podido disolver $nombre, no queda")
            sinStock = true
        } else {
            if (esSaludable) {
                println("$nombre se disuelve bien")

            } else {
                println("$nombre no se disuelve correctamente")
            }
            cantidadEnStock--
        }
    }
}

object Cafetera {
    fun prepararBebida(lista: List<Ingrediente>) {
        if (lista.any { e -> e.getSinStock() }) {
            println("No hay stock para hacer la bebida")
        } else {
            lista.forEach { e -> e.preparar() }
        }
    }

    fun calcularTotal(list: List<Preparable>): Double {
        return list.sumOf { e -> e.costePorUnidad }
    }
}
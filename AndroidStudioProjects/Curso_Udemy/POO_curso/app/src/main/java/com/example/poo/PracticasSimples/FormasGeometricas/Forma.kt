package com.example.poo.PracticasSimples.FormasGeometricas

import kotlin.math.pow

fun main() {
    // Creamos istancias de las formas
    var circulo = Circulo("CirculoAnton", 1.5)
    var cuadrado = Cuadrado("CuadradoSerpaio", 4.5)

    // Utilizamos sus métodos
    with(circulo) {
        println("Area círculo: ${this.calcularArea()}")
    }

    with(cuadrado) {
        println("Area cuadrado: ${this.calcularArea()}")
    }
}

interface Calculable {
    // Creamos la función para calcular el area de una forma
    fun calcularArea(): Double
}

abstract class Forma(protected var nombre: String) : Calculable

class Cuadrado(nombre: String, private var lado: Double) : Forma(nombre) {
    // Implementamos la función de la interfaz
    override fun calcularArea(): Double {
        return lado.pow(2.0)
    }
}

class Circulo(nombre: String, private var radio: Double) : Forma(nombre) {
    // Implementamos la función de la interfaz
    override fun calcularArea(): Double {
        return Math.PI * (radio * radio)
    }
}
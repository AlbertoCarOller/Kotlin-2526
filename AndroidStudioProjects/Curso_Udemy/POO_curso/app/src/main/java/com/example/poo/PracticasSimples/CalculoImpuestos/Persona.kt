package com.example.poo.PracticasSimples.CalculoImpuestos

fun main() {
    // Creamos el gestor
    var gestorImpuestos = GestorImpuestos()
    gestorImpuestos.procesarImpuestos(
        listOf(
            Empleado("Serapio", 22000.0, 1200.0),
            Autonomo("Atisbedo", 45000.8, 5600.9))
    )
}

interface Contribuidor {
    // Creamos una función que va a calcular los impuestos
    fun calcularImpuesto(): Double
}

abstract class Persona(private var nombre: String, protected var ingresoAnual: Double) :
    Contribuidor {
    // Hacemos un get del nombre
    internal fun getNombre(): String {
        return this.nombre
    }
}

class Empleado(nombre: String, ingresoAnual: Double, private var deducciones: Double) :
    Persona(nombre, ingresoAnual) {
    // Implementamos la función de la interfaz
    override fun calcularImpuesto(): Double {
        return (ingresoAnual - deducciones) * 0.15
    }
}

class Autonomo(nombre: String, ingresoAnual: Double, private var gastos: Double) :
    Persona(nombre, ingresoAnual) {
    // Implementamos la función de la interfaz
    override fun calcularImpuesto(): Double {
        return (ingresoAnual - gastos) * 0.15
    }
}

class GestorImpuestos() {
    // Creamos una función que va a mostrar los contribuyetes y lo que tiene que pagar
    fun procesarImpuestos(lista: List<Persona>) {
        for (i in lista) {
            println("Contribuyente: ${i.getNombre()} - Contribuido: ${i.calcularImpuesto()} EUR")
        }
    }
}
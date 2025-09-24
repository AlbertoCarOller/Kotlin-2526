package com.example.poo.PracticasSimples.ServicioMensajeria

fun main() {
    GestorEnvios.agregarPaquete(Documento("98987", 4.4, true))
    GestorEnvios.agregarPaquete(Caja("73549", 9.9, 10.5))
    GestorEnvios.agregarPaquete(Documento("23075", 2.2, false))

    GestorEnvios.mostrarCostoEnvio()
    GestorEnvios.procesarTodosLosEnvios()
    println("Calcular costo envío de todos: ${GestorEnvios.calcularCostoTodosEnvios()}")
}

abstract class Paquete(var id: String, var pesoEnGramos: Double) {
    open fun calcularCostoEnvio(): Double {
        return 0.0
    }

    override fun toString(): String {
        return "Paquete(id='$id', pesoEnGramos=$pesoEnGramos)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Paquete

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

class Documento(id: String, pesoEnGramos: Double, private var esConfidencial: Boolean) :
    Paquete(id, pesoEnGramos) {
    override fun calcularCostoEnvio(): Double {
        return if (esConfidencial) {
            15.0
        } else {
            5.0
        }
    }

    override fun toString(): String {
        return super.toString().plus("Documento(esConfidencial=$esConfidencial)")
    }

}

class Caja(id: String, pesoEnGramos: Double, private var volumenEnCmCubicos: Double) :
    Paquete(id, pesoEnGramos) {
    override fun calcularCostoEnvio(): Double {
        return 10.0 + (0.02 * volumenEnCmCubicos)
    }

    override fun toString(): String {
        return super.toString().plus("Caja(volumenEnCmCubicos=$volumenEnCmCubicos)")
    }

}

object GestorEnvios {
    var listaPaquetes = mutableListOf<Paquete>()

    fun agregarPaquete(paquete: Paquete) {
        if (!listaPaquetes.contains(paquete)) {
            listaPaquetes.add(paquete)
        }
    }

    fun procesarTodosLosEnvios() {
        listaPaquetes.forEach { paquete -> println("ID: ${paquete.id}, Peso: ${paquete.pesoEnGramos}") }
    }

    fun mostrarCostoEnvio() {
        listaPaquetes.forEach { paquete -> println("${paquete.id}, Costo envío: ${paquete.calcularCostoEnvio()}") }
    }

    fun calcularCostoTodosEnvios(): Double {
        return listaPaquetes.sumOf { paquete -> paquete.calcularCostoEnvio() }
    }
}
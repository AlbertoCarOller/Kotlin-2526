package com.example.poo.PracticasSimples.GestorPowerUps

fun main() {
    var megasalto = Megasalto("Megasalto conejo", 9.3)
    var megasalto1 = Megasalto("Megasalto del dragón", 10.8)
    var invencibilidad = Invencibilidad("Invencibilidad nivel 1", 2.2)
    var invencibilidad1 = Invencibilidad("Invencibilidad nivel 2", 12.2)

    with(MochilaPowerUps) {
        this.agregarPowerUp(megasalto)
        this.agregarPowerUp(megasalto1)
        this.agregarPowerUp(invencibilidad)
        this.agregarPowerUp(invencibilidad1)

        this.mostrarEfectos()
    }
}

class GestorPowerUpsException(mensaje: String) : Exception(mensaje)

interface Usable {
    var aplicarEfecto: () -> String
}

abstract class PowerUp(var nombre: String, var duracionSegundos: Double) : Usable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PowerUp

        return nombre == other.nombre
    }

    override fun hashCode(): Int {
        return nombre.hashCode()
    }

    override fun toString(): String {
        return "PowerUp(nombre='$nombre', duracionSegundos=$duracionSegundos)"
    }

}

class Megasalto(nombre: String, duracionSegundos: Double) : PowerUp(nombre, duracionSegundos) {
    override var aplicarEfecto: () -> String = { "Preparando $nombre..." }
    override fun toString(): String {
        return "Megasalto(nombre='$nombre', duracionSegundos=$duracionSegundos, aplicarEfecto=$aplicarEfecto)"
    }

}

class Invencibilidad(nombre: String, duracionSegundos: Double) : PowerUp(nombre, duracionSegundos) {
    override var aplicarEfecto: () -> String = { "Preparando $nombre..." }
    override fun toString(): String {
        return "Invencibilidad(nombre='$nombre', duracionSegundos=$duracionSegundos, aplicarEfecto=$aplicarEfecto)"
    }

}

object MochilaPowerUps {
    var listaPowerUps = mutableListOf<PowerUp>()

    fun agregarPowerUp(powerUp: PowerUp) {
        if (listaPowerUps.any { p -> p == powerUp }) {
            throw GestorPowerUpsException("No se permiten añadir power ups repetidos")
        }
        listaPowerUps.add(powerUp)
    }

    fun mostrarEfectos() {
        listaPowerUps.filter { p -> p.duracionSegundos > 10.0 }.map { p -> p.aplicarEfecto() }
            .forEach { p -> println(p) }
    }
}
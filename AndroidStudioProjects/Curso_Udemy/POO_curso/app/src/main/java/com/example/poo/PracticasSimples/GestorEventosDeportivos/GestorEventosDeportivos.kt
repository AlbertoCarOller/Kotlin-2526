package com.example.poo.PracticasSimples.GestorEventosDeportivos

fun main() {
    var carrera = Carrera("Pepete", "España", 8.2)
    var carrera1 = Carrera("Walker", "USA", 9.6)
    var carrera2 = Carrera("Bermudo", "España", 7.6)
    var lanzamiento = Lanzamiento("Antonio", "Ecuador", 12.3)
    var lanzamiento1 = Lanzamiento("Eugenio", "Ecuador", 31.2)

    with(GestorCompeticiones) {
        this.registrarAtleta(carrera)
        this.registrarAtleta(carrera1)
        this.registrarAtleta(carrera2)
        this.registrarAtleta(lanzamiento)
        this.registrarAtleta(lanzamiento1)

        this.analizarPorPais(20.0).forEach { it -> println(it) }
    }
}

interface Puntuable {
    var puntosBase: Int
    var puntuacionFinal: () -> Double
}

abstract class Atleta(var nombre: String, var pais: String) : Puntuable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Atleta

        return nombre == other.nombre
    }

    override fun hashCode(): Int {
        return nombre.hashCode()
    }
}

class Carrera(nombre: String, pais: String, var tiempoEnSegundos: Double) : Atleta(nombre, pais) {
    override var puntosBase: Int = 100

    override var puntuacionFinal: () -> Double = {
        var puntosFinalC = 0.0
        if (tiempoEnSegundos < 10) {
            puntosFinalC = puntosBase + (10 - tiempoEnSegundos) * 15
        } else {
            puntosFinalC = puntosBase - (tiempoEnSegundos / 2)
        }
        puntosFinalC
    }
}

class Lanzamiento(nombre: String, pais: String, var distanciaEnMetros: Double) :
    Atleta(nombre, pais) {
    override var puntosBase: Int = 50
    override var puntuacionFinal: () -> Double = { (puntosBase + (distanciaEnMetros * 3)) }
}

object GestorCompeticiones {
    var listaAtletas = mutableListOf<Atleta>()
    fun registrarAtleta(atleta: Atleta) {
        if (!listaAtletas.contains(atleta)) {
            listaAtletas.add(atleta)
        }
    }

    fun analizarPorPais(puntosMinimos: Double): Map<String, Int> {
        /*return listaAtletas.filter { a -> a.puntuacionFinal() == puntosMinimos }
            .groupBy { it -> it.pais }.mapValues { it -> it.value.size }*/

        /* La diferencia entre .groupBy y .groupingBy es que el primero agrupa de forma
        * menos eficiente y el value es una lista del objeto, en cambio el segundo
        * necesita una función de agrupación de por medio, para saber como agrupar los values */
        return listaAtletas.filter { it -> it.puntuacionFinal() > puntosMinimos }
            .groupingBy { it -> it.pais }.eachCount()
    }
}
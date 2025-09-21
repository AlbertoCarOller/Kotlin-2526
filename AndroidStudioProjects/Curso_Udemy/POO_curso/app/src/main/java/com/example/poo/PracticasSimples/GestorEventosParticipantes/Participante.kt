package com.example.poo.PracticasSimples.GestorEventosParticipantes

fun main() {
    // Creamos el gestor
    var gestorEventos = GestorEventos()
    gestorEventos.agregarParticipante(ParticipanteEstandar("Serapio", 21))
    gestorEventos.agregarParticipante(ParticipanteVIP("Atisbedo", 19))
    gestorEventos.agregarParticipante(ParticipanteEstandar("RespicioGodefrio", 22))
    gestorEventos.agregarParticipante(ParticipanteVIP("Anton", 29))

    println("Total: ${gestorEventos.calcularIngresarTotal()} EUR")

    gestorEventos.mostrarListaParticipantes()
}

interface Participante {
    fun calcularCostoInscripcion(): Double
    fun mostrarBeneficios()
}

abstract class Asistente(protected var nombre: String, protected var edad: Int) : Participante {
    internal fun getNombre(): String {
        return this.nombre
    }
}

class ParticipanteEstandar(nombre: String, edad: Int) : Asistente(nombre, edad) {
    override fun calcularCostoInscripcion(): Double {
        return 50.0
    }

    override fun mostrarBeneficios() {
        println("Los beneficios básicos: entrada y dos bebidas gratis")
    }
}

class ParticipanteVIP(nombre: String, edad: Int) : Asistente(nombre, edad) {
    override fun calcularCostoInscripcion(): Double {
        return if (edad >= 25) {
            200.0

        } else {
            200.0 - (200.0 * 0.2)
        }
    }

    override fun mostrarBeneficios() {
        println("Los beneficios VIP de: entrada, bebidas ilimitadas y acceso a primera fila")
    }
}

class GestorEventos() {
    private var listaParticipantes: MutableList<Participante> = mutableListOf()

    fun agregarParticipante(participante: Participante) {
        listaParticipantes.add(participante)
    }

    fun calcularIngresarTotal(): Double {
        var total = 0.0
        // 'sumOf' es flujos lambda, devuelve un Double en este caso, suma todos los resultados que da la función
        return listaParticipantes.sumOf { participante -> participante.calcularCostoInscripcion() }
    }

    fun mostrarListaParticipantes() {
        /* Con 'is' comprobamos que participante es Asistente por lo que puede llamar a '.getNombre'
        * ya que dentro del if, el participante se ha casteado a 'Asistente' */
        listaParticipantes.forEach { participante -> if (participante is Asistente) {
            println("Participante: ${participante.getNombre()}")} }
    }
}
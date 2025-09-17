package com.example.poo.PracticasSimples.BloquesK

fun main() {
    // El bloque run permite devolver el valor para la variable y ejecutar más instrucciones
    var nombre = run {
        println("Introduce el nombre")
        readLine()
    }
    // El bloque also permite llamar dentro del bloque a la variable con it y realizar acciones
    var lista = mutableListOf<Int>(1,2,3,4,5).also {
        it.add(6)
    }
    // El bloque let se ejecuta si el nombre no es null
    var nombre1: String? = "Xavi"
    nombre1?.let {
        println("El nombre tiene ${it.length} caracteres")
    }
    // El bloque apply sirve para configurar los objetos
    var persona = Persona("Anton", 37)
    persona.apply {
        this.setNombre("Antonio")
        this.setEdad(38)
    }
    // El bloque with sirve para referenciar al objeto dentro del bloque con this
    with(persona) {
        println("${this.getNombre()} tiene ${this.getEdad()}")
    }
}

class Persona(private var nombre: String, private var edad: Int) {

    // Hacemos los get y set
    internal fun getNombre(): String {
        return this.nombre
    }

    internal fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    internal fun getEdad(): Int {
        return this.edad
    }

    internal fun setEdad(edad: Int) {
        this.edad = edad
    }
}
package com.example.poo.PracticasSimples.Mascotas

abstract class Mascota(private val nombre: String, private var edad: Int) {

    // Creamos los get y set
    internal fun getNombre(): String {
        return this.nombre
    }

    internal fun getEdad(): Int {
        return this.edad
    }

    internal fun setEdad(edad: Int) {
        this.edad = edad
    }

    // Creamos una función que va mostrar por pantalla el sonido que hace el animal
    open fun emitirSonido() {
    }
}

class Perro(nombre: String, edad: Int, private val raza: String) : Mascota(nombre, edad) {

    // Creamos el get de raza
    internal fun getRaza(): String {
        return this.raza
    }

    // Creamos una función que va a simular que el perro está corriendo
    fun correr() {
        println("Soy ${this.getNombre()}, estoy corriendo")
    }

    // Modificamos la función 'emitirSonido'
    override fun emitirSonido() {
        println("Guau, guau!")
    }
}

class Gato(nombre: String, edad: Int, private var calvo: Boolean) : Mascota(nombre, edad) {

    // Creamos el get y set de 'calvo'
    internal fun getCalvo(): Boolean {
        return this.calvo
    }

    internal fun setCalvo(calvo: Boolean) {
        this.calvo = calvo
    }

    // Creamos una función que va a simular al gato limpiándose
    fun limpiar() {
        println("Hola soy ${this.getNombre()} y me estoy limmpiando")
    }

    // Modificamos la función 'emitirSonido'
    override fun emitirSonido() {
        println("Miau, miau!")
    }
}
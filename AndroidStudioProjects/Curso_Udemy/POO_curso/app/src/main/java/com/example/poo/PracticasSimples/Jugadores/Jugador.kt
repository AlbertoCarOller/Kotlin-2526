package com.example.poo.PracticasSimples.Jugadores

class Jugador(private var nombre: String) {
    private var puntuacion: Int = 0

    // Creamos los get y set
    fun getNombre(): String {
        return this.nombre
    }

    fun getPuntuacion(): Int {
        return this.puntuacion
    }

    // Creamos los set
    fun setNombre(n: String) {
        this.nombre = n
    }

    fun setPuntuacion(p: Int) {
        this.puntuacion = p
    }

    // Creamos una función que va a simular tirar un dado
    fun tirarDado(): Int {
        return (Math.random() * 6).toInt() + 1
    }
}
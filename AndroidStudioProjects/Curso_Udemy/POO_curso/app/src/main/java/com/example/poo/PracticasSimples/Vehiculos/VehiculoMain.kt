package com.example.poo.PracticasSimples.Vehiculos

fun main() {
    // Creamos un coche y una moto
    val coche = Coche("Ford", 2007, 4)
    val moto = Motocicleta("Mercedes", 2011, true)

    // Probamos sus métodos
    coche.bajarVentana("d")
    println(coche.mostrarInformacion())
    println(moto.mostrarInformacion())
}
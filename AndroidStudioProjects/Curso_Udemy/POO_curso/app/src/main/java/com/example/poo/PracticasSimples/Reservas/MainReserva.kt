package com.example.poo.PracticasSimples.Reservas

fun main() {

    // Creamos una reserva
    var reserva = run {
        println("Hacemos una reserva")
        Reserva("Serapio", 4, 10.2)
    }
    println(reserva.calcularTotal())
    println(reserva.mostrarInformacion())

    // Creamos una reserva vip
    var reservaVIP = run {
        println("Hacemos una reserva vip")
        ReservaVIP("Atisbedo", 12, 20.2, 0.30)
    }
    println(reservaVIP.calcularTotal())
    println(reservaVIP.mostrarInformacion())
}
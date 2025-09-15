package com.example.poo.PracticasSimples.Jugadores

fun main() {
    // Creamos el objeto que contiene la función para comenzar el juego
    val jugadorMain = JugadorMain()
    // Creamos el objeto jugador
    val jugador1 = Jugador("Ale")
    val jugador2 = Jugador("Alberto")
    println("Jugadores: ${jugador1.getNombre()} VS ${jugador2.getNombre()}")

    // Creamos un while para que mientras que no haya ganador se siga jugando
    while (jugador1.getPuntuacion() < 100 && jugador2.getPuntuacion() < 100) {
        jugadorMain.comenzarJuego(jugador1, jugador2)
        if (jugador1.getPuntuacion() == 100) {
            println("Ha ganado ${jugador1.getNombre()}")

        } else if (jugador2.getPuntuacion() == 100) {
            println("Ha ganado ${jugador2.getNombre()}")
        }
    }
}

class JugadorMain {
    // Creamos una función que va a decidir qué jugador ha ganado y sumar la puntuación
    fun comenzarJuego(jugador1: Jugador, jugador2: Jugador) {
        if (jugador1.tirarDado() > jugador2.tirarDado()) {
            jugador1.setPuntuacion(jugador1.getPuntuacion() + 10)

        } else {
            jugador2.setPuntuacion(jugador2.getPuntuacion() + 10)
        }
    }
}
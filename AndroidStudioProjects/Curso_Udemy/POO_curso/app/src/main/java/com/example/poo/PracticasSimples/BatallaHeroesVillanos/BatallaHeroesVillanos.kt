package com.example.poo.PracticasSimples.BatallaHeroesVillanos

fun main() {
    // Creamos dos personajes
    var heroe = Heroe("Chelu")
    var villano = Villano("Atisbedo")

    // Iniciamos la batalla
    gestorDeBatallas.batalla(heroe, villano)
}

interface Atacable {
    // Creamos dos propiedades
    var salud: Int
    var poderDeAtaque: Int

    // Creamos una función que va a reducir la salud del personaje
    fun recibirDanio(danio: Int) {
        if (danio < salud) {
            this.salud -= danio
        } else {
            this.salud = 0
        }
    }
}

abstract class Personaje(protected var nombre: String) : Atacable {
    internal fun getNombre(): String {
        return this.nombre
    }


    // Creamos una función para que se presente
    fun presentarse() {
        println("Hola, soy $nombre")
    }

    fun atacar(enemigo: Atacable) {
        enemigo.recibirDanio(poderDeAtaque)
    }
}

class Heroe(nombre: String) : Personaje(nombre) {
    // Le damos valor a los atributos de la interfaz
    override var salud = 100
    override var poderDeAtaque = 25
}

class Villano(nombre: String) : Personaje(nombre) {
    override var salud = 120
    override var poderDeAtaque = 20
}

object gestorDeBatallas {
    // Creamos una función que va a simular un combate
    fun batalla(atacador1: Personaje, atacador2: Personaje) {
        while (atacador1.salud != 0 && atacador2.salud != 0) {
            var aleatorio = (Math.random() * 2).toInt() + 1
            if (aleatorio == 1) {
                atacador1.atacar(atacador2)
            } else {
                atacador2.atacar(atacador1)
            }
        }
        if (atacador1.salud == 0) {
            println("${atacador2.getNombre()} has ganado")

        } else {
            println("${atacador1.getNombre()} has ganado")
        }
    }
}
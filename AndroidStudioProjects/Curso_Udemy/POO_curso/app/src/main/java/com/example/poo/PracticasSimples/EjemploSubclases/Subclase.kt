package com.example.poo.PracticasSimples.EjemploSubclases

fun main() {
    // Creamos una instancia de 'Subclase'
    var subclase = Subclase()
    println(subclase.presentar())

    // Ahora creamos una instancia de 'Anidada'
    var anidada = Subclase.Anidada() // -> Se crea 'anidada' independientemte de la clase 'Padre'
    println(anidada.presentar())

    // Ahora creamos una instancia de 'Interna', IMPORTANTE: ANTES DE CREAR UNA CLASE INNER SE DEBE DE CREAR EL PADRE
    var interna = Subclase().Interna()
    println(interna.presentar())
}

/* Creamos una clase con atributos que no tiene porque tener constructor,
 porque ya le damos el valor dentro de la clase */
class Subclase {
    private var name = "Padre"

    // Creamos una función que va a retornar un saludo
    fun presentar(): String {
        return "Hola, me llamo $name"
    }

    // Creamos una sub-clase, es decir una clase dentro de otra
    class Anidada {
        private var nameAnidada = "Anidada"

        // Creamos una función que va a retornar un saludo, no hay herencia es SUBCLASE, NO HIJA
        fun presentar(): String {
            return "Hola, me llamo $nameAnidada"
        }
    }

    /* Creamos una clase inner (Interna), este tipo de clases puede acceder a los atributos
     de la clase padre, NO LOS HEREDA, SIMPLEMENTE PUEDE ACCEDER */
    inner class Interna {
        private var nameInterna = "Interna"
        fun presentar(): String {return "Hola, me llamo ${this.nameInterna}, hija de $name"}
    }
}
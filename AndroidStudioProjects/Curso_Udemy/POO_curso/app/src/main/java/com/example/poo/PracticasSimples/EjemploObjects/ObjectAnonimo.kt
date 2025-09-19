package com.example.poo.PracticasSimples.EjemploObjects

fun main() {
    /* Creamos una clase anónima, estas se crean directamente a la hora de crear
     la instancia, no tienen constructor, este tipo de clases solo se pueden instanciar
     en el ámbito donde fueron creadas, ya que el tipo son desconocidas de ahí que se
     llamen clases anónimas*/
    var saludador = object {
        fun saludar() {
            println("Hola, soy una clase anónima")
        }
    }
    saludador.saludar()
}
package com.example.poo.PracticasSimples.EjemploObjects

fun main() {
    // Llamamos a la clase singleton
    serapio.saludar()
}

/* Creamos una clase singleton, esta es una clase la cual se instancia directamente como si
 se hubiera creado el objeto y solo puede haber una instancia de esta, no tienen constructor
 (se crea como una especie de clase normal)*/
object serapio {
    var apodo = "sera"
    fun saludar() {
        println("Hola soy $apodo")
    }
}
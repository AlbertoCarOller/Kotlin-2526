package com.example.poo

/* Se debe de poner en "open" para poder "abrir el eslabón", es decir, que se puedan crear hijos,
 que no sea final y ese sea su último "eslabón", porque de forma predeterminada es final, es decir,
  no puede cambiar, se queda así, su forma final*/
open class Person(var name: String = "Anonimo", var passport: String? = null) { /* -> En el constructor debe aparecer el
 nombre y el pasaporte, estos son atributos sin un valor predefinido este último puede ser null */
    // Creamos un atributo predefinido, cada persona predefinidamente va a estar viva
    var alive: Boolean = true

    // Creamos una función que mate a una persona
    fun die() {
        alive = false
    }
}

// Creamos una clase hija de person
class Athlete(name: String, passport: String?, var sport: String) : Person(name, passport)
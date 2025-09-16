package com.example.poo.PracticasSimples.Validador

fun main() {

    // EN ESTE EJERCICIO HAY UN ERROR A PROPÓSITO (línea 21)

    // Pedimos al usuario que introduzca su nombre
    println("Introduce tu nombre")
    val nombre: String? = readLine()
    nombre?.uppercase()
    println("Introduce tu edad")
    /* -> ?. se utiliza cuando queremos utilizar una función
    y el objeto puede ser null */

    // Pedimos al usuario que introduzca su edad
    var edad: Int = readLine()?.toIntOrNull() ?: 20 /* -> Con ?: en caso de que sea null el valor
     de la variable se pone por defecto el valor que esté a la derecha*/

    // Pedimos al usuario el número de teléfono
    println("Introduce tu número de teléfono")
    var numTelefono: Int = readLine()?.toIntOrNull()!! /* -> Con !! le decimos a Kotlin que
     NO ES NI SERÁ NULL (CUIDADO) */

    println("Hola $nombre, tú edad es de $edad y tu número de teléfono es $numTelefono")
}
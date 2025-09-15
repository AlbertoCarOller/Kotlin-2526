package com.example.poo.PracticasSimples.Mascotas

fun main() {

    // Creamos un gato y un perro
    var perro = Perro("Atisbedo", 4, "pastor alemán")
    var gato = Gato("Respicio Godefrio", 2, true)

    // Probamos los diferentes métodos de perro
    perro.emitirSonido()
    perro.correr()

    // Probamos los métodos de gato
    gato.emitirSonido()
    gato.limpiar()

    // Al gato le crece el pelo
    gato.setCalvo(false)

    // Imprimimos un mensaje de si es calvo o no
    if (gato.getCalvo()) {
        println("${gato.getNombre()} es calvo")

    } else {
        println("${gato.getNombre()} no es calvo")
    }
}
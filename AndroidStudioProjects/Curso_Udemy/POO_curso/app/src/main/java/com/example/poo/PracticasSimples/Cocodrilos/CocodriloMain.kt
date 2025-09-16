package com.example.poo.PracticasSimples.Cocodrilos

fun main() {
    // Solicitamos el número de cocodrilos
    var numCocod = 0
    var numCodString: String?
    while (numCocod < 1 || numCocod > 50) {
        println("Introduce el número de cocodrilos")
        numCodString = readLine()
        if (numCodString?.toIntOrNull() != null) {
            numCocod = numCodString.toInt()
        }
    }

    // Introducimos los nombres
    var contador = 0
    var contadorLlorones = 0
    while (numCocod > contador) {
        contador++
        var nombreF: String
        println("Introduce el nombre en el cartel")
        var nombre: String? = readLine()
        if (nombre != null) {
            nombreF = nombre
            if (isLloron(nombreF)) {
                contadorLlorones++
            }
        }
    }

    // Mostramos por pantalla los llorones que hay
    println("Hay $contadorLlorones llorones")
}

fun isLloron(nombre: String): Boolean {
    var contador = 0
    var nombreF = nombre.lowercase()
    if (nombreF.contains("a") || nombreF.contains("á")) {
        contador++
    }

    if (nombreF.contains("e") || nombreF.contains("é")) {
        contador++
    }

    if (nombreF.contains("i") || nombreF.contains("í")) {
        contador++
    }

    if (nombreF.contains("o") || nombreF.contains("ó")) {
        contador++
    }

    if (nombreF.contains("u") || nombreF.contains("ú")) {
        contador++
    }
    if (contador == 2) {
        return true
    }
    return false
}
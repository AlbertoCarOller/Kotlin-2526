package Practicas_simples

fun main() {
    println("Introduce una frase")
    var frase = readLine() ?: "Hola, buenas tardes"
    // Con el '.toRegex' detecta el string como un regex
    var contador = frase.trim().split("\\s+".toRegex()).size
    println("Hay $contador palabras")
}
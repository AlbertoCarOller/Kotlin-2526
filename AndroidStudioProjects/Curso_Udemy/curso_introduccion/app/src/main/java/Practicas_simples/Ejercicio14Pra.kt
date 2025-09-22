package Practicas_simples

fun main() {
    var nombre: String? = readLine()
    println(nombre?.uppercase() ?: "Error")

    var num: Double? = readLine()?.toDoubleOrNull()
    println(num ?: "Error")
}
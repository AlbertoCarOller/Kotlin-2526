package Practicas_simples

fun main() {
    println("Introduce un número")
    var numLeido: Int = readLine()?.toIntOrNull() ?: 1
    for (i in 1 until numLeido) {
        if (i % 5 == 0 && i % 3 == 0) {
            println("PIM PAM")

        } else if (i % 5 == 0) {
            println("PAM")

        } else if (i % 3 == 0) {
            println("PIM PAM")
        } else {
            println(i)
        }
    }
}
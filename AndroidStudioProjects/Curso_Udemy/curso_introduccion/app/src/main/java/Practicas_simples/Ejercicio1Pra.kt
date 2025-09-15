package Practicas_simples

fun main() {
    val ejercicio = Ejercicio1_pra()
    ejercicio.valoracionLetra(7)
}

class Ejercicio1_pra {

    // Creamos una función que va imprimir el tipo de nota, pasada una nota por parámetros
    fun valoracionLetra(nota: Int) {
        when (nota) {
            10 -> println("Sobresaliente")
            7, 8, 9 -> println("Notable")
            5, 6 -> println("Aprobado")
            0, 1, 2, 3, 4 -> println("Suspenso")
            else -> println("La nota no es válida")
        }
    }
}
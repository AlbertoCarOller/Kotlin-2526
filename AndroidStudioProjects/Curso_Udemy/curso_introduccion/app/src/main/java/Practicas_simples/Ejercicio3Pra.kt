package Practicas_simples

fun main() {
    // Creamos el objeto
    val ejercicio3Pra = Ejercicio3_pra()
    // Llamamos a la función y guardamos la nota media
    val media = ejercicio3Pra.calcularMedia(arrayOf(6.6, 9.2, 7.5, 5.25))
    println("La nota media es de $media")
}

class Ejercicio3_pra {
    // Esta función va a devolver la media de unas notas acumuladas en un array
    fun calcularMedia(notas: Array<Double>): Double {
        // En caso de que esté vacío se devuelve 0
        if (notas.isEmpty()) {
            return 0.0
        }
        // Creamos la variable que va a almacenar la suma de las notas
        var sumaNotas = 0.0
        for (i in notas) {
            sumaNotas += i
        }
        return sumaNotas / notas.size
    }
}
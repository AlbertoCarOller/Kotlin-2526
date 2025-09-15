package Practicas_simples

fun main() {
    // Creamos una lista de puntuaciones
    var puntuaciones: MutableList<Double> = mutableListOf(5.5, 9.2, 7.5, 9.9, 2.3, 4.6)
    // Creamos el objeto
    val ejercicio7Pra = Ejercicio7Pra()
    // Guardamos la lista de puntuaciones mayores
    var puntuacionesMayores: MutableList<Double> =
        ejercicio7Pra.devolverMejoresPuntuaciones2(6.6, puntuaciones)
    println("5 mejores puntuaciones: $puntuacionesMayores")
}

class Ejercicio7Pra {
    // Creamos una función que va a devolver una lista con las 5 mejores puntuaciones
    fun devolverMejoresPuntuaciones(puntuacion: Double, puntuaciones: MutableList<Double>): MutableList<Double> {
        var puntuacionesCopia: MutableList<Double> = puntuaciones
        // Añadimos a la lista que vamos a recorrer la nueva puntuación
        puntuacionesCopia = puntuacionesCopia.plus(puntuacion).toMutableList()
        var indice = 0
        var contador = 0
        var puntuacionesMayores: MutableList<Double> = mutableListOf()
        while (contador < 5) {
            var num = -1.0
            for (i in 0 until puntuacionesCopia.size) {
                if (num < puntuacionesCopia[i]) {
                    num = puntuacionesCopia[i]
                    indice = i
                }
            }
            // Añadimos el mayor encontrado
            puntuacionesMayores = puntuacionesMayores.plus(num).toMutableList()
            /* Lo eliminamos de la lista para que en la siguiente iteración del while se busque
             el mayor sin contar el ya encontrado */
            puntuacionesCopia.removeAt(indice)
            contador++
        }
        return puntuacionesMayores
    }

    // Creamos la misma función pero más sencilla
    fun devolverMejoresPuntuaciones2(puntuacion: Double, puntuaciones: MutableList<Double>): MutableList<Double> {
        // Añadimos la puntuación a la lista
        puntuaciones.add(puntuacion)
        // Ordenamos la lista
        puntuaciones.sortDescending()
        // Obtenemos las 5 primeras puntuaciones y las devolvemos
        return puntuaciones.subList(0,5)
    }
}
package Practicas_simples

fun main() {
    // Cremaos el objeto
    val ejercicio4Pra = Ejercicio4_pra()
    // Creamos el mapa del tesoro, los 1 son tesoros y los 0 son casillas vacías
    println("Tesoros: ".plus(ejercicio4Pra.contarTesoros(arrayOf(
        intArrayOf(0,0,0,0,1),
        intArrayOf(0,1,0,0,0),
        intArrayOf(1,1,0,0,1),
        intArrayOf(0,0,1,0,0)))))
}

class Ejercicio4_pra {
    // Creamos un método que va a devolver el número de tesoros que hay
    fun contarTesoros(mapa: Array<IntArray>): Int {
        var tesoros = 0
        for (i in 0 until mapa.size) {
            for (j in 0 until mapa[i].size) {
                if (mapa[i][j] == 1) {
                    tesoros++
                }
            }
        }
        return tesoros
    }
}
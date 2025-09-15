package Practicas_simples

fun main() {
    // Creamos el objeto
    val ejercicio5Pra = Ejercicio5_pra()
    // Creamos el array de números
    val array: IntArray = intArrayOf(2,7,4,9,1,6,8,12,43,90)
    println("Suma: ".plus(ejercicio5Pra.sumarPares(ejercicio5Pra
        .crearPar(ejercicio5Pra.contarPares(array), array))))
}

class Ejercicio5_pra {
    // Creamos una función que me va a devolver un boolean dependiendo de si es par o no un número
    fun esPar(num: Int): Boolean {
        return (num % 2) == 0
    }

    // Creamos una función que va a devolver el número de pares
    fun contarPares(array: IntArray): Int {
        var pares = 0
        for (i in array) {
            if (esPar(i)) pares++
        }
        return pares
    }

    // Creamos una función que va a devolver un array con los pares
    fun crearPar(num: Int, array: IntArray): IntArray {
        var arrayPar: IntArray = IntArray(num)
        for (i in array) {
            // En caso de que sea par lo añadimos al nuevo array
            if (esPar(i)) arrayPar = arrayPar.plus(i)
        }
        return arrayPar
    }

    // Creamos una función que va a devolver la suma de los números del array de pares
    fun sumarPares(arrayPares: IntArray): Int {
        var suma = 0
        for (i in arrayPares) {
            suma += i
        }
        return suma
    }
}
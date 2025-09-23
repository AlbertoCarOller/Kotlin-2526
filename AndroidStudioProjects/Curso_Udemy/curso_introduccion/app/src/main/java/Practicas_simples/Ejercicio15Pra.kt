package Practicas_simples

fun main() {
    var mayor: Char
    var a = 'A'
    var b = 'B'
    // Se asigna directamente el valor a la varibale
    mayor = if (a > b) a else b

    // Creamos una función de forma reducida, acepta un String, devuelve un Unit (nada)
    val  saluda : (String) -> Unit = { it -> println("Hola $it") }

    // Nos vamos sartando de 2 en 2 con el 'step'
    for (i in 0..5 step 2) {
        println(i)
    }
}
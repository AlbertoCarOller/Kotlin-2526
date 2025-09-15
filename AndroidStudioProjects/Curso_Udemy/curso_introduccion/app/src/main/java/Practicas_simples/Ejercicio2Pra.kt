package Practicas_simples

fun main() {
    // Creamos el objeto/clase
    val ejercicio2Pra = Ejercicio2_pra()
    // Creamos la varible que va a almacenar el precio final después del descuento
    var precioFinal = 0.0
    // Se repetirá la función hasta que se introduzca un día correcto
    while (precioFinal == 0.0) {
        println("Introduce el precio original del producto")
        /* Se lee como un String, esto lo hacemos para evitar problemas al castearlo,
        la '?' significa que puede ser null */
        var precioOriginalString: String? = readLine()
        println("Introduce el día de la semana")
        var diaSemana: String? = readLine()
        // Comprobamos si no es null el precio_original
        if (precioOriginalString != null) {
            /* Casteamos el precio original, el '.toDoubleOrNull' castea a Double, en caso
             de que haya un problema será null */
            var precioOriginal: Double? = precioOriginalString.toDoubleOrNull()
            // Si ni el precio ni el día de la semana son null, se ejecutará la función
            if (precioOriginal != null && diaSemana != null) {
                precioFinal = ejercicio2Pra.calcularPrecioFinal(precioOriginal, diaSemana)
            }
        }
    }
}

class Ejercicio2_pra {

    // Creamos una función que va a calcular el precio final de un producto dependiendo del día
    fun calcularPrecioFinal(precioOriginal: Double, diaSemana: String): Double {
        // En el caso de que el precio original sea igual a menor 0 no será válido
        if (precioOriginal <= 0) {
            return 0.0
        }
        when (diaSemana) {
            "Lunes", "Martes" -> return precioOriginal - (precioOriginal / 0.1)
            "Miércoles", "Jueves" -> return precioOriginal - (precioOriginal / 0.4)
            "Viernes", "Sábado" -> return precioOriginal - (precioOriginal / 0.6)
            "Domingo" -> return precioOriginal - (precioOriginal / 0.8)
            else -> return 0.0
        }
    }
}
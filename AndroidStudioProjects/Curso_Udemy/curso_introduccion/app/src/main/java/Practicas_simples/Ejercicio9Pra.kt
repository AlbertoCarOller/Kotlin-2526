package Practicas_simples

fun main() {
    // Creamos un mapa del inventario de una tienda
    var inventario = mutableMapOf<String, Int>()
    // Creamos el objeto
    val ejercicio9Pra = Ejercicio9Pra()
    // Añadimos productos al inventario
    ejercicio9Pra.agregarProductos(inventario, listOf("La Recompensa", "Ron Barceló",
        "Amareto", "Vodka"))
    // Consumimos algo de existencias para posteriormente reponer
    ejercicio9Pra.consumirExistencias(inventario, 30, "La Recompensa")
    ejercicio9Pra.consumirExistencias(inventario, 40, "Ron Barceló")
    // Imprimimos el inventario
    println(inventario)
    // Reponemos las existencias
    ejercicio9Pra.reponerInventario(inventario)
    // Volvemos a imprimir el inventario
    println(inventario)
}

class Ejercicio9Pra {
    // Creamos una función para añadir una serie de productos
    fun agregarProductos(inventario: MutableMap<String, Int>, productos: List<String>) {
        // Vamos a suponer que el máximo de un mismo producto es 50
        if (!productos.isEmpty()) {
            for (i in productos) {
                inventario.put(i, 50)
            }
        }
    }

    // Creamos una función que repase el inventario y reponga hasta el máximo
    fun reponerInventario(inventario: MutableMap<String, Int>) {
        for (i in inventario) {
            if (i.value < 50) {
                i.setValue(50)
            }
        }
    }

    // Creamos una función que va a consumir existencias del inventario
    fun consumirExistencias(inventario: MutableMap<String, Int>, consumir: Int, producto: String) {
        var existencias: Int? = inventario[producto]
        if (existencias != null) {
            if (existencias >= consumir) {
                inventario[producto] = existencias - consumir
            }
        }
    }
}
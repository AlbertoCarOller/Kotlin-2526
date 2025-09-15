package Practicas_simples

fun main() {
    // Creamos un mapa con los vendedores y la cantidad vendida
    val vendedores: Map<String, Int> = mapOf(
        "Coca-Cola" to 189,
        "Pepsi" to 569,
        "Aquario" to 290,
        "Fanta" to 68,
        "Freeway" to 1
    )
    // Creamos el objeto
    val ejercicio10Pra = Ejercicio10Pra()
    println("Mejor vendedor: ${ejercicio10Pra.mejorVendedor(vendedores)}")
}

class Ejercicio10Pra {
    // Creamos una función que va a devolver al vendedor que más haya vendido
    fun mejorVendedor(vendedores: Map<String, Int>): String {
        var numMayor = -1
        var nombreMayor = ""
        for (i in vendedores) {
            if (i.value > numMayor) {
                numMayor = i.value
                nombreMayor = i.key
            }
        }
        return nombreMayor
    }
}
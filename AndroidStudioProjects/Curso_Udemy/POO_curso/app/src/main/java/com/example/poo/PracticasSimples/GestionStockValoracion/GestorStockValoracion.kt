package com.example.poo.PracticasSimples.GestionStockValoracion

fun main() {
    var producto1 = Producto("9832T", "Vainilla dorada", 34.4, 20, 4)
    var producto2 = Producto("3196Y", "Chocolate con leche", 3.6, 12, 10)
    var producto3 = Producto("2936G", "Limonada de fresa", 6.7, 6, 2)
    var producto4 = Producto("4401Z", "Mantequilla", 3.1, 19, 23)
    var producto5 = Producto("0623M", "Paté de atún", 8.3, 10, 6)

    AlmacenCentral.agregarProducto(producto1)
    AlmacenCentral.agregarProducto(producto2)
    AlmacenCentral.agregarProducto(producto3)
    AlmacenCentral.agregarProducto(producto4)
    AlmacenCentral.agregarProducto(producto5)

    AlmacenCentral.revisarObsolescencia()

    println(
        "Total valor stock: ${
            // Formateamos para que el valor del stock solo tenga 2 decimales, gracias al .format()
            "%.2f".format(
                AlmacenCentral.obtenerStockTotal().calcularValorTotalStock(
                    { it -> it.precioCosto },
                    { it -> it.esObsoleto(18) })
            )
        }"
    )
}

interface Valoracion {
    fun calcularDepreciacion(mesesAntiguedad: Int): Double
    fun esObsoleto(limiteMeses: Int): Boolean
}

data class Producto(
    var sku: String, var nombre: String, var precioCosto: Double,
    var mesesEnStock: Int, var stockActual: Int
) : Valoracion {
    override fun calcularDepreciacion(mesesAntiguedad: Int): Double {
        return this.precioCosto * (1.0 - (mesesAntiguedad * 0.01))
    }

    override fun esObsoleto(limiteMeses: Int): Boolean {
        return limiteMeses <= mesesEnStock
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Producto

        return sku == other.sku
    }

    override fun hashCode(): Int {
        return sku.hashCode()
    }
}

object AlmacenCentral {
    var listaProductos = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        if (!listaProductos.contains(producto)) {
            listaProductos.add(producto)
        }
    }

    fun obtenerStockTotal(): List<Producto> {
        return this.listaProductos
    }

    fun revisarObsolescencia() {
        listaProductos.filter { it -> it.esObsoleto(18) }.forEach { it -> println(it.sku) }
    }
}

fun List<Producto>.calcularValorTotalStock(
    fn1: (Producto) -> Double,
    fn2: (Producto) -> Boolean
): Double {
    return this.filter { it -> fn2(it) }.sumOf { it -> fn1(it) * it.stockActual }
}
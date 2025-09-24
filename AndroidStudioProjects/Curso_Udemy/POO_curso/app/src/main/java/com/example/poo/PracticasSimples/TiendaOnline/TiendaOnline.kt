package com.example.poo.PracticasSimples.TiendaOnline

fun main() {
    var compra1 = Compra("FreeChocolate", 0.0)
    var compra2 = Compra("Vainilla", 5.5)
    var compra3 = Compra("Peperoni", 9.9)
    var compra4 = Compra("Coca-Cola", 9.6)

    var listaCompras = listOf(compra1, compra2, compra3, compra4)
    GestorPagos.procesarCompra(compra1, MetodoPago.PAYPAL)
    GestorPagos.procesarCompra(compra2, MetodoPago.TARJETA)
    GestorPagos.procesarCompra(compra3, MetodoPago.TRASFERENCIA)
    GestorPagos.procesarCompra(compra4, MetodoPago.PAYPAL)

    println(GestorPagos.totalCompraMayor(listaCompras))
    println(GestorPagos.totalCompras(listaCompras))
    GestorPagos.comprasGratis(listaCompras)
}

// Las enum class se les permite tener atributos/parámetros a diferencia de Java
enum class MetodoPago(var valor: Double) {
    TARJETA(0.02),
    PAYPAL(0.03),
    TRASFERENCIA(0.0)
}

interface Transaccionable {
    var costoTotal: Double
    fun aplicarRecargo(metodoPago: MetodoPago)
}

data class Compra(
    var nombreProducto: String, private var precioBase: Double
) : Transaccionable {

    override var costoTotal: Double = precioBase
    override fun aplicarRecargo(metodoPago: MetodoPago) {
        costoTotal = precioBase + (precioBase * metodoPago.valor)
    }

    override fun toString(): String {
        return "Compra(nombreProducto='$nombreProducto', precioBase=$precioBase, costoTotal=$costoTotal)"
    }

}

object GestorPagos {
    fun procesarCompra(compra: Compra, metodoPago: MetodoPago) {
        compra.aplicarRecargo(metodoPago)
        println("Se ha procesado el pago de ${compra.nombreProducto} con costo de ${compra.costoTotal}")
    }

    fun totalCompras(list: List<Compra>): Double {
        return list.sumOf { compra -> compra.costoTotal }
    }

    fun totalCompraMayor(list: List<Compra>): Compra {
        return list.maxBy { compra -> compra.costoTotal }
    }

    fun comprasGratis(list: List<Compra>) {
        list.filter { compra -> compra.costoTotal == 0.0 }.forEach { compra -> println(compra) }
    }
}
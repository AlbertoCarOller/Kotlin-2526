package com.example.poo.PracticasSimples.PedidosCafe

fun main() {
    // Creamos una instancia de Pedido
    var pedido = Pedido()
    with(pedido) {
        this.listaItems.add(ItemPedido("Sandwich", 3.3, false, 2))
        this.listaItems.add(ItemPedido("Naranja", 2.5, false, 1))
        this.listaItems.add(ItemPedido("Zumo de melocotón", 1.1, true, 1))
        this.listaItems.add(ItemPedido("Bizcocho de plátano", 6.5, false, 1))
    }
    println(
        "Suma: ${
            pedido.aplicarAjustePrecio { it ->
                var precioFinal: Double = it.precioBase * it.cantidad
                if (it.esBebida) {
                    precioFinal = precioFinal * 1.10
                }
                precioFinal
            }
        }"
    )
    // Obtenemos los items que no sean bebidas y unimos los nombres en un String
    println(pedido.listaItems.filter { it -> !it.esBebida }.map { it -> it.nombre }
        .joinToString { it -> it })
}

data class ItemPedido(
    var nombre: String,
    var precioBase: Double,
    var esBebida: Boolean,
    var cantidad: Int
)

class Pedido {
    var listaItems = mutableListOf<ItemPedido>()
    fun aplicarAjustePrecio(reglaAjuste: (ItemPedido) -> Double): Double {
        return listaItems.sumOf { it -> reglaAjuste(it) }
    }
}
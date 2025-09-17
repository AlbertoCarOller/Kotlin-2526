package com.example.poo.PracticasSimples.GestorTienda

fun main() {
    // Creamos el gestor
    var gestorTienda = GestorTienda()
    with(gestorTienda) {
        // Añadimos productos
        this.agregarProducto(ProductoFisico("Chocolate", 3.4, "GHT5", 10))
        this.agregarProducto(ProductoDigital("Cuphead", 19.5, "YRE9", "https//:cuphead.com"))
        this.agregarProducto(ProductoFisico("Coca-Cola", 1.5, "OPD5", 14))
        this.agregarProducto(ProductoFisico("Pepsi", 1.0, "IPB7", 6))

        // Mostramos todos los productos
        this.mostrarProductos()

        // Obtenemos un producto por el id
        println("El producto con el id pasado es: ${this.obtenerProductoPorID("YRE9")?.getNombre()}")
    }
}

interface Vendible {
    // Creamos una función que va a calcular el precio con IVA
    fun calcularPrecioIVA(): Double

    // Devuelve el id
    fun obtenerId(): String
}

abstract class Producto(
    protected var nombre: String,
    protected var precioBase: Double,
    protected var id: String
) :
    Vendible {
    // Hacemos un get del nombre
    internal fun getNombre(): String {
        return this.nombre
    }

    // Creamos la función para mostrar la información
    fun mostrarInfo(): String {
        return "Nombre: $nombre, Precio base: $precioBase, ID: $id"
    }
}

class ProductoFisico(nombre: String, precioBase: Double, id: String, private var stock: Int) :
    Producto(nombre, precioBase, id) {

    // Implementamos las funciones de la interfaz
    override fun calcularPrecioIVA(): Double {
        return precioBase + (precioBase * 0.21)
    }

    override fun obtenerId(): String {
        return id
    }
}

class ProductoDigital(
    nombre: String,
    precioBase: Double,
    id: String,
    private var URLDescarga: String
) : Producto(nombre, precioBase, id) {

    // Implementamos las funciones de la interfaz
    override fun calcularPrecioIVA(): Double {
        return precioBase + (precioBase * 0.10)
    }

    override fun obtenerId(): String {
        return id
    }
}

class GestorTienda() {
    // Creamos el atributo, en este caso una lista de productos vendibles
    private var productosVendibles = mutableListOf<Producto>()

    // Creamos una función que va a añadir un producto a la lista
    fun agregarProducto(producto: Producto) {
        productosVendibles.add(producto)
    }

    // Creamos una función que va a devolver un Producto con el id pasado por parámetros
    fun obtenerProductoPorID(id: String): Producto? {
        for (i in productosVendibles) {
            if (i.obtenerId() == id) {
                return i
            }
        }
        return null
    }

    // Creamos una función que va a mostrar todos los productos del inventario
    fun mostrarProductos() {
        for (i in 0 until productosVendibles.size) {
            println("Producto: ${productosVendibles[i].getNombre()}")
        }
    }
}
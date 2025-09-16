package com.example.poo.PracticasSimples.Reservas

open class Reserva(private var nombreCliente: String, private var dias: Int, private var precioBaseDia: Double) {

    // Creamos los get y set
    internal fun getNombreCliente(): String {
        return this.nombreCliente
    }

    internal fun setNombreCliente(n: String) {
        this.nombreCliente = n
    }

    internal fun getDias(): Int {
        return this.dias
    }

    internal fun setDias(d: Int) {
        this.dias = d
    }

    internal fun getPrecioBaseDia(): Double {
        return this.precioBaseDia
    }

    open fun calcularTotal(): Double {
        return this.precioBaseDia * this.dias
    }

    open fun mostrarInformacion(): String {
        return "Nombre cliente: $nombreCliente, Días: $dias, Precio por día: $precioBaseDia"
    }
}

class ReservaVIP(nombreCliente: String, dias: Int, precioBaseDia: Double, private var descuento: Double)
    : Reserva(nombreCliente, dias, precioBaseDia) {

        // Creamos los get y set
    internal fun getDescuento(): Double {
        return this.descuento
    }

    internal fun setDescuento(d: Double) {
        this.descuento = d
    }

    // Modificamos la función 'calcularTotal'
    override fun calcularTotal(): Double {
        return super.calcularTotal() - (this.getPrecioBaseDia() * descuento)
    }

    // Modificamos la función 'mostrarInformacion'
    override fun mostrarInformacion(): String {
       return super.mostrarInformacion().plus(", Descuento: $descuento")
    }
    }
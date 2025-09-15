package com.example.poo.PracticasSimples.Vehiculos

abstract class Vehiculo(private var marca: String, private var ano: Int) {

    // Creamos los get
    internal fun getMarca(): String {
        return this.marca
    }

    internal fun getAno(): Int {
        return this.ano
    }

    // Creamos una función que va a mostrar información sobre el vehículo
    open fun mostrarInformacion(): String {
        return "Marca: $marca, Año: $ano"
    }
}

// Creamos la clase hija coche
class Coche(marca: String, ano: Int,private var numPuertas: Int = 2) : Vehiculo(marca, ano) {

    // Creamos el get de número de puertas
    internal fun getNumPuertas(): Int {
        return this.numPuertas
    }

    // Sobreescribimos la función de la clase padre
    override fun mostrarInformacion(): String {
        return super.mostrarInformacion().plus(", Número de puertas: $numPuertas")
    }

    // Creamos una función que va a simular bajar una ventana del coche
    fun bajarVentana(ventana: String) {
        when(ventana) {
            "d" -> println("Bajando ventanas de la derecha")
            "i" -> println("Bajando ventanas de la izquierda")
            else -> println("Esas venatas no existen")
        }
    }
}

// Creamos una clase hija motocicleta
class Motocicleta(marca: String, ano: Int,private var sidecar: Boolean = false) : Vehiculo(marca, ano) {

    // Creamos un get y set del sidecar
    internal fun getSidecar(): Boolean {
        return this.sidecar
    }

    internal fun setSidecar(sidecar: Boolean) {
        this.sidecar = sidecar
    }

    // Sobreescribimos la función de la clase padre
    override fun mostrarInformacion(): String {
        return if (sidecar) {
            super.mostrarInformacion().plus(", Sidecar: Sí")
        } else {
            super.mostrarInformacion().plus(", Sidecar: No")
        }
    }
}
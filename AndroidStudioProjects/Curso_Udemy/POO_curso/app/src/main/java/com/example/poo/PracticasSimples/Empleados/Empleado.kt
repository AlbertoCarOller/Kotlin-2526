package com.example.poo.PracticasSimples.Empleados

abstract class Empleado(private val nombre: String, private var salarioBase: Double) {

    // Creamos los get y set necesarios
    internal fun getNombre(): String {
        return this.nombre
    }

    internal fun getSalarioBase(): Double {
        return this.salarioBase
    }

    internal fun setSalarioBase(salarioBase: Double) {
        this.salarioBase = salarioBase
    }

    // Creamos una función que sea calcular el salario final
    open fun calcularSalarioFinal(): Double {
        return this.salarioBase
    }
}

class Gerente(nombre: String, salarioBase: Double, private var bono: Double) : Empleado(nombre, salarioBase) {

    // Creamos los get y set necesarios
    internal fun getBono(): Double {
        return this.bono
    }

    internal fun setBono(bono: Double) {
        this.bono = bono
    }

    // Modificamos la función del padre
    override fun calcularSalarioFinal(): Double {
        return super.calcularSalarioFinal() + bono
    }
}

class Analista(nombre: String, salarioBase: Double, private var comisiones: Double) : Empleado(nombre, salarioBase) {

    // Creamos los get y set necesarios
    internal fun getComisiones(): Double {
        return this.comisiones
    }

    internal fun setComisiones(comisiones: Double) {
        this.comisiones = comisiones
    }

    // Modificamos la función del padre para calcular el salario final en este caso
    override fun calcularSalarioFinal(): Double {
        return super.calcularSalarioFinal() + comisiones
    }
}
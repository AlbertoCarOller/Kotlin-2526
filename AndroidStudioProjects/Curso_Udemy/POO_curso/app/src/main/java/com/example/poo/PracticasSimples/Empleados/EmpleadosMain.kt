package com.example.poo.PracticasSimples.Empleados

fun main() {

    // Creamos un gerente y un analista
    var gerente = Gerente("Alberto", 2457.45, 224.98)
    var analista = Analista("Manuel", 2056.3, 156.64)

    // Ahora llamamos a la función para calcular el salario final de cada uno
    println("El salario final de ${gerente.getNombre()} es de ${gerente.calcularSalarioFinal()} EUR")
    println("El salario final de ${analista.getNombre()} es de ${analista.calcularSalarioFinal()} EUR")
}
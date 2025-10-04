package com.example.poo.PracticasSimples.SistemaNominasEmpleados

fun main() {
    var empleado1 = Empleado(8912, "Alberto", 1200.0, 84)
    var empleado2 = Empleado(4712, "Chelu", 900.99, 45)
    var empleado3 = Empleado(5901, "Serapio", 690.90, 29)
    var empleado4 = Empleado(1894, "Atisbedo", 777.77, 37)
    var empleado5 = Empleado(4231, "Repicio Godefrio", 2400.23, 130)
    var empleado6 = Empleado(5432, "Anton", 1100.99, 78)

    DepartamentoRRHH.registrarEmpleado(empleado1)
    DepartamentoRRHH.registrarEmpleado(empleado2)
    DepartamentoRRHH.registrarEmpleado(empleado3)
    DepartamentoRRHH.registrarEmpleado(empleado4)
    DepartamentoRRHH.registrarEmpleado(empleado5)
    DepartamentoRRHH.registrarEmpleado(empleado6)

    DepartamentoRRHH.obtenerEvaluacion(50)
    println("Total de ventas: ${DepartamentoRRHH.obtenerEmpleados().sumarTotalMetrica(::ventas)}")
    println(
        "Empleados prefijos: ${
            DepartamentoRRHH.obtenerEmpleados()
                .obtenerNombreConPrefijos { it -> it.nombre.substring(0, 3) }
        }"
    )
}

fun ventas(empleado: Empleado) = empleado.ventasRealizadas.toDouble()

interface EvaluarRendimiento {
    fun obtenerSalarioBase(): Double
    fun esElegibleParaBono(ventasObjetivo: Int): Boolean
}

data class Empleado(
    var id: Int,
    var nombre: String,
    var salarioBase: Double,
    var ventasRealizadas: Int
) : EvaluarRendimiento {
    override fun obtenerSalarioBase(): Double {
        return this.salarioBase
    }

    override fun esElegibleParaBono(ventasObjetivo: Int): Boolean {
        return this.ventasRealizadas >= ventasObjetivo
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Empleado

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}

object DepartamentoRRHH {
    private var listaEmpleado = mutableListOf<Empleado>()
    fun registrarEmpleado(empleado: Empleado) {
        if (!listaEmpleado.contains(empleado)) {
            listaEmpleado.add(empleado)
        }
    }

    fun obtenerEmpleados(): List<Empleado> {
        return this.listaEmpleado
    }

    fun obtenerEvaluacion(objetivo: Int) {
        listaEmpleado.filter { it -> it.esElegibleParaBono(objetivo) }
            .forEach(::println)
    }
}

fun List<Empleado>.sumarTotalMetrica(fn: (Empleado) -> Double): Double {
    return this.sumOf { it -> fn(it) }
}

fun List<Empleado>.obtenerNombreConPrefijos(fn: (Empleado) -> String): String {
    return this.joinToString { it -> fn(it) }
}
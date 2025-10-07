package com.example.poo.PracticasSimples.GestionProyectosCreativo

fun main() {
    try {
        // Creamos varios proyectos
        var proyecto1 = Proyecto("Paco ia", 13, 600.4, 34.5)
        var proyecto2 = Proyecto("GestorBBDD", 22, 409.2, 21.0)
        var proyecto3 = Proyecto("Nave espacial", 24, 669.89, 23.99)
        var proyecto4 = Proyecto("Videojuego", 59, 300.3, 9.99)

        // Añadimos los juegos al estudio
        EstudioDiseno.registrarProyecto(proyecto1)
        EstudioDiseno.registrarProyecto(proyecto2)
        EstudioDiseno.registrarProyecto(proyecto3)
        EstudioDiseno.registrarProyecto(proyecto4)

        println(
            "Suma total proyectos: %.2f".format(
                EstudioDiseno.getListaProyectos().calcularCostoTotalNeto(
                    { it -> it.obtenerTasaBaseTotal() },
                    { it.aplicarDescuento(0.3) }, 0.16
                )
            )
        )

        EstudioDiseno.getListaProyectos().obtenerReporte { it -> it.tasaHoraBase < 23 }

        println(
            EstudioDiseno.getListaProyectos()
                .proyectoPorCosto { it -> it.obtenerTasaBaseTotal() + it.costoMateriales })

    } catch (e: ProyectoException) {
        println(e.message)
    }

}

// Creamos los alias necesarios
typealias condicion = (Proyecto) -> Boolean
typealias proyectoToDouble = (Proyecto) -> Double

class ProyectoException(mensaje: String) : Exception(mensaje)

interface CostoEstimado {
    fun obtenerTasaBaseTotal(): Double
    fun aplicarDescuento(descuentoPorcentaje: Double): Double
}

data class Proyecto(
    var nombre: String,
    var horasEstimadas: Int,
    var costoMateriales: Double,
    var tasaHoraBase: Double
) : CostoEstimado {
    override fun obtenerTasaBaseTotal(): Double {
        return horasEstimadas * tasaHoraBase
    }

    override fun aplicarDescuento(descuentoPorcentaje: Double): Double {
        if (descuentoPorcentaje > 1 || descuentoPorcentaje < 0) {
            throw ProyectoException("El valor del porcentaje no es válido")
        }
        return obtenerTasaBaseTotal() * descuentoPorcentaje
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Proyecto

        return nombre == other.nombre
    }

    override fun hashCode(): Int {
        return nombre.hashCode()
    }
}

object EstudioDiseno {
    private var listaProyectos = mutableListOf<Proyecto>()

    internal fun getListaProyectos(): List<Proyecto> {
        return listaProyectos
    }

    fun registrarProyecto(proyecto: Proyecto) {
        if (!listaProyectos.contains(proyecto)) {
            listaProyectos.add(proyecto)
        }
    }

    fun revisarProyectosLargos(horasMax: Int) {
        listaProyectos.filter { it.horasEstimadas >= horasMax }.forEach { println(it.nombre) }
    }
}

/**
 * Esta función va a calcular el coste total de todos los proyectos
 * con descuentos (dos descuentos)
 */
fun List<Proyecto>.calcularCostoTotalNeto(
    calculadorTrabajo: proyectoToDouble,
    descuentoGlobal: proyectoToDouble,
    descuento: Double
): Double {
    return this.sumOf {
        ((calculadorTrabajo(it) + it.costoMateriales)
                - it.aplicarDescuento(descuento)) - descuentoGlobal(it)
    }
}

/**
 * Esta función va a imprimir los nombres de los proyectos que cumplan
 * cierta condición
 */
fun List<Proyecto>.obtenerReporte(condicion: condicion) {
    this.filter(condicion).forEach { println(it.nombre) }
}

/**
 * Esta función va a sumar los costes totales de cada proyecto, sin descuentos
 */
fun List<Proyecto>.proyectoPorCosto(fn: proyectoToDouble): Map<String, Double> {
    return this.groupBy { it.nombre }.mapValues { it.value.sumOf { it -> fn(it) } }
}
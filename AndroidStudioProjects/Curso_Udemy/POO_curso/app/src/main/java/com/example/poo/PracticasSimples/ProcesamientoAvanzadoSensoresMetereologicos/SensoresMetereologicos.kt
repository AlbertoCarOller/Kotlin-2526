package com.example.poo.PracticasSimples.ProcesamientoAvanzadoSensoresMetereologicos

fun main() {
    try {
        // Creamos una clase anónima que va contener las diferentes lecturas recogidas
        var gestorSensores = object {
            var listaLecturas = mutableListOf<LecturaSensor>()
            fun agregarLectura(lecturaSensor: LecturaSensor) {
                if (!listaLecturas.contains(lecturaSensor)) {
                    listaLecturas.add(lecturaSensor)
                }
            }
        }
        // Añadimos lecturas
        gestorSensores.agregarLectura(LecturaSensor("Madrid", 25.7, 7, true))
        gestorSensores.agregarLectura(LecturaSensor("Madrid", 35.8, 34, true))
        gestorSensores.agregarLectura(LecturaSensor("Barcelona", 22.4, 19, true))
        gestorSensores.agregarLectura(LecturaSensor("Isla Cristina", 19.5, 65, true))
        gestorSensores.agregarLectura(LecturaSensor("Segovia", 10.3, 78, false))

        println(
            "Total temperatura: ${
                gestorSensores.listaLecturas.procesarYSumar(
                    { it -> it.esValida },
                    { it -> it.temperatura })
            }"
        )

        println(
            "Validez: ${
                gestorSensores.listaLecturas.transformarCondicionalmente(
                    { it -> it.esValida },
                    { it -> "Valido: ${it.ciudad} con temperatura ${it.temperatura}" },
                    { it -> "Invalido: ${it.ciudad} con temperatura ${it.temperatura}" })
            }"
        )

        println(
            "Ciudades por temperatura media: ${
                gestorSensores.listaLecturas.ciudadTemperaturaMedia(
                    ::validez
                )
            }"
        )

    } catch (e: LecturaSensorException) {
        println(e.message)
    }
}

fun validez(lecturaSensor: LecturaSensor) = lecturaSensor.esValida

class LecturaSensorException(mensaje: String) : Exception(mensaje)

data class LecturaSensor(
    var ciudad: String,
    var temperatura: Double,
    var humedad: Int,
    var esValida: Boolean
) {
    init {
        if (humedad > 100 || humedad < 0) {
            throw LecturaSensorException("La humedad no es válida")
        }
    }
}

/**
 * Esta función va a devolver la suma (Double) según un filtro
 * y un valor que es el que se utilizará para sumar
 */
fun List<LecturaSensor>.procesarYSumar(
    fn: (LecturaSensor) -> Boolean,
    fn2: (LecturaSensor) -> Double
): Double {
    return this.filter { it -> fn(it) }.sumOf { it -> fn2(it) }
}

/**
 * Esta función de orden superior va a transformar según una condición una
 * 'LecturaSensor' a un String
 */
fun List<LecturaSensor>.transformarCondicionalmente(
    fnCondicion: (LecturaSensor) -> Boolean,
    fnBien: (LecturaSensor) -> String,
    fnMal: (LecturaSensor) -> String
): List<String> {
    return this.map { it ->
        var valor = fnMal(it)
        if (fnCondicion(it)) {
            valor = fnBien(it)
        }
        valor
    }.toList()
}

/**
 * Esta función va a devolver un mapa con las ciudades y la media de su temperatura
 */
fun List<LecturaSensor>.ciudadTemperaturaMedia(
    condicion: (LecturaSensor) -> Boolean
): Map<String, Double> {
    return this.filter { it -> condicion(it) }.groupBy { it.ciudad }
        .mapValues { it -> (it.value.sumOf { it.temperatura }) / it.value.size }
}
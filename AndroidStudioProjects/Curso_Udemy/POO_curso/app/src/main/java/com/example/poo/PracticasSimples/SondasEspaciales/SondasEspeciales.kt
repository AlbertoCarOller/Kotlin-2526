package com.example.poo.PracticasSimples.SondasEspaciales

fun main() {
    // Creamos una lista de lecturas
    var listaLecturas = listOf(
        Lectura("T-Voyager", 50.4),
        Lectura("P-OPI", 34.4),
        Lectura("B-ERMUDO", -43.9),
        Lectura("A-TISBEDO", 134.1),
        Lectura("C-HELU", 207.91),
        Lectura("B-ERMUDO", 101.2)
    )
    /* Filtramos por las lecturas que superen los 100 grados centígrados y llamamos a 'procesarDatos'
     que va a pasarlo a Kelvin e imprimos */
    procesarDatos(
        listaLecturas.filter { it -> it.temperaturaCelsius > 100 }.toList(),
        { it -> it + 273.15 }).forEach { it -> println(it) }

    // Contamos las lecturas de las temperaturas que sobrepasen los 100 grados centígrados, las anomalías
    println("Anomalías: ${listaLecturas.count { it -> it.temperaturaCelsius > 100 }}")

    // Creamos un mapa con los id de las sondas y el número de lecturas hechas
    listaLecturas.groupingBy { it -> it.idSonda }.eachCount()
        .forEach { it -> println("ID: ${it.key}, Valor: ${it.value}") }
}

/* Esta función de orden superior transforma la lista de Lecturas a una lista de Double con las
   temperaturas de cada Lectura, una función de grado superior es cuando una función acepta otra
   función o devuelve una función */
fun procesarDatos(listaLectura: List<Lectura>, ajustador: (Double) -> Double): List<Double> {
    return listaLectura.map { lectura -> ajustador(lectura.temperaturaCelsius) }
}

// Creamos una data class para guardar los datos de la Lectura de las sondas
data class Lectura(var idSonda: String, var temperaturaCelsius: Double)
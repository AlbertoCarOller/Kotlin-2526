package Practicas_simples

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    // Creamos un mapa que va a contener los eventos con sus fechas
    var eventos = mapOf<String, LocalDate>(
        "Coca-Cola Fest" to LocalDate.of(2025, 9, 21),
        "Puro Latino" to LocalDate.of(2024, 8, 24),
        "Los 80" to LocalDate.of(2025, 8, 27),
        "Isla Fest" to LocalDate.of(2025, 12, 16)
    )

    // Creamos el objeto
    val ejercicio11Pra = Ejercicio11Pra()
    // Preguntamos por teclado el nombre del evento
    var nombreEvento: String? = null
    while (nombreEvento == null) {
        println("Introduce el nombre del evento")
        nombreEvento = readLine()
    }
    println(ejercicio11Pra.indicarEvento(eventos, nombreEvento))
}

class Ejercicio11Pra {
    // Creamos una función que va a indicar si un evento es pasado, presente o futuro
    fun indicarEvento(eventos: Map<String, LocalDate>, nombreEvento: String): String {
        // Comprobamos que exista el veneto y que no esté vacío el mapa de eventos
        if (!eventos.containsKey(nombreEvento) || eventos.isEmpty()) {
            return "Sin resultado"
        }
        // Obtenemos la fecha del evento
        val fechaEvento = eventos[nombreEvento]!!
        // Comprobamos y dependiendo de la fecha se retornará lo indicado
        return if (fechaEvento.isAfter(LocalDate.now())) {
            "El evento $nombreEvento con fecha ${fechaEvento.format(DateTimeFormatter
                .ofPattern("dd/MM/yyyy"))} aún no ha ocurrido"

        } else if (fechaEvento.isBefore(LocalDate.now())) {
            "El evento $nombreEvento con fecha ${fechaEvento.format(DateTimeFormatter
                .ofPattern("dd/MM/yyyy"))} ya ocurrió"
        } else {
            "El evento $nombreEvento con fecha ${fechaEvento.format(DateTimeFormatter
                .ofPattern("dd/MM/yyyy"))} es hoy"
        }
    }
}
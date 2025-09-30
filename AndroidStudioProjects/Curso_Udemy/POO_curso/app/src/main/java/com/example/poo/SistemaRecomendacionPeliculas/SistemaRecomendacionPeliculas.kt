package com.example.poo.SistemaRecomendacionPeliculas

fun main() {
    // Creamos una lista de películas
    var listaPeliculas = listOf(
        Pelicula("Matrix", Genero.FANTASIA, 8.9, 138),
        Pelicula("Resacón en Las Vegas", Genero.COMEDIA, 7.4, 89),
        Pelicula("Lion", Genero.ACCION, 7.6, 74),
        Pelicula("IT", Genero.TERROR, 4.6, 70),
        Pelicula("El Corredor del Laberinto", Genero.ACCION, 9.9, 100),
        Pelicula("Los Juegos del Hambre", Genero.ACCION, 10.0, 122)
    )

    // Mostramos los títulos de las películas con una puntuación superior a 8
    listaPeliculas.filter { it -> it.puntuacionIMDB > 8 }.map { it -> it.titulo }
        .forEach { it -> println("Recomendada: $it") }
    // Llamamos a la función de orden superior
    evaluarPorCriterio(
        listaPeliculas, { it -> it.duracionMinutos >= 100 },
        { it -> println("Título: ${it.titulo}, Género: ${it.genero}") })
}

/**
 * Esta función va a recibir la lista de películas, con el criterio
 * las filtrará y con la acción se imprimirá lo necesario
 */
fun evaluarPorCriterio(
    listaPeliculas: List<Pelicula>,
    criterio: (Pelicula) -> Boolean,
    accion: (Pelicula) -> Unit
) {
    listaPeliculas.filter { it -> criterio(it) }.forEach { it -> accion(it) }
}

enum class Genero(var nombre: String) {
    ACCION("Acción"),
    FANTASIA("Fantasia"),
    TERROR("Terror"),
    COMEDIA("Comedia")
}

data class Pelicula(
    var titulo: String,
    var genero: Genero,
    var puntuacionIMDB: Double,
    var duracionMinutos: Int
)
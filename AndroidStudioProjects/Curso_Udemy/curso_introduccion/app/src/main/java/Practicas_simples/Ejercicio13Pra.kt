package Practicas_simples

fun main() {
    var calificaciones = mutableMapOf<String, Double>(
        "Pedro" to 10.0,
        "Chelu" to 9.4,
        "Atisbedo" to 7.2,
        "Bermudín" to 9.9
    )
    /* El operador Elvis en este caso funciona porque está dentro de una interpolación, aunque
     el tipo que tiene que devolver la función sea Double o null */
    println("La nota del alumno es de: ${consultarNota("Chelu", calificaciones) ?: "No hay datos"}")

    actualizarCalificacion("Pedro", 6.2, calificaciones)
}

fun consultarNota(nombre: String, calificaciones: Map<String, Double>): Double? {
    return calificaciones[nombre]
}

fun actualizarCalificacion(nombre: String, nota: Double, calificaciones: MutableMap<String, Double>) {
    calificaciones.replace(nombre, nota)
}
package com.example.poo.PracticasSimples.SistemaMisionesRecompensasRPGRetro

fun main() {
    try {
        // Creamos una lista de misiones
        var mision = listOf<Mision>(
            Mision("Rescata a Chelu", 8, true, 200),
            Mision("Colarse en la mansión de Chelu", 10, false, 250),
            Mision("Derrotar a Lolitogoku", 1, true, 10),
            Mision("Hundir la flota de Davidividi", 10, true, 500),
            Mision("Conquista de Paco", 8, false, 340)
        )
        // Calculamos el total de XP de todas las misiones de la lista
        println("Total XP: ${mision.calcularTotalXP { mision -> (3 * mision.dificultad) }}")

    } catch (e: SistemaMisionesException) {
        println(e.message)
    }
}

class SistemaMisionesException(mensaje: String) : Exception(mensaje)

data class Mision(
    var nombre: String,
    var dificultad: Int,
    var esPrincipal: Boolean,
    var recompensaBaseOro: Int
) {
    init {
        if (dificultad > 10 || dificultad < 1) {
            throw SistemaMisionesException("La dificultad no es válida")
        }
    }

}

// Creamos una función extendida para una lista de Misiones
fun List<Mision>.calcularTotalXP(calculadoraXP: (Mision) -> Int): Int {
    return this.sumOf { it -> calculadoraXP(it) }
}
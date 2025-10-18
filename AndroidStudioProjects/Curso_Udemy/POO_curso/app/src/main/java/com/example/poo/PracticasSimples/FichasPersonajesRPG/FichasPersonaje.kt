package com.example.poo.PracticasSimples.FichasPersonajesRPG

fun main() {
    var listaPersonajeRPG = listOf(
        Guerrero("Tonio", 12, 100, 34, 20, 98),
        Guerrero("Chelu", 10, 76, 50, 34, 56),
        Mago("Atisbedo", 48, 56, 23, 10, 101),
        Mago("RespicioGodefrio", 12, 78, 43, 12, 87)
    )

    println(
        "Total: %.2f".format(
            listaPersonajeRPG.obtenerMetricaPonderada(
                { it -> it.nivel > 10 },
                { p, d -> p.obtenerPoderOfensivoNeto() * d },
                23.4
            )
        )
    )
}

abstract class PersonajeRPG(
    open var nombre: String,
    open var nivel: Int,
    open var saludBase: Int,
    open var puntosAtaqueBase: Int,
    open var puntosDefensaBase: Int
) {
    abstract fun calcularBonificacionOfensiva(): Int

    fun obtenerPoderOfensivoNeto(): Int {
        return puntosAtaqueBase + (nivel * 2) + calcularBonificacionOfensiva()
    }
}

data class Guerrero(
    override var nombre: String,
    override var nivel: Int,
    override var saludBase: Int,
    override var puntosAtaqueBase: Int,
    override var puntosDefensaBase: Int,
    var fuerza: Int
) : PersonajeRPG(nombre, nivel, saludBase, puntosAtaqueBase, puntosDefensaBase) {
    override fun calcularBonificacionOfensiva(): Int {
        return 5 + (fuerza * 3)
    }
}

data class Mago(
    override var nombre: String,
    override var nivel: Int,
    override var saludBase: Int,
    override var puntosAtaqueBase: Int,
    override var puntosDefensaBase: Int,
    var manaTotal: Int
) : PersonajeRPG(nombre, nivel, saludBase, puntosAtaqueBase, puntosDefensaBase) {
    override fun calcularBonificacionOfensiva(): Int {
        return if (manaTotal >= 100) {
            50
        } else {
            10
        }
    }
}

fun List<PersonajeRPG>.obtenerMetricaPonderada(
    condicion: (PersonajeRPG) -> Boolean,
    lambdaMapeoPonderada: (PersonajeRPG, Double) -> Double,
    factorConstante: Double
): Double {
    return this.filter { it -> condicion(it) }
        .sumOf { it -> lambdaMapeoPonderada(it, factorConstante) }
}
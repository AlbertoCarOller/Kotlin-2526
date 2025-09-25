package com.example.poo.PracticasSimples.CinePuntosFidelidad

fun main() {
    var transaccion = Transaccion(999, TipoEntrada.STANDARD, 34.6, 10.8)
    var transaccion1 = Transaccion(999, TipoEntrada.PREMIUM, 50.5, 9.7)
    var transaccion2 = Transaccion(123, TipoEntrada.STANDARD, 11.9, 20.9)
    var transaccion3 = Transaccion(345, TipoEntrada.PREMIUM, 44.6, 12.2)

    GestorPuntos.registrarTransaccion(transaccion)
    GestorPuntos.registrarTransaccion(transaccion1)
    GestorPuntos.registrarTransaccion(transaccion2)
    GestorPuntos.registrarTransaccion(transaccion3)

    println("-----------Regla Base-------------")
    GestorPuntos.obtenerRankingPorCliente(ReglaBase())
        .forEach { it -> println("ID: ${it.key}, Puntos: ${it.value}\n") }
    println("-----------Regla Promocion-------------")
    GestorPuntos.obtenerRankingPorCliente(ReglaPromocion())
        .forEach { it -> println("ID: ${it.key}, Puntos: ${it.value}\n") }
}

enum class TipoEntrada(name: String) {
    STANDARD("Standard"),
    PREMIUM("Premium")
}

data class Transaccion(
    val clienteId: Int, val tipoEntrada: TipoEntrada, val costoTicket: Double,
    val gastoCafeteria: Double
)

interface ReglaPuntos {
    var puntos: (transaccion: Transaccion) -> Int
}

class ReglaBase() : ReglaPuntos {
    override var puntos: (Transaccion) -> Int = { it -> (it.costoTicket / 5).toInt() }
}

class ReglaPromocion() : ReglaPuntos {
    // No se puede hacer dentro de una lambda otras {}, se puede pasar una ya definida
    override var puntos: (Transaccion) -> Int = { it ->
        var base = (it.costoTicket / 5).toInt()
        if (it.gastoCafeteria > 10) {
            base += 20
        }
        base
    }
}

object GestorPuntos {
    var transacciones = mutableListOf<Transaccion>()

    fun registrarTransaccion(transaccion: Transaccion) {
        transacciones.add(transaccion)
    }

    fun obtenerRankingPorCliente(reglaPuntos: ReglaPuntos): Map<Int, Int> {
        // .associate transforma un pares en map (SI ENCUENTRA DIFERENTES VALORES PARA UN KEY SOBREESCRIBE)
        //return transacciones.associate { it -> it.clienteId to reglaPuntos.puntos(it) }

        /* .gropBy -> Crea un mapa agrupando en este caso por el id, los values lo transforma en listas
        *  .mapValues -> Transforma los valores en lo que sea, en este caso en la suma de todos los
        *  puntos por cada cliente, gracias al .sumOf */
        return transacciones.groupBy { it -> it.clienteId }
            .mapValues { it -> it.value.sumOf { it -> reglaPuntos.puntos(it) } }
    }
}
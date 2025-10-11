package com.example.poo.PracticasSimples.AuditoriaCrypto

fun main() {
    // Creamos varios activos crytos
    var activoCrypto1 = ActivoCrypto("BTC", 100.4, 350.3, 4, true)
    var activoCrypto2 = ActivoCrypto("KTL", 67.4, 127.99, 8, false)
    var activoCrypto3 = ActivoCrypto("JVA", 189.99, 388.99, 3, false)
    var activoCrypto4 = ActivoCrypto("SWT", 99.99, 200.0, 6, true)

    var listaActivos = listOf(activoCrypto1, activoCrypto2, activoCrypto3, activoCrypto4)

    println("Ganancia neta: ${listaActivos.calcularGananciaNeta { it -> it.valorActualUSD - (it.valorActualUSD * 0.75) }}")

    listaActivos.obtenerReporteRiesgo(
        { it -> it.esDeFi },
        { it -> "Alto riesgo ${it.nombre}" },
        { it -> "Bajo riesgo ${it.nombre}" })
        .forEach(::println)

    println(
        "Valor actual total financiera: ${
            listaActivos.filtrarYObtenerMetrica(
                { it -> it.esDeFi },
                { it -> it.valorActualUSD })
        }"
    )
}

typealias condicion = (ActivoCrypto) -> Boolean

class ActivoCryptoException(mensaje: String) : Exception(mensaje)

data class ActivoCrypto(
    var nombre: String, var montoInvertidoUSD: Double, var valorActualUSD: Double,
    var factorRiesgo: Int, var esDeFi: Boolean
) {
    init {
        if (factorRiesgo < 1 || factorRiesgo > 10) {
            throw ActivoCryptoException("El factor de riesgo no es válido")
        }
    }
}

fun List<ActivoCrypto>.calcularGananciaNeta(fn: (ActivoCrypto) -> Double): Double {
    return this.sumOf { it -> (it.valorActualUSD - it.montoInvertidoUSD) - fn(it) }
}

fun List<ActivoCrypto>.obtenerReporteRiesgo(
    condicionAlerta: condicion,
    transformacionAltoRiesgo: (ActivoCrypto) -> String,
    trabsformacionRiesgoNormal: (ActivoCrypto) -> String
): List<String> {
    return this.map { it ->
        var s = trabsformacionRiesgoNormal(it)
        if (!condicionAlerta(it)) {
            s = transformacionAltoRiesgo(it)
        }
        s
    }.toList()
}

fun List<ActivoCrypto>.filtrarYObtenerMetrica(
    filtrado: condicion,
    extractor: (ActivoCrypto) -> Double
): Double {
    return this.filter { it -> filtrado(it) }.sumOf { it -> extractor(it) }
}
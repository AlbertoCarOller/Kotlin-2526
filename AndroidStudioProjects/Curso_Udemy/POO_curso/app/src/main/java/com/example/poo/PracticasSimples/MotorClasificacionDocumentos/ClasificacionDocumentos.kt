package com.example.poo.PracticasSimples.MotorClasificacionDocumentos

fun main() {
    // Creamos una lista de documentos
    var listaDocumento = listOf(
        Documento("Secreto USA", 439, 6, 12.5, true),
        Documento("UFO Arizona", 799, 2, 8.7, true),
        Documento("Texaco", 344, 1, 6.8, false),
        Documento("Matanza LV", 563, 3, 9.6, false)
    )

    println("KB total: %.2f".format(listaDocumento.calcularTamanoTotal { it -> it.tamanioKB }))

    listaDocumento.obtenerReporteCritico(
        { it -> it.esConfidencial },
        { it -> "Confidencial: ${it.titulo}" },
        { it -> "No confidencial: ${it.titulo}" })
        .forEach(::println)

    println(
        "KB total doble filtrado: %.2f".format(
            listaDocumento.filtrarYObtenerMetricaUnica(
                { it -> it.esConfidencial },
                { it -> it.palabrasClave > 2 },
                { it -> it.tamanioKB })
        )
    )
}

typealias documentoToDouble = (Documento) -> Double
typealias documentoToString = (Documento) -> String
typealias documentoCondicion = (Documento) -> Boolean

data class Documento(
    var titulo: String, var fechaCreacionDias: Int, var palabrasClave: Int,
    var tamanioKB: Double, var esConfidencial: Boolean
)

fun List<Documento>.calcularTamanoTotal(fn: documentoToDouble): Double {
    return this.sumOf { it -> fn(it) }
}

fun List<Documento>.obtenerReporteCritico(
    condicion: documentoCondicion,
    cumple: documentoToString,
    noCumple: documentoToString
): List<String> {
    return this.map { it ->
        var s = noCumple(it)
        if (condicion(it)) {
            s = cumple(it)
        }
        s
    }
}

fun List<Documento>.filtrarYObtenerMetricaUnica(
    filtroInclusion: documentoCondicion,
    filtroSecundaria: documentoCondicion,
    extractorDouble: documentoToDouble
): Double {
    return this.filter { it -> filtroInclusion(it) && filtroSecundaria(it) }
        .sumOf { it -> extractorDouble(it) }
}
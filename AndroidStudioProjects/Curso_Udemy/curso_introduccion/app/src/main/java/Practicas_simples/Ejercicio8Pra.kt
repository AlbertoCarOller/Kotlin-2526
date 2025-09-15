package Practicas_simples

fun main() {
    // Creamos una lista de eventos
    var eventos: MutableList<String> = mutableListOf("Concierto Sech", "Concierto Duki", "Concierto Manuel Carrasco", "Concierto Luxna")
    // Creamos una lista con los asistentes de cada evento, el índice es el mismo
    var numAsistentes: MutableList<Int> = mutableListOf(997, 3, 247, 341)
    // Creamos el objeto
    val ejercicio8Pra = Ejercicio8Pra()
    var eventosFamosos: MutableList<String> = ejercicio8Pra.eventosFamosos(eventos, numAsistentes)
    if (!eventosFamosos.isEmpty()) {
        println("2 eventos más famosos: $eventosFamosos")

    } else {
        println("No hay eventos")
    }
}

class Ejercicio8Pra {
    // Creamos una función que va a devolver los dos eventos más famosos
    fun eventosFamosos(eventos: MutableList<String>, numAsistentes: MutableList<Int>): MutableList<String> {
        if (eventos.size < 2 && numAsistentes.size < 2) {
            return mutableListOf()
        }
        var eventosFamosos: MutableList<String> = mutableListOf()
        var indice = -1
        var contador = 0
        while (contador < 2) {
            var numMayor = -1
            contador++
            for (i in 0 until numAsistentes.size) {
                if (numMayor < numAsistentes[i] && numAsistentes[i] > -1) {
                    numMayor = numAsistentes[i]
                    indice = i
                }
            }
            if (indice > -1) {
                // Añadimos el evento más famoso encontrado
                eventosFamosos = eventosFamosos.plus(eventos[indice]).toMutableList()
                // Eliminamos el evento una vez añadido
                numAsistentes.removeAt(indice)
                eventos.removeAt(indice)
            }
        }
        return eventosFamosos
    }
}
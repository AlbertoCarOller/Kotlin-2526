package Practicas_simples

fun main() {
    // Creamos el conjunto de invitados
    var invtados: MutableSet<String> =
        mutableSetOf("Sara García", "Alberto Carmona, Alberto Columé")
    // Creamos el objeto
    val ejercicio6Pra = Ejercicio6Pra()
    ejercicio6Pra.agregarInvitado("Respicio Godefrío", invtados)
    // Mostramos los invitados
    println("Invitados: $invtados")
}

class Ejercicio6Pra {
    // Creamos una función que va a añadir invitados al conjunto en caso de que no estén ya
    fun agregarInvitado(invitado: String, invitados: MutableSet<String>) {
        if (!invitados.contains(invitado)) invitados.add(invitado)
    }
}
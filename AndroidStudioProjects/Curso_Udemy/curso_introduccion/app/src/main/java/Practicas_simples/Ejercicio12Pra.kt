package Practicas_simples

import java.util.ArrayList

fun main() {

    var lista = arrayListOf<String>("Antonio", "Alberto", "Pepe", "Lolo", "Serapio")
    recorrerLista(ordenarLista(lista))
    lista.add("Paco")
    lista.remove("Pepe")
    recorrerLista(lista)
}

fun ordenarLista(lista: ArrayList<String>): ArrayList<String> {
    var listaOrdenada = lista
    listaOrdenada.sort()
    return listaOrdenada
}

fun recorrerLista(lista: ArrayList<String>) {
    lista.forEach { e -> println(e) }
}
package com.example.lazycolum.ui.theme

object GrupoNumeros { /* -> Creamos una clase anónima, no de crea instancias de estas clases,
 es como static por así decirlo */

    fun agregarNumeros(): MutableList<Int> {
        val listaNumeros = mutableListOf<Int>()
        for (i in 1..100) listaNumeros.add(i)
        return listaNumeros
    }
}
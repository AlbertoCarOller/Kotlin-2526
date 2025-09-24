package com.example.poo.PracticasSimples.AsistentePersonal

fun main() {
    var tarea1 = Tarea("Barrer la casa", Categoria.HOGAR, 2, false)
    var tarea2 = Tarea("Ayuda con el trabajo", Categoria.TRABAJO, 4, false)
    var tarea3 = Tarea("Dar de comer al perro", Categoria.HOGAR, 5, true)
    var tarea4 = Tarea("Llorar", Categoria.PERSONAL, 3, true)
    var tarea5 = Tarea("Deshaogarse", Categoria.PERSONAL, 2, false)

    with(GestorTareas) {
        this.agregarTarea(tarea1)
        this.agregarTarea(tarea2)
        this.agregarTarea(tarea3)
        this.agregarTarea(tarea4)
        this.agregarTarea(tarea5)

        this.tareasDeTrabajo()
        this.ordernarPorPrioridad()
        this.mostrarNombresPrioridad5()
        this.prioritariosSinCompletar()
    }
}

enum class Categoria(var tipo: String) {
    HOGAR("Hogar"),
    TRABAJO("Trabajo"),
    PERSONAL("Personal")
}

class AsistentePersonalException(mensaje: String) : Exception(mensaje)

data class Tarea(
    var nombre: String,
    var categoria: Categoria,
    var prioridad: Int,
    var completada: Boolean
) {
    init {
        if (prioridad > 5 || prioridad < 1) {
            throw AsistentePersonalException("La prioridad no pude ser mayor 5 ni menor a 1")
        }
    }
}

object GestorTareas {
    var listaTareas = mutableListOf<Tarea>()
    fun agregarTarea(tarea: Tarea) {
        if (!listaTareas.contains(tarea)) {
            listaTareas.add(tarea)
        }
    }

    // Mostramos las tareas que están orientadas al trabajo
    fun tareasDeTrabajo() {
        listaTareas.filter { tarea -> tarea.categoria == Categoria.TRABAJO }
            .forEach { tarea -> println(tarea) }
    }

    // Mostramos los nombres de las tareas con prioridad 5
    fun mostrarNombresPrioridad5() {
        listaTareas.filter { tarea -> tarea.prioridad == 5 }.map { tarea -> tarea.nombre }
            .forEach { tarea -> println(tarea) }
    }

    // Mostramos las tareas ordenadas de forma descendente
    fun ordernarPorPrioridad() {
        listaTareas.sortedByDescending { tarea -> tarea.prioridad }.forEach { tarea -> println(tarea) }
    }

    // Por cada tarea prioritaria sin completar se dará un aviso
    fun prioritariosSinCompletar() {
        listaTareas.filter { tarea -> ((tarea.prioridad == 4 || tarea.prioridad == 5) && !tarea.completada) }
            .forEach { tarea -> println("Alerta, la tarea ${tarea.nombre} prioritaria aún no se ha completado") }
    }
}
package com.example.retocomposables

// Creamos una data class que va a guardar información básica importante
data class Game(
    val nombre: String = "Invitado",
    val score: Int = 0,
    val level: Int = 0)
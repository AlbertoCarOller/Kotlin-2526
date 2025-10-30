package com.example.retocomposables

// Creamos una data class que va a guardar información básica importante
data class Game(
    val nombre: String = "Invitado",
    var score: Int = 0,
    var level: Int = 0)
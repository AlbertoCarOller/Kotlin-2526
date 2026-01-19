package com.example.unscramble.ui

// Creamos el uiState, contiene los valores (val) de la UI
data class GameUiState(
    // La palabra actual desordenada a ordenar, al principio estará vacío
    val currentScrambledWord: String = "",
    // Creamos una variable que va a indicarnos si la palabra metida por el usuario es correcta o no
    val isGuessedWordWrong: Boolean = false,
    // Creamos una variable score que va a almacenar la puntuación del usuario
    val score: Int = 0,
    // Creamos una variable que va a indicar por el número de palabra que vamos
    val currentWordCount: Int = 1,
    // Creamos una variable que va a indicar si el juego ha terminado o continua
    val isGameOver: Boolean = false
)
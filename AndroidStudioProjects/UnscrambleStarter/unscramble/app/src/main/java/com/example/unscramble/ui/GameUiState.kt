package com.example.unscramble.ui

// Creamos el uiState, contiene los valores (val) de la UI
data class GameUiState(
    // La palabra actual desordenada a ordenar, al principio estará vacío
    val currentScrambledWord: String = "",
    // Creamos una variable que va a indicarnos si la palabra metida por el usuario es correcta o no
    val isGuessedWordWrong: Boolean = false
)
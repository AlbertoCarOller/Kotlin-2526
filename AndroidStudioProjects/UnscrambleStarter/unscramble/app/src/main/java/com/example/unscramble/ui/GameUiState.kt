package com.example.unscramble.ui

// Creamos el uiState, contiene los valores (val) de la UI
data class GameUiState(
    // La palabra actual desordenada a ordenar
    val currentScrambledWord: String = ""
)
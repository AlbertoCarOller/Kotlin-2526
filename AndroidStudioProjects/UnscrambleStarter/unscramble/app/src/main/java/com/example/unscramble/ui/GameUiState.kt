package com.example.unscramble.ui

import com.example.unscramble.data.Language
import com.example.unscramble.data.LevelGame
import com.example.unscramble.datamodel.GameModel
import com.example.unscramble.datamodel.WordModel

// Creamos el uiState, contiene los valores (val) de la UI
data class GameUiState(
    // Creamos el valor que va a almacenar la palabra actual (normal)
    val currentWord: String = "",
    // Creamos la lista que va a almacenar las palabras usadas
    val usedWords: MutableList<String> = mutableListOf(),
    // Creamos el valor que va a almacenar el lenguaje de la app
    val language: String = Language.ENGLISH.language,
    // Creamos el valor que va a almacenar el nivel del juego
    val levelGame: Int = LevelGame.EASY.level,
    // Creamos un valor que va a indicar si está cargando o no el juego
    val isLoading: Boolean = true,
    // Creamos un valor que va a almacenar el máximo número de palabras (por defecto en nivel EASY)
    val maxNoWords: Int = LevelGame.EASY.level,
    // Creamos un valor que va a almacenar un mensaje de error para el usuario (null al principio)
    val userMessage: UserMessage? = null,
    // La palabra actual desordenada a ordenar, al principio estará vacío
    val currentScrambledWord: String = "",
    // Creamos una variable que va a indicarnos si la palabra metida por el usuario es correcta o no
    val isGuessedWordWrong: Boolean = false,
    // Creamos una variable score que va a almacenar la puntuación del usuario
    val score: Int = 0,
    // Creamos una variable que va a indicar por el número de palabra que vamos
    val currentWordCount: Int = 1,
    // Creamos una variable que va a indicar si el juego ha terminado o continua
    val isGameOver: Boolean = false,
    /* La lista de palabras solicitadas para el juego, cuando el usuario acierte sacamos una palabra
     de la lista, cuando esté vacía termina el juego */
    val wordsGame: MutableList<String> = mutableListOf(),
    // Creamos una lista donde guardar todos los juegos
    val listGame: MutableList<GameModel> = mutableListOf()
)

// Creamos una enum class que va a contener dos tipos de errores
enum class UserMessage {
    ERROR_ACCESSING_DATASTORE,
    ERROR_WRITING_DATASTORE,
    // Añadimos un nuevo error
    ERROR_GETTING_WORDS
}
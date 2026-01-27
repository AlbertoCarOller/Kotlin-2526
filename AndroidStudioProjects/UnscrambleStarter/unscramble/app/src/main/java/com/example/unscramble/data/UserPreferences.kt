package com.example.unscramble.data

// Creamos una data class donde vamos a almacenar las preferencias del usuario
data class UserPreferences(
    // El idioma de la app
    val language: String,
    // El nivel de dificultad
    val levelGame: Int
) {
    companion object {
        // Creamos la clave 'SETTINGS_FILE' es el nombre para la creación del fichero
        const val SETTINGS_FILE = "settings"
    }
}

// Creamos una enum class que va a almacenar el lenguaje con sus abreviaciones
enum class Language(val language: String) {
    ENGLISH("en"),
    SPANISH("es")
}

// Creamos una enum class que va a almacenar el nivel de dificultad
enum class LevelGame(val level: Int) {
    EASY(5),
    MEDIUM(10),
    HARD(15)
}
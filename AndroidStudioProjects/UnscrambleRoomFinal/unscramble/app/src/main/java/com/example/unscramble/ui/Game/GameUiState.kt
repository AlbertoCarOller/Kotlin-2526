package com.example.unscramble.ui.Game

import com.example.unscramble.data.Language
import com.example.unscramble.data.LevelGame

data class GameUiState(
    val wordsGame: MutableList<String> = mutableListOf(),
    val currentWord: String = "",
    val currentScrambledWord: String = "",
    val rightWords: MutableList<String> = mutableListOf(),
    val wrongWords: MutableList<String> = mutableListOf(),
    val isGuessedWordWrong : Boolean = false,
    val score: Int = 0,
    val isGameOver : Boolean = false,
    val language: String = Language.ENGLISH.language,
    val levelGame: Int = LevelGame.EASY.level,
    val isLoading: Boolean = true,
    val userMessage: UserMessage? = null
)

enum class UserMessage {
    ERROR_ACCESSING_DATASTORE,
    ERROR_WRITING_DATASTORE,
    ERROR_GETTING_WORDS,
    ERROR_GETTING_GAMES
}
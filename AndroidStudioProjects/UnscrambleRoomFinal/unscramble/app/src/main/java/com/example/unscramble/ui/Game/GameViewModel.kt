package com.example.unscramble.ui.Game

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.unscramble.data.Language
import com.example.unscramble.data.LevelGame
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.UserPreferences
import com.example.unscramble.data.UserPreferencesRepository
import com.example.unscramble.data.allSpanishWords
import com.example.unscramble.data.allWords
import com.example.unscramble.data.getEnglishWords
import com.example.unscramble.data.getSpanishWords
import com.example.unscramble.datamodel.GameModel
import com.example.unscramble.datamodel.WordModel
import com.example.unscramble.repository.GamesRepository
import com.example.unscramble.repository.WordsRepository
import com.example.unscramble.unscramblerelease.UnscrambleReleaseApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GameViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val wordsRepository: WordsRepository,
    private val gamesRepository: GamesRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UnscrambleReleaseApplication)
                GameViewModel(
                    application.userPreferencesRepository,
                    application.wordsRepository,
                    application.gamesRepository)
            }
        }
    }


    //Estado de la interfaz de usuario.
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //Valor de la palabra ingresada por el usuario.
    var userGuess by mutableStateOf("")
        private set

    //Bloque que se ejecuta con la creación del objeto.
    init {
//        viewModelScope.launch {
//            wordsRepository.insertWordList(getEnglishWords())
//            wordsRepository.insertWordList(getSpanishWords())
//        }
        viewModelScope.launch {
            // Flujo de estado transformado de lista de WordModel a lista de strings.
            val wordsFlow: StateFlow<List<String>> =
                userPreferencesRepository.userPrefs
                    //Toma el flujo de preferencias y lo mapea a un flujo de lista de WordModel.
                    //Cada ver que haya un cambio en las preferencias, se obtendrán nuevos WordModel.
                    .flatMapLatest { preferences ->
                        wordsRepository.getSomeRandomWordsByLanguage(
                            preferences.language,
                            preferences.levelGame
                        )
                    }
                    //Transforma la lista de WordModel en una lista de strings.
                    .map { wordList ->
                        wordList.map { it.title }
                    }
                    //Captura excepciones y actualiza el estado de la interfaz de usuario.
                    .catch { e ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                userMessage = UserMessage.ERROR_GETTING_WORDS,
                                isLoading = false
                            )
                        }
                        emit(emptyList()) // Emitir una lista vacía en caso de error
                    }
                    //Convierte el flujo en un flujo de estado con la lista de palabras.
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = emptyList()
                    )

            //Combina preferencias y lista de palabras en un solo flujo de estado.
            userPreferencesRepository.userPrefs
                .combine(wordsFlow) { preferences, words ->
                    Pair(preferences, words)
                }
                    //Inicialmente muestra un estado de carga.
                .onStart {
                    _uiState.update { currentState ->
                        currentState.copy(isLoading = true)
                    }
                }
                //Captura excepciones y actualiza el estado de la interfaz de usuario.
                .catch { e ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userMessage = UserMessage.ERROR_ACCESSING_DATASTORE,
                            isLoading = false
                        )
                    }
                }
                //Actualiza el estado de la interfaz de usuario con las preferencias y las palabras.
                .collect { (preferences, words) ->
                    Log.d("GameViewModel", "Entra a CollectCombined: $words")
                    // Si no hay palabras, actualiza el estado de la interfaz de usuario con un mensaje de error.
                    if (words.isEmpty()) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                userMessage = UserMessage.ERROR_GETTING_WORDS,
                                isLoading = false
                            )
                        }
                    } else {
                        //Actualiza el estado de la interfaz con las palabras del flujo y las opciones de preferencia.
                        updateState(words.toMutableList(), preferences.language, preferences.levelGame)
                    }
                }
        }
    }

    fun setSettings(language: String = Language.ENGLISH.language, levelGame: Int = LevelGame.EASY.level) {
        viewModelScope.launch() {
            try {
                userPreferencesRepository.savePreferences(
                    UserPreferences(language, levelGame)
                )
                Log.d("GameViewModel", "Guarda preferencias")
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        userMessage = UserMessage.ERROR_WRITING_DATASTORE
                    )
                }
            }
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun updateState(wordsGame: MutableList<String>,language: String, levelGame: Int) {
        val nextWord = if(wordsGame.isNotEmpty()) wordsGame.removeAt(0) else ""
        _uiState.value = if(wordsGame.isNotEmpty())
            GameUiState(
                wordsGame = wordsGame,
                currentWord = nextWord,
                currentScrambledWord = shuffleCurrentWord(nextWord),
                language = language,
                levelGame = levelGame,
                isLoading = false,
        ) else GameUiState(isGameOver = true)
        Log.d("GameViewModel", uiState.value.toString())
    }

    fun saveGame(user: String, resets: Boolean = false) {
        viewModelScope.launch {
            val game = GameModel(
                user = user,
                date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
                score = uiState.value.score,
                rightWords = uiState.value.rightWords.joinToString(),
                wrongWords = uiState.value.wrongWords.joinToString()
            )
            gamesRepository.insertGame(game)
            if(resets) resetGame()
        }
    }

    private fun resetGame() {
            viewModelScope.launch {
            val wordList = wordsRepository.getOnceSomeRandomWordsByLanguage(uiState.value.language, uiState.value.levelGame)
            updateState(wordList.map { it.title }.toMutableList(), uiState.value.language, uiState.value.levelGame)
        }
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(uiState.value.currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore,true)
        } else {
            // Si falla muestra un error.
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int, rightWord: Boolean) {
        if (uiState.value.wordsGame.isEmpty()){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    rightWords = if(rightWord) currentState.rightWords.apply { add(currentState.currentWord) } else currentState.rightWords,
                    wrongWords = if(!rightWord) currentState.wrongWords.apply { add(currentState.currentWord) } else currentState.wrongWords,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                val nextWord = currentState.wordsGame.first()
                currentState.copy(
                    wordsGame = currentState.wordsGame.apply { removeAt(0) },
                    isGuessedWordWrong = false,
                    rightWords = if(rightWord) currentState.rightWords.apply { add(currentState.currentWord) } else currentState.rightWords,
                    wrongWords = if(!rightWord) currentState.wrongWords.apply { add(currentState.currentWord) } else currentState.wrongWords,
                    currentWord = nextWord,
                    currentScrambledWord = shuffleCurrentWord(nextWord),
                    score = updatedScore
                )
            }
        }
        Log.d("GameViewModel", uiState.value.toString())
    }

    fun skipWord() {
        updateGameState(uiState.value.score,false)
        //Borra el texto.
        updateUserGuess("")
    }
}

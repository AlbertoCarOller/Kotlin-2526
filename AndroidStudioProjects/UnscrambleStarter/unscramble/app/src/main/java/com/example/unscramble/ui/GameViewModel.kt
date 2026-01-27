package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.unscramble.data.AllSpanishWords
import com.example.unscramble.data.Language
import com.example.unscramble.data.LevelGame
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.UserPreferences
import com.example.unscramble.data.UserPreferencesRepository
import com.example.unscramble.data.allWords
import com.example.unscramble.unscramblerelease.UnscrambleReleaseApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Creamos el StateFlow, el controlador de datos, aquí por lo general siempre va a ir una sola variable uiState
class GameViewModel(
    // Pasamos por parámetros el repository (inyección de dependencias)
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    /* Creamos un companion object para que antes de iniciar el viewModel poder
    * crear un Factory personalizado para el viewModel que soporte el Repository,
    * viewModelFactory {} es una función especial que nos permite redefinir un factory,
    * al crear el ViewModel Android nos dá una bolsa de utilidades, en este bolsa está
    * la App genérica (Application), para acceder a esta debemos de llamarla con la key
    * 'APPLICATION_KEY', la casteamos a nuestra Application personalizada (UnscrambleReleaseApplication),
    * el GameViewModel(application.userPreferencesRepository) devuelto es la clase creada del ViewModel
    * con el Repository ya pasado por parámetros */
    companion object {
        // El Factory redefinido que soporte el Repository
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            // initializer {} -> Este indica como se debe de crear un ViewModel (dentro de viewModelFactory)
            initializer {
                val application = (this[APPLICATION_KEY] as UnscrambleReleaseApplication)
                GameViewModel(application.userPreferencesRepository)
            }
        }
    }

    /* NOTA IMPORTANTE: '_uiState' es totalmente nencesario ya que sin esta, esta clase no tendría
    * sentido, este atributo permite a Compose (la vista, la UI) ver los cambios que suceden y
    * cambiar de estado, esto lo hace mediante una data class que contiene los datos que van a ir
    * cambiando, a su vez se necesita el suscriptor de Compose, para que este esté escuchando
    * cosntantemente y se entere de los cambios de valores, el suscriptor es 'uiState', este
    * no debe ser privado para que desde Compose podamos acceder a él y hacer que compose se suscriba,
    * debe ser de solo lectura, en esta clase aparte de los atributos ya dichos, debe estar la lógica
    * del programa, es decir todas las funciones y atributos de apoyo para hacer según que acciones
    * deben de estar aquí  */

    /* Vamos a explicar de que se compone y que hace esta varible, para empezar es privada,
    * GameUiState() -> es la data class que contienen los datos iniciales,
    * MutableStateFlow() -> metemos la GameUiState() dentro de una caja, esa caja es MutableStateFlow(),
    * dentro de esta caja están los datos iniciales que lo que hace especial a esta caja es que cuando
    * los datos iniciales de GameUiState() cambian, avisa a la pantalla para que se redibuje */
    private val _uiState = MutableStateFlow(GameUiState())

    /* Esta es la versión a la que se suscribe la UI (Compose), asStateFlow() hace que sea de solo lectura,
    * esta es la copia de seguridad, transforma uiState a un StateFlow<GameUiState> lo que como ya he dicho
    * es de solo lectura */
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Creamos la variable que va a guardar la palabra ordenada actual
    //private lateinit var currentWord: String

    // Creamos un conjunto vacío donde se va a ir guardando las palabras utilizadas
    //private var useWords = mutableSetOf<String>()

    /* Esta variable va a almacenar la información que vaya escribiendo el usuario
    * en el TextForm para adivinar la palabra, se hace var para que sea manipulable
    * desde dentro de esta clase, fuera no podrá manipularse gracias a que hemos privatizado
    * el set, el valor con el cual va a empezar es un String vacío y se pone dentro de un
    * mutableStateOf() para que Compose puede enterarse de sus cambios y redibujarse, se hace
    * de esta forma en vez de directamente en la data class porque de esta forma ya que el usuario
    * por letra que escriba se tiene que actualizar la variable pues si estuviera en la data class
    * el proceso sería mucho más complejo y costoso ya que habría que hacer la copia del GameUiState
    * por cada palabra que metiera el usuario */
    var userGuess by mutableStateOf("")
        private set

    /* Al crear esta clase lo primero a lo que se llama gracias al 'init' es la función
     resetGame() para que el juego genere la palabra aleatoria desordenada y le dé el
     valor 'currentScrambledWord' de la GameUiState ya que al principio el valor de este
     atributo es de "", el init lo posicionamos aquí abajo para que kotlin conozca los
     atributos que se utilizan en resetGame() */
//    init {
//        resetGame()
//    }

    // Es lo primero que se ejecuta al crear la clase (ViewModel)
    init {
        // Se lanza una corrutina para el ViewModel
        viewModelScope.launch {
            // Cargamos los datos de la dataStore a través del Repository
            userPreferencesRepository.userPrefs
                // onStart() -> Este se ejecuta antes de que llegue el primer dato del flujo
                .onStart {
                    // Ponemos isLoading a true para que muestre una carga mientras que llegan los datos
                    _uiState.update { currentState ->
                        currentState.copy(isLoading = true)
                    }
                }
                // En caso de error se actualiza la uiState para guardar un mensaje de error
                .catch { _ ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userMessage = UserMessage.ERROR_ACCESSING_DATASTORE
                        )
                    }
                }
                // collect() -> Para cuando los datos ya han llegado pues realiza una acción
                .collect { preferences ->
                    // Actualiza los datos de la GameUIState
//                    _uiState.update { currentState ->
//                        currentState.copy(
//                            // Ponemos el lenguaje de las preferencias
//                            language = preferences.language,
//                            // Ponemos el nivel de las preferencias
//                            levelGame = preferences.levelGame,
//                            // Está cargando pasa a false para quitar el spiner de carga, ya que han venido los datos
//                            isLoading = false
//                        )
//                    }
                    // En este caso es cambiar las preferencias del jugador
                    updateStateSettings(preferences.language, preferences.levelGame)
                }
        }
    }

    /**
     * Esta función va a obtener una palabra del cojunto de palabras
     * de forma completamente random, en caso de que la palabra obtenida ya se
     * haya utilizado, se volverá a llamar a la función hasta que la palabra sea
     * nueva, después de esto se llamará a una función auxiliar de desordenará la
     * palabra 'shuffleCurrentWord()', una vez desordenada se devuelve, esta es una
     * función recursiva
     */
//    private fun pickRandomWordAndShuffle(): String {
//        // La función random() devuelve un elemento aleatorio de la lista de elementos
//        currentWord = allWords.random()
//        // Comprobamos que la palabra obtenida sea una palabra que ya haya salido (useWords)
//        if (useWords.contains(currentWord)) {
//            /* Se hace recursividad de la función, en caso de que no se haya obtenido una palabra nueva,
//            * se vuelve a llamar para intentar obtener otra y así constantemente hasta que sea diferente,
//            * cuando sea diferente la función la desordenará y la devolverá, por eso hace return de la función */
//            return pickRandomWordAndShuffle()
//            // En caso de que la palabra no se haya usado ya, se desordena
//        } else {
//            // Guardamos la palabra en la lista de palabras utilizada
//            useWords.add(currentWord)
//            // Devolvemos la palabra desordenada
//            return shuffleCurrentWord(currentWord)
//        }
//    }

    /**
     * Esta función va a desordenar la palabra pasada por parámetros,
     * devolviendo así la palabra desordenada
     */
//    private fun shuffleCurrentWord(word: String): String {
//        /* Creamos la variable que va a guardar la palabra como un array para posteriormente
//         poder acceder a la función de desordenar */
//        val shuffleWord = word.toCharArray()
//        /* Desordenamos el array por lo tanto así la palabra, mientras que la palabra ordenada
//         sea igual a la 'desordenada' seguimos intentando desordenar */
//        while (word == String(shuffleWord)) {
//            // shuffle() -> Sirve para desordenar un array de forma aleatoria
//            shuffleWord.shuffle()
//        }
//        // Una vez que la palabra está desordenada la devolvemos
//        return String(shuffleWord)
//    }

    /**
     * Esta función va a devolver el juego a los valores iniciales,
     * borra el contenido de la lista useWords, el valor del uiState
     * (que es la data class 'GameUiState') es reiniciado, se crea una
     * nueva la cual para darle valor a su atributo (la palabra desordenada)
     * llama a la función pickRandomWordAndShuffle()
     */
//    fun resetGame() {
//        /* Quitamos todas las palabras del conjunto de palabras usadas, pero confirmamos que
//         no sea nula */
//        useWords.let(MutableSet<String>::clear)
//        /* Aquí reiniciamos los valores, creamos una nueva GameUiState(), le pasamos la función
//         pickRandomWordAndShuffle() la cual va a devolver una palabra nueva desordenada, se la
//         estamos asignando a 'currentScrambledWord', comprobamso que no sea null */
//        _uiState.let { it.value = GameUiState(pickRandomWordAndShuffle()) }
//    }

    /**
     * Esta función va a actualizar la palabra anterior por la
     * nueva pasada por el usuario
     */
    fun updateUserGuess(guessedWord: String) {
        // Se actualiza la variable 'userGuess' a la pasado por parámetros
        userGuess = guessedWord
    }

    /**
     * Esta función va a comprobar que la palabra que haya introducido el usuario
     * sea la palabra a adivinar, en caso de que no se cambia el valor 'isGuessedWordWrong' a true,
     * en caso de que sí se cambia a false, actualizando con update()
     */
//    fun checkUserGuess() {
//        // En caso de que la palabra a adivinar sea igual a la palabra puesta por el usuario entra
//        if (userGuess.equals(currentWord, ignoreCase = true)) {
//            // Cambiamos a false, que está bien en caso de acierto e incrementamos el score en 20
//            //_uiState.update { it.copy(isGuessedWordWrong = false, score = it.score.plus(SCORE_INCREASE)) }
//            // Creamos un valor el cual representa el incremento de la puntuación del usuario
//            val updateScore = _uiState.value.score.plus(SCORE_INCREASE)
//            // Llamamos a la función 'updateGameState' para actualizar el estado del juego para adivinar la próxima palabra
//            updateGameState(updateScore)
//            // En caso de que no sea igual entra
//        } else {
//            // Cambiamos la varible de la data class a true, con update() la actualizamos
//            _uiState.update { it.copy(isGuessedWordWrong = true) }
//        }
//        // Una vez comprobado si es o no la palabra reseteamos el valor de la palabra metida por el usuario
//        updateUserGuess("")
//    }

    /**
     * Esta función va a actualizar (crear una nueva instacia de la data class) la data class
     * con los valores preparados para empezar una nueva ronda, se hace mediante la función
     * update() de este modo actualizamos el score, si la palabra es correcta (isGuessedWordWrong)
     * y la palabra actual desordenada que el usuario tiene que adivinar (currentScrambledWord),
     * incrementamos también el número de palabra por donde va
     */
//    private fun updateGameState(updateScore: Int) {
//        /* En caso de que el número/índice de palabras del usuario sea distinta al máximo(10)
//         podemos actualizar el estado del juego */
//        if (_uiState.value.currentWordCount != MAX_NO_OF_WORDS) {
//            /* Creamos una nueva data class, en la cual se pone a false que haya error,
//         el score se incrementa, y llamamos a la función pickRandomWordAndShuffle()
//         para coger una nueva palabra aleatoria desordenada, incrementamos el número
//         de palabra por donde va */
//            _uiState.update {
//                it.copy(
//                    isGuessedWordWrong = false,
//                    score = updateScore,
//                    currentScrambledWord = pickRandomWordAndShuffle(),
//                    // Aquí incrememtamos el número de palabra en 1 con la función inc()
//                    currentWordCount = it.currentWordCount.inc()
//                )
//            }
//            // En caso de que se haya llegado al número máximo de palabras entra
//        } else {
//            // Se actualiza la variable de juego terminado a true
//            _uiState.update { it.copy(isGameOver = true) }
//        }
//    }

    /**
     * Esta función se salta la palabra actual, buscando así una nueva, manteniendo
     * la puntuación, incrementando el número/índice de palabras por donde va el usuario
     * y reseteando lo puesto por el usuario en el TextField
     */
//    fun skipWord() {
//        /* Pasamos la misma puntuación, pero se busca una palabra nueva y se incrementa
//         el número de palabra por donde va el usuario */
//        updateGameState(_uiState.value.score)
//        // Reseteamos también lo introducido por el usuario en el TextField
//        updateUserGuess("")
//    }

    // A PARTIR DE AQUÍ LAS FUNCIONES COPIADAS COMO DICE EL LABORATORIO, LAS FUNCIONES MODIFICADAS

    /**
     * Esta funcón va a seleccionar una palabra random en inglés o español
     * dependiendo del lenguaje pasada por parámetros
     */
    private fun pickRandomWord(language: String): String {
        // Se elige una palabra aleatoria dependiendo del leguaje seleccionado
        val word =
            if (language == Language.SPANISH.language) AllSpanishWords.random() else allWords.random()
        return if (uiState.value.usedWords.contains(word)) {
            // En caso de que la palabra ya se haya seleccionado, llamamos otra vez a la función (recursiva)
            pickRandomWord(language)
            // En caso de que la palabta esté bien, pues se elige esa palabra
        } else {
            // La palabra nueva a devolver
            word
        }
    }

    /**
     * Esta función va a desordenar la palabra elegida
     * aleatoriamente
     */
    private fun shuffleCurrentWord(word: String): String {
        // La palabra pasa a ser un array de char para poder llamar a la función shuffle()
        val tempWord = word.toCharArray()
        // Desordenamos los char del array
        tempWord.shuffle()
        // Mientras sea la misma palabra ordenada sigue desordenando
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        // Devolvemos la palabra desordenada
        return String(tempWord)
    }

    /**
     * Esta función reinicia el estado del juego
     */
    fun updateStateSettings(language: String, levelGame: Int) {
        // Se elige una palabra random()
        val word = pickRandomWord(language)
        // Se actualiza el estado del juego con update()
        _uiState.update { currentState ->
            currentState.copy(
                // La palabra elegida
                currentWord = word,
                // La palabra desordenada
                currentScrambledWord = shuffleCurrentWord(word),
                // Se reinicia las palabras usadas
                usedWords = mutableListOf(word),
                language = language,
                levelGame = levelGame,
                score = 0,
                isLoading = false,
                isGameOver = false
            )
        }
    }

    /**
     * Esta función llama a la función anterior para reiniciar el juego
     */
    fun resetGame() {
        updateStateSettings(uiState.value.language, uiState.value.levelGame)
    }

    /**
     * Esta función comprueba que la palabra introducida por el usuario sea la correcta
     * en caso de que sí, se incrementa el SCORE, en caso de que no se muestra error
     * en el TextFormField
     */
    fun checkUserGuess() {
        // En caso de que sea la palabra
        if (userGuess.equals(uiState.value.currentWord, ignoreCase = true)) {
            // Se actualiza el score
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // Si falla se muestra un error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reseteamos la palabra
        updateUserGuess("")
    }

    /**
     * Esta función actualiza el estado del juego dependiendo de si ha terminado
     * o no el juego lo hará de una forma u otra
     */
    private fun updateGameState(updatedScore: Int) {
        // Comrueba que las palabras usadas sean las mismas que el nivel del juego (el número), entra
        if (uiState.value.usedWords.size == uiState.value.levelGame) {
            // Actualiza el estado
            _uiState.update { currentState ->
                currentState.copy(
                    // La palabra es correcta
                    isGuessedWordWrong = false,
                    // Se incrementa el SCORE
                    score = updatedScore,
                    // Se termina el juego (dialog)
                    isGameOver = true
                )
            }
            // En caso de que no haya terminado las rondas
        } else {
            // Actualiza
            _uiState.update { currentState ->
                // Se elige otra palabra random
                val word = pickRandomWord(currentState.language)
                currentState.copy(
                    // La palabra está bien
                    isGuessedWordWrong = false,
                    // Se añade la palabra usada
                    usedWords = currentState.usedWords.apply { add(word) },
                    // Se actualiza la palabra
                    currentWord = word,
                    // Se desordenada la palabra
                    currentScrambledWord = shuffleCurrentWord(word),
                    // Se incrementa el score
                    score = updatedScore,
                    // Se incrementa el índice de la palabra por la que va el usuario
                    currentWordCount = currentState.currentWordCount.inc()
                )
            }
        }
    }

    /**
     * Esta función se salta la palabra actual
     */
    fun skipWord() {
        // El score se mantiene
        updateGameState(_uiState.value.score)
        //Borra el texto
        updateUserGuess("")
    }


    /**
     * Esta función va a cambiar las preferencias del usuario,
     * es decir el lenguaje y el nivel de dificultad del juego
     */
    fun setSettings(
        language: String = Language.ENGLISH.language,
        levelGame: Int = LevelGame.EASY.level
    ) {
        /* viewModelScope -> Es un entorno atado a ViewModel;
        * launch {} -> Lanza una corrutina sin bloquear el hilo principal */
        viewModelScope.launch {
            try {
                // Llamamos a la función savePreferences() para cambiar el lenguaje e idioma
                userPreferencesRepository.savePreferences(
                    UserPreferences(language, levelGame)
                )
                // En caso de error entra
            } catch (e: Exception) {
                // Actualizamos el mensaje de error
                _uiState.update { currentState ->
                    currentState.copy(
                        userMessage = UserMessage.ERROR_WRITING_DATASTORE
                    )
                }
            }
        }
    }
}
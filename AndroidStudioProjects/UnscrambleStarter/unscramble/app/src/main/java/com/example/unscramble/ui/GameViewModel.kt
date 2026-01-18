package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.AllSpanishWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Creamos el StateFlow, el controlador de datos, aquí por lo general siempre va a ir una sola variable uiState
class GameViewModel : ViewModel() {

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
    private lateinit var currentWord: String

    // Creamos un conjunto vacío donde se va a ir guardando las palabras utilizadas
    private var useWords = mutableSetOf<String>()

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
    init {
        resetGame()
    }

    /**
     * Esta función va a obtener una palabra del cojunto de palabras
     * de forma completamente random, en caso de que la palabra obtenida ya se
     * haya utilizado, se volverá a llamar a la función hasta que la palabra sea
     * nueva, después de esto se llamará a una función auxiliar de desordenará la
     * palabra 'shuffleCurrentWord()', una vez desordenada se devuelve, esta es una
     * función recursiva
     */
    private fun pickRandomWordAndShuffle(): String {
        // La función random() devuelve un elemento aleatorio de la lista de elementos
        currentWord = AllSpanishWords.random()
        // Comprobamos que la palabra obtenida sea una palabra que ya haya salido (useWords)
        if (useWords.contains(currentWord)) {
            /* Se hace recursividad de la función, en caso de que no se haya obtenido una palabra nueva,
            * se vuelve a llamar para intentar obtener otra y así constantemente hasta que sea diferente,
            * cuando sea diferente la función la desordenará y la devolverá, por eso hace return de la función */
            return pickRandomWordAndShuffle()
            // En caso de que la palabra no se haya usado ya, se desordena
        } else {
            // Devolvemos la palabra desordenada
            return shuffleCurrentWord(currentWord)
        }
    }

    /**
     * Esta función va a desordenar la palabra pasada por parámetros,
     * devolviendo así la palabra desordenada
     */
    private fun shuffleCurrentWord(word: String): String {
        /* Creamos la variable que va a guardar la palabra como un array para posteriormente
         poder acceder a la función de desordenar */
        val shuffleWord = word.toCharArray()
        /* Desordenamos el array por lo tanto así la palabra, mientras que la palabra ordenada
         sea igual a la 'desordenada' seguimos intentando desordenar */
        while (word == String(shuffleWord)) {
            // shuffle() -> Sirve para desordenar un array de forma aleatoria
            shuffleWord.shuffle()
        }
        // Una vez que la palabra está desordenada la devolvemos
        return String(shuffleWord)
    }

    /**
     * Esta función va a devolver el juego a los valores iniciales,
     * borra el contenido de la lista useWords, el valor del uiState
     * (que es la data class 'GameUiState') es reiniciado, se crea una
     * nueva la cual para darle valor a su atributo (la palabra desordenada)
     * llama a la función pickRandomWordAndShuffle()
     */
    private fun resetGame() {
        /* Quitamos todas las palabras del conjunto de palabras usadas, pero confirmamos que
         no sea nula */
        useWords.let(MutableSet<String>::clear)
        /* Aquí reiniciamos los valores, creamos una nueva GameUiState(), le pasamos la función
         pickRandomWordAndShuffle() la cual va a devolver una palabra nueva desordenada, se la
         estamos asignando a 'currentScrambledWord', comprobamso que no sea null */
        _uiState.let { it.value = GameUiState(pickRandomWordAndShuffle()) }
    }

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
    fun checkUserGuess() {
        // En caso de que la palabra a adivinar sea igual a la palabra puesta por el usuario entra
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // Cambiamos a true, que está bien en caso de acierto
            _uiState.update { it.copy(isGuessedWordWrong = false) }
            // En caso de que no sea igual entra
        } else {
            // Cambiamos la varible de la data class a false, con update() la actualizamos
            _uiState.update { it.copy(isGuessedWordWrong = true) }
        }
        // Una vez comprobado si es o no la palabra reseteamos el valor de la palabra metida por el usuario
        updateUserGuess("")
    }
}
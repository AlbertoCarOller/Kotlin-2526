package com.example.retocomposables

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocomposables.ui.theme.RetoComposablesTheme
import kotlin.random.Random

/* NOTA: Las activities funcionan como una pila, cuando vas hacia delante, es decir navegas hacia delante
* (intent) la actividad anterior queda guardada en la pila, pero cuando navegamos hacia atrás la
* actividad se detruye de la pila y se redibuja, se destruye por finalización por eso ya la mochila
* no puede mantener los datos, esta se destruye también, pero cuando se destruye por re-creación
* la mochila sigue intacta */

class MainActivity : ComponentActivity() {
    // Valor constante estático
    companion object {
        const val TAG = "MainActivity"
    }

    // Con lateinit le decimos a Koytlin que pronto será inicializada para que no nos dé error
    private lateinit var game: Game

    //private var score: Int = 0 //-> Se la pasaríamos a PrincipalStructure
    //private var level: Int = 0 //-> Se la pasaríamos a PrincipalStructure

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Recibimos el nombre
        val nombre = intent.getStringExtra("NAME_LOG_KEY") ?: "Invitado"
        /* Inicializamos la clase juego aquí, IMPORTANTE: la única manera de asegurarnos
         que el Intent se ha creado correctamente es dentro del 'onCreate', una vez que ya se
          ha creado esta pantalla es cuando se le pueden pasar argumentos*/
        game = Game(nombre = nombre)
        // Recuperamos los datos en caso de que no esté vacía la mochila (antiguo)
        /*savedInstanceState?.let {
            score = savedInstanceState.getInt("SCORE_KEY")
            level = savedInstanceState.getInt("LEVEL_KEY")
        }*/
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetoComposablesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(stringResource(R.string.tituloMain))
                        })
                    }) { innerPadding ->
                    PrincipalStructure(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardamos en la mochila (antiguo)
        outState.putInt("SCORE_KEY", score)
        outState.putInt("LEVEL_KEY", level)
    }*/

    /**
     * Creamos un "widget" de texto al cual le vamos
     * a pasar un nombre y un valor para tal nombre,
     * por último un modificador que va a personalizar
     * el Text
     */
    @Composable
    fun ShowVariables(
        name: String, value: Int, modifier: Modifier = Modifier,
        // Le pasamos un tamaño de la letra, por defecto no se especifica (Unspecified)
        letraSize: TextUnit = TextUnit.Unspecified
    ) {
        Text("$name -> $value", modifier = modifier, fontSize = letraSize)
    }

    /**
     * Creamos un "widget" que va a mostrar un botón con el texto especificado,
     * personalizado gracias al modifier y que hará lo que diga la lambda
     * 'onClick() -> Unit'
     */
    @Composable
    fun StandardButton(
        label: String, modifier: Modifier = Modifier, onClick: () -> Unit
    ) {
        Button(modifier = modifier, onClick = onClick) {
            Text(label)
        }
    }

    /**
     * Este "widget" crea la esctructura principal necesaria para la creacion
     * del contenedor y los botones
     */
    @Composable
    fun PrincipalStructure(modifier: Modifier) {
        /* Declaramos las variables con 'by rememberSaveable { mutableIntStateOf(valor_inicial) }' para que no se
         borren los datos, los recuerde al redibujar, redibujar al cambiar el estado de una variable,
         añadiendo al rememberSaveable se guarda en la mochila, mutableStateOf() ->
         es el chivato que le avisa a la actividad que se redibuje si cambia
         el estado de la variable, SOLO SABE GUARDAR TIPOS BÁSICOS: Int, String, Boolean, etc. */
        var score by rememberSaveable { mutableIntStateOf(game.score) }
        var level by rememberSaveable { mutableIntStateOf(game.score) }
        // Color para el fondo donde se ve la puntuación
        //var color by rememberSaveable { mutableStateOf(Color.White) } -> Mal, el color no es un tipo básico
        // LocalContext.current -> Pasa el context actual de esta actividad
        val context = LocalContext.current /* -> Solo se puede llamar desde otro @Composable,
             en este caso el de la estructura principal, llama a la Activity principal siempre */
        // Estructura principal
        Column(modifier = modifier) {
            // Obtenemos los mensajes
            val mensajes = Pair(
                stringResource(R.string.mensaje1),
                stringResource(R.string.mensaje2)
            )

            // Saludo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hello ${game.nombre}!")
            }
            // Fila con Textos y botón
            Row(modifier = Modifier.padding(5.dp)) {
                // Creamos la Box donde se va a mostrar el 'Score' y 'Level'
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(80.dp)
                        .width(150.dp)
                        .background(color = colorPorLevel(game))
                ) {
                    // Creamos la columna con el Score y el Level
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Score
                        ShowVariables(stringResource(R.string.score), score, letraSize = 20.sp)
                        // Level
                        ShowVariables(stringResource(R.string.level), level, letraSize = 20.sp)
                    }
                }
                // Centramos el botón envolviéndolo en un Colum
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    // Creamos el botón de incrementar
                    StandardButton(stringResource(R.string.botonIncrementador), onClick = {
                        val valores = sumarScoreYLevel(game)
                        // Actualizamos los valores
                        score = valores.first
                        level = valores.second
                        //Lo refelejamos en la data class también
                        game.score = score
                        game.level = level
                        // Log.d() -> Es para escribir en el LogCat
                        Log.d("Increase Score", "Button Increase Score clicked")
                        comprobarLevel10(context, game, mensajes)
                    })
                    // Creamos espacio entre los dos botones
                    Spacer(modifier = Modifier.height(5.dp))
                    // Creamos el botón de decrementar
                    StandardButton(stringResource(R.string.botonDecrementador), onClick = {
                        // Actualizamos los valores
                        val resultados = restarScoreYLevel(game)
                        score = resultados.first
                        level = resultados.second
                        //Lo refelejamos en la data class también
                        game.score = score
                        game.level = level
                        // Log.d() -> Es para escribir en el LogCat
                        Log.d("Decrement Score", "Button Decrement Score clicked")
                        comprobarLevel10(context, game, mensajes)
                    })
                }
            }
            // Creamos una Colum para colocar el mensaje de level 5 y el botón de 'End Game'
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Mensaje cuando estés por el level 5, lo metemos en un Box
                Box(
                    modifier = Modifier.fillMaxHeight(fraction = 0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    // Muestra el mensaje si el nivel es 5
                    MensajeLevel5(game.level == 5)
                }
                // Creamos el botón para ir a la 'EndGameActivity' lo metemos en un Box
                Box(
                    modifier = Modifier.fillMaxHeight(fraction = 0.8f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    StandardButton(
                        "End Game",
                        onClick = {
                            // Se le pasa el context y los valores del score y level necesarios
                            goToEndGameActivity(context = context, game, game.level == 10, mensajes)
                            Log.d("End Game", "Button End Game clicked")
                        })
                }
            }
        }
    }

    // Método llamado cuando la actividad está a punto de hacerse visible
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: La actividad está a punto de ser visible.")
    }

    // Método llamado cuando la actividad se ha hecho visible
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: La actividad está en primer plano y se puede interactuar con ella.")
    }

    // Método llamado cuando otra actividad toma el control, poniendo esta actividad en segundo plano
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: La actividad está en segundo plano.")
    }

    // Método llamado cuando la actividad ya no es visible para el usuario
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: La actividad ya no es visible.")
    }

    // Método llamado justo antes de que la actividad sea destruida
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: La actividad ha sido destruida.")
    }
}

/**
 * Esta función va a recibir los valores de score y level
 * a partir de estos valores va a sumar el score y el level
 * correspondiente a la lógica pedida
 */
var sumarScoreYLevel: (Game) -> Pair<Int, Int> = { game ->
    // Nuevos valores
    var score = game.score
    var level = game.level
    // En caso de que level sea mayor a 1 hacemos el Random
    if (level >= 1) {
        // Incrementamos el valor de score de un número aleatorio entre 1 y el level
        score += (Random.nextInt(1, level + 1))
        // En caso de que level sea 0 o 1, se suma 1 directamente
    } else {
        score++
    }
    // Calculamos el valor del level dependiendo del Score
    level = (score / 10)
    // Devuelve el par de valores (Pair) como el Map.Entry
    Pair(score, level)
}

/**
 * Esta función va a restar el Score, va a restar el doble del
 * nivel actual, en caso de que esta cantidad sea mayor, se establecerá
 * el valor del score en 0, también tenemos en cuenta el level
 */
var restarScoreYLevel: (Game) -> Pair<Int, Int> = { game ->
    var score = game.score
    var level = game.level
    // Calculamos la cantidad a restar
    var cantidadRestar = game.level * 2
    // En caso de que el score que sea mayor a la cantidad a restar entra
    if (game.score > cantidadRestar) {
        score -= cantidadRestar
        level = (score / 10)
        // En caso que el score sea menor o igual a la cantidad a restarm directamente la pasamos a 0
    } else {
        score = 0
        level = 0
    }
    Pair(score, level)
}

/**
 * Esta función va a recibir el context, es decir el contexto de la clase o actividad actual
 * y después vamos a pasarle el score y level actual para posteriormente crear un 'Intent' que
 * lleve desde esta actividad a 'EndGameActivity' pasándole como argumento el 'score' y el 'level'
 */
fun goToEndGameActivity(
    context: Context,
    game: Game,
    level10: Boolean,
    mensajes: Pair<String, String>
) {
    // Creamos el 'Intent' con el contexto de actual, es decir esta clase, hasta la clase 'EndGameActivity'
    val intent = Intent(context, EndGameActivity::class.java)
    // Enviamos los datos con el .putExtra de los 'Intent'
    intent.putExtra("SCORE_KEY", game.score)
    intent.putExtra("LEVEL_KEY", game.level)
    intent.putExtra("NAME_KEY", game.nombre)
    // Creamos el mensaje que se va a enviar a la 'EndGameActivity'
    var mensaje = mensajes.first
    if (level10) {
        mensaje = mensajes.second
    }
    intent.putExtra("MENSAJE_KEY", mensaje)
    // Iniciamos la otra actividad, con la función '.startActivity(intent)', pasándole el 'Intent' necesario
    context.startActivity(intent)
}

/**
 * Este "widget" va a mostrar un texto en verde
 * cuando se cumpla una condición pasada por parámetros
 */
@Composable
fun MensajeLevel5(mostrar: Boolean) {
    if (mostrar) {
        Text(
            stringResource(R.string.mensajeBuenCamino),
            fontSize = 30.sp,
            color = Color.Green,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Esta función va a comprobar que el level sea 10 para ir así a la
 * pantalla 'EndGameActivity'
 */
fun comprobarLevel10(context: Context, game: Game, mensajes: Pair<String, String>) {
    if (game.level == 10) {
        goToEndGameActivity(context = context, game, true, mensajes)
    }
}

/**
 * Esta función va a devolver un color, este color será el que
 * se aplicará a un Box dependiendo de las comprobaciones de esta función
 */
var colorPorLevel: @Composable (game: Game) -> Color = {
    var color = colorResource(id = R.color.red)
    if (it.level in 3..6) {
        color = colorResource(id = R.color.amarillo)
    } else if (it.level in 7..9) {
        color = colorResource(R.color.verde)
    }
    color
}
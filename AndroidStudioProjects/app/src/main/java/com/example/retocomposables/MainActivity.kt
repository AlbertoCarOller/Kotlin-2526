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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocomposables.ui.theme.RetoComposablesTheme
import kotlin.random.Random

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
                            Text("Super Game Counter")
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
    fun StandardButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
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
          añadiendo al rememberSaveable se guarda en la mochila*/
        var score by rememberSaveable { mutableIntStateOf(game.score) }
        var level by rememberSaveable { mutableIntStateOf(game.score) }
        // Estructura principal
        Column(modifier = modifier) {
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
                        .background(color = Color.Red)
                ) {
                    // Creamos la columna con el Score y el Level
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Score
                        ShowVariables("Score", score, letraSize = 20.sp)
                        // Level
                        ShowVariables("Level", level, letraSize = 20.sp)
                    }
                }
                // Centramos el botón envolviéndolo en un Box
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    // Creamos el botón de incrementar
                    StandardButton("Increase Score", onClick = {
                        val valores = sumarScoreYLevel(score, level)
                        // Actualizamos los valores
                        score = valores.first
                        level = valores.second
                        //Lo refelejamos en la data class también
                        game.score = score
                        game.level = level
                        // Log.d() -> Es para escribir en el LogCat
                        Log.d("Increase Score", "Button Increase Score clicked")
                    })
                    // Creamos espacio entre los dos botones
                    Spacer(modifier = Modifier.height(5.dp))
                    // Creamos el botón de decrementar
                    StandardButton("Decrement Score", onClick = {
                        // Actualizamos los valores
                        score = restarScore(game.score, game.level)
                        //Lo refelejamos en la data class también
                        game.score = score
                        // Log.d() -> Es para escribir en el LogCat
                        Log.d("Decrement Score", "Button Decrement Score clicked")
                    })
                }
            }
            // LocalContext.current -> Pasa el context actual de esta actividad
            val context = LocalContext.current /* -> Solo se puede llamar desde otro @Composable,
             en este caso el de la estructura principal, llama a la Activity principal siempre */
            // Creamos el botón para ir a la 'EndGameActivity'
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                StandardButton("End Game", onClick = {
                    // Se le pasa el context y los valores del score y level necesarios
                    goToEndGameActivity(context = context, game.score, game.level, game.nombre)
                    Log.d("End Game", "Button End Game clicked")
                })
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
var sumarScoreYLevel : (scoreR: Int, levelR: Int) -> Pair<Int, Int> = { scoreR, levelR ->
    // Nuevos valores
    var score = scoreR
    var level = levelR
    // En caso de que level sea mayor a 1 hacemos el Random
    if (level >= 1) {
        // Incrementamos el valor de score de un número aleatorio entre 1 y el level
        score += (Random.Default.nextInt(1, level + 1))
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
 * el valor del score en 0
 */
var restarScore : (scoreR: Int, levelR: Int) -> Int = { scoreR, levelR ->
    var score = scoreR
    // Calculamos la cantidad a restar
    var cantidadRestar = levelR * 2
    // En caso de que el score que sea mayor a la cantidad a restar entra
    if (scoreR > cantidadRestar) {
        score -= cantidadRestar
        // En caso que el score sea menor o igual a la cantidad a restarm directamente la pasamos a 0
    } else {
        score = 0
    }
    score
}

/**
 * Esta función va a recibir el context, es decir el contexto de la clase o actividad actual
 * y después vamos a pasarle el score y level actual para posteriormente crear un 'Intent' que
 * lleve desde esta actividad a 'EndGameActivity' pasándole como argumento el 'score' y el 'level'
 */
fun goToEndGameActivity(context: Context, scoreR: Int, levelR: Int, name: String) {
    // Creamos el 'Intent' con el contexto de actual, es decir esta clase, hasta la clase 'EndGameActivity'
    val intent = Intent(context, EndGameActivity::class.java)
    // Enviamos los datos con el .putExtra de los 'Intent'
    intent.putExtra("SCORE_KEY", scoreR)
    intent.putExtra("LEVEL_KEY", levelR)
    intent.putExtra("NAME_KEY", name)
    // Iniciamos la otra actividad, con la función '.startActivity(intent)', pasándole el 'Intent' necesario
    context.startActivity(intent)
}
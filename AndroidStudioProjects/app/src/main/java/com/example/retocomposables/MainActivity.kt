package com.example.retocomposables

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocomposables.ui.theme.RetoComposablesTheme

class MainActivity : ComponentActivity() {
    // Valor constante estático
    companion object {
        const val TAG = "MainActivity"
    }

    private val name: String = "Alberto"

    // Creamos el objeto que contiene la información
    var InformacionBasica = InformacionBasica()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
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
        /* Declaramos las variables con 'by remember { mutableIntStateOf(valor_inicial) } para que no se
         borren los datos, los recuerde al redibujar, redibijar al cambiar el estado de una variable */
        var score by remember { mutableIntStateOf(0) }
        var level by remember { mutableIntStateOf(0) }
        // Estructura principal
        Column(modifier = modifier) {
            // Saludo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hello $name!")
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
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                ) {
                    // Creamos el botón
                    StandardButton("Increase Score", onClick = {
                        val valores = sumarScoreYLevel(score, level)
                        // Actualizamos los valores
                        score = valores.first
                        level = valores.second
                        // Log.d() -> Es para escribir en el LogCat
                        Log.d("Increase Score", "Button Increase Score clicked")
                    })
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                StandardButton("End Game", onClick = {
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
 * cada 10 de score en 1
 */
fun sumarScoreYLevel(scoreR: Int, levelR: Int): Pair<Int, Int> {
    // Nuevos valores
    var score = scoreR
    var level = levelR
    // Incrementamos el valor
    score++
    // Sumamos un nivel cada 10 de score
    if (score % 10 == 0) {
        level++
    }
    return Pair(score, level)
}
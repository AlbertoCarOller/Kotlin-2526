package com.example.retocomposables

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocomposables.ui.theme.RetoComposablesTheme

class EndGameActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetoComposablesTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar({
                        Text(stringResource(R.string.tituloEnd))
                    })
                }) { innerPadding ->
                    // Obtenemos el context de esta activity
                    val contextEndGame = LocalContext.current
                    // Creamos la columna principal que va a contener la información
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        /* IMPORTANTE: Para obtener los valores que pasa el intent es parecido a un Map,
                         se pone la clave y te devuelve el value, el método utilizado es .getStringExtra(key_name, defaultValue) */
                        // Primer mensaje de saludo al usuario o introducción a esta pantalla
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxHeight(fraction = 0.1f)
                        ) {
                            Text(
                                intent.getStringExtra("MENSAJE_KEY")
                                    ?.plus(" " + intent.getStringExtra("NAME_KEY")) ?: "Sin datos"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxHeight(fraction = 0.9f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(modifier = Modifier.fillMaxHeight(fraction = 0.6f)) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(stringResource(R.string.finDelJuego), fontSize = 40.sp)
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Informacion(
                                            stringResource(R.string.score),
                                            intent.getIntExtra("SCORE_KEY", 0),
                                            25.sp,
                                            Color.Red
                                        )
                                        Informacion(
                                            stringResource(R.string.level),
                                            intent.getIntExtra("LEVEL_KEY", 0),
                                            25.sp,
                                            Color.Red
                                        )
                                    }
                                }
                                Box(modifier = Modifier.fillMaxHeight(fraction = 0.4f)) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        /* Image(painter = painterResource(id = R.drawable.nombre_imagen)) ->
                                        * necesitamos el "widget" Image, dentro tiene la propiedad painterResource
                                        * que básicamente es dibijar o pintar recurso, debemos de llamar al id
                                        * de la imagen que básicamente será donde está ubicada, R es la clase
                                        * que se refiere a los recursos de la app, accedemos a drawable, que
                                        * es donde se guardan las imágenes y accedermos a ella, le ponemos
                                        * también una descripción*/
                                        Image(
                                            painter = painterResource(id = R.drawable.email),
                                            stringResource(R.string.botonEnviarDatos)
                                        )
                                        // Creamos un Pair con el asunto y el cuerpo
                                        val asuntoYCuerpo = Pair(
                                            stringResource(R.string.asunto)
                                                .format(intent.getStringExtra("NAME_KEY")),
                                            stringResource(R.string.cuerpo)
                                                .format(
                                                    intent.getStringExtra("NAME_KEY"),
                                                    intent.getIntExtra("SCORE_KEY", 0),
                                                    intent.getIntExtra("LEVEL_KEY", 0)
                                                )
                                        )
                                        Button(onClick = {
                                            enviarDatos(
                                                context = contextEndGame,
                                                asuntoYCuerpo = asuntoYCuerpo
                                            )
                                            Log.d("Enviar datos...", "Enviar datos... pulsado")
                                        }) {
                                            Text(stringResource(R.string.botonEnviarDatos))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Este "widget" muestra información, muestra un texto con un tamaño
 * y color que pasaremos por parámetros
 */
@Composable
fun Informacion(
    mensaje: String,
    valor: Int,
    letraSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Black
) {
    Text("$mensaje = $valor", fontSize = letraSize, color = color)
}

/**
 * Esta función va a enviar como asunto y cuerpo información sobre el usuario,
 * el level y el score, lo mandamos mediante un intent implícito, es decir que
 * es pide abrir otras apps fuera de mi propia app, fuera de mis activity
 */
fun enviarDatos(context: Context, asuntoYCuerpo: Pair<String, String>) {
    // Creamos un intent implícito, tenemos que pasarle la acción, que en este caso es ACTION_SEND (enviar)
    val intentEnvio = Intent(Intent.ACTION_SEND)
    // Este es el asunto
    intentEnvio.putExtra(
        "ASUNTO_KEY", asuntoYCuerpo.first
    )
    // Este es el cuerpo
    intentEnvio.putExtra(
        "CUERPO_KEY",
        asuntoYCuerpo.second
    )
    // Le decimos que el tipo de apps que tiene que abrir son apps de texto plano con .type
    intentEnvio.type = "text/plain"
    // Abrimos la actividad, que en este caso es la ventana emergente de aplicaciones
    context.startActivity(intentEnvio)
}
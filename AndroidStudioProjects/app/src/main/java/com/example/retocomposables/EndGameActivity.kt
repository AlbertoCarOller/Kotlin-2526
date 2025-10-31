package com.example.retocomposables

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
import androidx.compose.ui.res.painterResource
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
                        Text("Detalles")
                    })
                }) { innerPadding ->
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
                            Text("Aquí tienes los resultados ${intent.getStringExtra("MENSAJE_KEY") ?: "Sin datos"}")
                        }
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxHeight(fraction = 0.9f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(modifier = Modifier.fillMaxHeight(fraction = 0.7f)) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Game Over!", fontSize = 50.sp)
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Informacion(
                                            "Score", intent.getIntExtra("SCORE_KEY", 0),
                                            30.sp, Color.Red
                                        )
                                        Informacion(
                                            "Level", intent.getIntExtra("LEVEL_KEY", 0),
                                            30.sp, Color.Red
                                        )
                                    }
                                }
                                Box(modifier = Modifier.fillMaxHeight(fraction = 0.3f)) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                        /* Image(painter = painterResource(id = R.drawable.nombre_imagen)) ->
                                        * necesitamos el "widget" Image, dentro tiene la propiedad painterResource
                                        * que básicamente es dibijar o pintar recurso, debemos de llamar al id
                                        * de la imagen que básicamente será donde está ubicada, R es la clase
                                        * que se refiere a los recursos de la app, accedemos a drawable, que
                                        * es donde se guardan las imágenes y accedermos a ella, le ponemos
                                        * también una descripción*/
                                        //TODO: poner otra imagen
                                        Image(
                                            painter = painterResource(id = R.drawable.bosque),
                                            "Bosque"
                                        )
                                        Button(onClick = {
                                            Log.d("Enviar datos...", "Enviar datos... pulsado")
                                        }) {
                                            Text("Enviar datos...")
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
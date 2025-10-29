package com.example.retocomposables

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                        Text("Aquí tienes los resultados ${intent.getStringExtra("NAME_KEY") ?: "Sin datos"}")
                        Spacer(modifier = Modifier.height(150.dp))
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
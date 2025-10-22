package com.example.kotlinuiaprendizaje

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlinuiaprendizaje.ui.theme.KotlinUIAprendizajeTheme

// MainActivity -> La ventana interactiva de la app
class MainActivity : ComponentActivity() {
    // onCreate -> Es el punto de entrada (aquí va el código)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContent -> Es el puente entre la ventana (MainActivity) y Jetpack Compose (UI)
        setContent {
            // KotlinUIAprendizajeTheme -> Tema, como se debe de ver la UI, colores y demás
            KotlinUIAprendizajeTheme {
                /* Scaffold -> Es el esqueleto básico de una pantalla.
                *  modifier -> Es un atributo del Scaffold que le dice al Scaffold como debe ser,
                * es decir, su tamaño, color, bordes, etc.
                * Modifier.fillMaxSize() -> Le dice al Scaffold que ocupe por completo el espacio
                * que le ha dado su contenedor padre, es decir en este caso que ocupe el tamaño completo del
                * dispositivo, porque le dice al Scaffold que coga el tamaño completo de su padre,
                * en este caso es el KotlinUIAprendizajeTheme, que a su vez el padre de este es
                * setContent, es decir que coge toda la pantalla */
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text("Hola $name")
        Text("Hola Composable")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinUIAprendizajeTheme {
        Greeting("Android")
    }
}
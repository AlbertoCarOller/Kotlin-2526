package com.example.comparadornumeros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.* // <-- Scaffold, TopAppBar, etc. están aquí
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comparadornumeros.ui.theme.ComparadorNumerosTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class) // Necesario para Scaffold y TopAppBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var num1 by rememberSaveable { mutableStateOf("") }
            var num2 by rememberSaveable { mutableStateOf("") }
            val scrollState = rememberScrollState()
            val context = LocalContext.current
            ComparadorNumerosTheme() {
                // Scaffold nos da la estructura base de la app (como el fondo).
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // Opcional: Podemos añadir una barra superior fácilmente
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                "Comparador de Números",
                                fontSize = 25.sp, fontFamily = FontFamily.Monospace
                            )
                        })
                    }
                ) { innerPadding ->
                    // 'innerPadding' es el espacio seguro que deja Scaffold
                    // para que nuestro contenido no se ponga debajo de la TopBar.

                    // Pasamos ese padding a nuestra pantalla.
                    PantallaComparador(
                        modifier = Modifier.padding(innerPadding),
                        num1 = num1, num2 = num2,
                        { newValue ->
                            num1 = if (newValue.isNotEmpty()) num1.plus(newValue)
                            else ""
                        },
                        { newValue ->
                            num2 = if (newValue.isNotEmpty()) num2.plus(newValue)
                            else ""
                        },
                        context,
                        scrollState = scrollState
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaComparador(
    modifier: Modifier = Modifier,
    num1: String,
    num2: String,
    subirValor1: (String) -> Unit,
    subirValor2: (String) -> Unit,
    context: Context,
    scrollState: ScrollState
) { // <-- CAMBIO 2: Aceptamos un Modifier
    Column(
        // Aplicamos el modifier que recibimos (que contiene el padding de Scaffold)
        // y luego añadimos nuestros propios modificadores.
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Sección del Resultado ---
        Text(
            text = "Elige un ganador",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))
        // --- Sección de los Comparadores ---
        Row(
            modifier = Modifier
                .fillMaxWidth(),
                //.weight(0.7f), -> Crea conflictos con el scrollState
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // -- Número 1 --
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Número 1", fontSize = 18.sp)
                Text(
                    text = num1, // Valor de ejemplo
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
                // Aquí irá la Botonera 1
                botoneraNumerica { newValue -> subirValor1(newValue) }
            }

            // -- Texto "VS" --
            Text(
                text = "VS", fontSize = 20.sp, fontWeight = FontWeight.Black,
                modifier = Modifier.align(Alignment.Top)
            )

            // -- Número 2 --
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Número 2", fontSize = 18.sp)
                Text(
                    text = num2, // Valor de ejemplo
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(8.dp)
                )
                // Aquí irá la Botonera 2
                botoneraNumerica { newValue -> subirValor2(newValue) }
            }
        }
        Spacer(modifier = Modifier.height(55.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
                //.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var mensaje = compararValoresYMostrar(num1, num2)
            Text(
                text = mensaje,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(70.dp))
            Button(onClick = { goToEndGameActivity(num1, num2, mensaje, context) }) {
                Text("Terminar")
            }
        }
    }
}

/**
 * Este función va a crear los botones para indicar
 * el número y el botón de eliminar
 */
@Composable
fun botoneraNumerica(onClickText: (String) -> Unit) {
    Column {
        var contador = 0
        // Creamos 3 Row
        for (i in 0..2) {
            Row {
                // Creamos los 3 campos de cada Row
                for (i in 0..2) {
                    contador++
                    // Lo guardamos en una varible que se crea por cada for interno, por lo que no cambia su estado
                    val valorActual = contador
                    Button(onClick = { onClickText(valorActual.toString()) }) {
                        Text("$contador")
                    }
                }
            }
        }
        Row {
            // Botón del 0
            Button(onClick = { onClickText("0") }) {
                Text("0")
            }
            // Botón para eliminar el número
            Button(onClick = { onClickText("") }) {
                Text("C")
            }
        }
    }
}

/**
 * Esta función devuelve un String que representa la
 * comparación entre los 2 números
 */
@Composable
fun compararValoresYMostrar(num1: String, num2: String): String {
    var mensaje: String
    try {
        // Transformamos los números a enteros
        val num1I = num1.toInt()
        val num2I = num2.toInt()
        // Una vez transformados, si no ha saltado excepción, comparamos
        mensaje = if (num1I > num2I) {
            "Número 1 es mayor"

        } else if (num1I < num2I) {
            "Número 2 es mayor"

        } else {
            "Son IGUALES"
        }
        // En caso de que haya expeción a transformar algún número mostrará ese mensaje
    } catch (e: NumberFormatException) {
        return "No hay datos suficientes"
    }
    return mensaje
}

fun goToEndGameActivity(num1: String, num2: String, mensaje: String, context: Context) {
    val intent = Intent(context, EndGameActivity::class.java)
    intent.putExtra("NUM1_KEY", num1)
    intent.putExtra("NUM2_KEY", num2)
    intent.putExtra("MENSAJE_KEY", mensaje)
    context.startActivity(intent)
}
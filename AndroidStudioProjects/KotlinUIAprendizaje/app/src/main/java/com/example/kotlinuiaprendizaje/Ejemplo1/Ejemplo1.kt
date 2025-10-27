package com.example.kotlinuiaprendizaje.Ejemplo1

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinuiaprendizaje.ui.theme.KotlinUIAprendizajeTheme

// Ponemos esta anotación porque TopAppBar y TopAppBarDefaults son de Material3Api que es experimental
@OptIn(ExperimentalMaterial3Api::class)
// Creamos una ventana con ComponentActivity
class Ejemplo1 : ComponentActivity() {
    // Creamos la variable constante nombre
    private val name = "Alberto"
    // Creamos el punto de entrada con el Bundle para guardar los datos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ocupa el completo de la pantalla, incluido las barras
        enableEdgeToEdge()
        // Creamos el puente
        setContent {
            // El tema del proyecto
            KotlinUIAprendizajeTheme {
                // La pantalla principal
                Scaffold(
                    // Sin llaves porque se le pasa un objeto ya preparado, Modifier en este caso, son datos
                    modifier = Modifier.fillMaxSize(),
                    // Llaves porque espera un composable, para dibujar en la pantalla
                    topBar = {
                        // Un AppBar centrado de Material3
                        CenterAlignedTopAppBar(
                            title = { Text("First App Bar") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White
                            )
                        )
                    },
                    // Le damos color de fondo
                    containerColor = Color(0xFFECECEC), // Color hexadecimal
                ) { innerPadding ->
                    // Columna principal
                    GameStateDisplay(name ,modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(Color(0xFFECECEC)),
                        Alignment.CenterHorizontally)
                }
            }
        }
    }
}

/**
 * Este "widget" va a representar recuadros, los cuales podremos
 * modifcar a nuestro antojo, representa los 4 recueadros en este
 * caso dentro de la Colum principal
 */
@Composable
fun Recuadro(
    contenido: List<String>,
    modifier: Modifier,
    alineacionHorizontalColumna: Alignment.Horizontal = Alignment.Start,
    alineacionVerticalColumna: Arrangement.Vertical = Arrangement.Top,
    espacios: Boolean,
    colorContenido: Color = Color.Black
) {
    // Creamos un Box, como Container en flutter, con el tamaño especificado, se debe de poner .dp para ello
    Box(modifier = modifier) {
        // Creamos el contenido
        // Columna para dividir el espacio y que no se sobreponga el contenido
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = alineacionHorizontalColumna,
            verticalArrangement = alineacionVerticalColumna
        ) {
            // .forEachIndexed { index, string ->...} un forEach pero tenemos el índice
            contenido.forEachIndexed { index, string ->
                // Colocamos el Texto
                Text(string, color = colorContenido)
                // Colocamos espacio en caso de que queramos
                if (espacios) {
                    // Si no es el último contenido
                    if (index < contenido.size - 1)
                    // Espacio
                        Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

/**
 * Este "widget" va a representar a toda la Columna, estructura base,
 * con sus respecto contenido
 */
@Composable
private fun GameStateDisplay(name: String, modifier: Modifier = Modifier, alineacionContenidoColum: Alignment.Horizontal) {
    // Columna principal
    Column(modifier = modifier, horizontalAlignment = alineacionContenidoColum) {
        // Cuerpo, es decir, todos los recuadros y espacios
        // Separación entre TopAppBar y el recuadro amarillo
        Spacer(modifier = Modifier.height(8.dp))
        // Recuadro amarillo
        Recuadro(
            listOf("Elemento en la primera fila, $name"),
            Modifier
                .size(width = 380.dp, height = 30.dp)
                .background(color = Color(0xFFFFC107))
                .border(width = 2.dp, color = Color(0xFF795548)),
            Alignment.CenterHorizontally,
            Arrangement.Center,
            false
        )
        /* Creamos la fila que va a contener los cuadros verde y azul, utilizamos
        * intrinsicSize = IntrinsicSize.Min pata que tenga ocupe el mínimo posible
        * el de sus hijos */
        Row(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(8.dp)
                .fillMaxWidth(),
            // Arrangement.SpaceBetween -> Espacio equitativa para sus hijos dentro de esta
            Arrangement.SpaceBetween
        ) {
            // Cuadro verde
            Recuadro(
                listOf("Elemento 1 en columna 1", "Elemento 2 en Columna 2"),
                Modifier
                    /* Modifier.fillMaxWidth(fraction = 0.33f) -> Le decimos que
                     queremos que ocupe el 33 % de todo el ancho */
                    .fillMaxWidth(fraction = 0.33f)
                    .height(120.dp)
                    .padding(8.dp)
                    .background(color = Color(0xFF8BC34A))
                    .border(
                        width = 2.dp,
                        color = Color(0xFF4CAF50)
                    ),
                espacios = true,
                colorContenido = Color.White
            )
            // Cuadro azul
            Recuadro(
                listOf("Elemento en Columna 2"),
                Modifier
                    .height(120.dp)
                    /* No se le puede dar una anchura con
                     .fillMaxWidth(fraction = 0.67f), da
                      conflictos al tener una alineación horizontal*/
                    .width(300.dp)
                    .padding(8.dp)
                    .background(Color(0xFF03A9F4))
                    .border(width = 2.dp, color = Color(0xFF0288D1)),
                Alignment.CenterHorizontally,
                Arrangement.Center,
                false,
                Color.White
            )
        }
        // Creamos el recuadro rojo claro, el más grande
        Recuadro(
            listOf("Elemento en la tercera fila"),
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(color = Color(0xFFE91E63))
                .border(width = 2.dp, color = Color(0xFFB0003A)),
            Alignment.CenterHorizontally,
            Arrangement.Bottom,
            false
        )
    }
}
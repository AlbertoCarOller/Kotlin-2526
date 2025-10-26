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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Color(0xFFECECEC)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Separación entre TopAppBar y el recuadro amarillo
                        Separacion(0, 10)
                        // Recuadro amarillo
                        Recuadro(
                            listOf("Elemento en la primera fila"),
                            Modifier
                                .size(width = 400.dp, height = 30.dp)
                                .background(color = Color(0xFFFFC107))
                                .border(width = 2.dp, color = Color(0xFF795548)),
                            Alignment.Center
                        )
                        // Segunda separación
                        Separacion(0, 20)
                        /* Creamos la fila que va a contener los cuadros verde y azul, utilizamos
                        * intrinsicSize = IntrinsicSize.Min pata que tenga ocupe el mínimo posible
                        * el de sus hijos */
                        Row(
                            modifier = Modifier
                                .height(intrinsicSize = IntrinsicSize.Min)
                                .padding(8.dp),
                            // Arrangement.SpaceBetween -> Espacio equitativa para sus hijos dentro de esta
                            Arrangement.SpaceBetween
                        ) {
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
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Recuadros, Box como en flutter Container
@Composable
fun Recuadro(
    contenido: List<String>,
    modifier: Modifier,
    alineacionContenido: Alignment = Alignment.TopStart
) {
    // Creamos un Box, como Container en flutter, con el tamaño especificado, se debe de poner .dp para ello
    Box(modifier = modifier, contentAlignment = alineacionContenido) {
        // Creamos el contenido
        // Columna para dividir el espacio y que no se sobreponga el contenido
        Column(modifier = Modifier.fillMaxSize()) {
            contenido.forEach { it -> Text(it) }
        }
    }
}

// Creamos este widget para crear separaciones
@Composable
fun Separacion(anchura: Int, altura: Int) {
    Box(modifier = Modifier.size(width = anchura.dp, height = altura.dp))
}
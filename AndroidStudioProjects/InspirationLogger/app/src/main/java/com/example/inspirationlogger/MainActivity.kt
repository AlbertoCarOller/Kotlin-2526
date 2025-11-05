package com.example.inspirationlogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspirationlogger.ui.theme.InspirationLoggerTheme
import java.util.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Modifier.padding().border() -> El padding es un margen (externo).
            //Modifier.border().padding() -> El padding es un relleno (interno).
            // Creamos las variables necesarias
            var energia by rememberSaveable { mutableStateOf(100) }
            var ideas by rememberSaveable { mutableStateOf(0) }
            InspirationLoggerTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                stringResource(R.string.saludoCreativo)
                                    .format(
                                        intent.getStringExtra("NAME_KEY"),
                                        intent.getStringExtra("YEARS_KEY")
                                    )
                            )
                        }
                    )
                }) { innerPadding ->
                    // Creamos la estructura principal
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            // ContentScale.Crop -> Cogerá todo el tamaño de Box sin que se deforme
                            Image(
                                // alpha() -> Para cambiar la opacidad de una imagen
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(0.4f),
                                contentScale = ContentScale.Crop,
                                painter = cambiarBombilla(ideas),
                                contentDescription = "Bombilla",
                            )
                            // Fila que contiene la información de la energía y las ideas
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    stringResource(R.string.mostrarEnergia)
                                        .format(energia),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.verde)
                                )
                                Text(
                                    stringResource(R.string.mostrarIdeas)
                                        .format(ideas),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.verde),

                                    )
                            }
                        }
                        Column(
                            modifier = Modifier.padding(top = 30.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            botonAccion(
                                stringResource(R.string.botonInspiracion),
                                {
                                    var resultado = restarEnergiaYAumentarIdeas(energia, ideas)
                                    energia = resultado.first
                                    ideas = resultado.second
                                })
                            botonAccion(
                                stringResource(R.string.botonCafe),
                                {})
                        }
                    }
                }
            }
        }
    }
}

/**
 * Este "Widget" va a representar un botón, al cual
 * se le va a poder poner un nombre y una acción
 */
var botonAccion: @Composable (String, () -> Unit) -> Unit = { nombreBoton, accion ->
    Button(onClick = accion) {
        Text(nombreBoton)
    }
}

/**
 * Esta función va a restar un número aleatorio a a la energía, pero a la vez
 * aumenta el número de ideas aleatoriamente
 */
var restarEnergiaYAumentarIdeas: (Int, Int) -> Pair<Int, Int> = { energia, ideas ->
    // Creamos las variables que van a ser el nuevo valor de la energía y las ideas
    var energiaC = energia
    var ideasC = ideas
    // Generamos el número que se le va a restar a la energia
    var numRestarEnergia = kotlin.random.Random.nextInt(10, 26)
    if (numRestarEnergia <= energiaC) energiaC -= numRestarEnergia else energiaC = 0
    // Sumamos directamente a la idea el número generado
    ideasC += kotlin.random.Random.nextInt(1, 6)
    Pair(energiaC, ideasC)
}

/**
 * Esta función va a cambiar la imagen de fondo de la bombilla
 * dependiendo del nivel de ideas actual
 */
var cambiarBombilla: @Composable (Int) -> Painter = {
    if (it >= 5) painterResource(R.drawable.bombilla_encendida)
    else painterResource(R.drawable.bombilla_apagada)
}
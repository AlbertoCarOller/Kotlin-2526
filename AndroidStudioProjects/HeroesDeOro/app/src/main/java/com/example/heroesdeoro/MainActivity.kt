package com.example.heroesdeoro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heroesdeoro.ui.theme.HeroesDeOroTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var salud by rememberSaveable { mutableStateOf(100) }
            var oro by rememberSaveable { mutableStateOf(0) }
            var modoRiesgo by rememberSaveable { mutableStateOf(false) }
            var colores = cambiarColores(modoRiesgo)
            val context = LocalContext.current
            HeroesDeOroTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                stringResource(R.string.saludoHeroe)
                                    .format(intent.getStringExtra("HEROE_KEY")),
                                fontSize = 18.sp,
                                color = colores.second,
                                fontWeight = FontWeight.ExtraBold
                            )
                        })
                    }) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Gran Cañón",
                            painter = cambiarFondo(modoRiesgo)
                        )
                        Row(
                            modifier = Modifier.align(Alignment.TopCenter),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            boton(
                                stringResource(R.string.botonCueva),
                                colores,
                                {
                                    var resultados = accionExplorarCuevas(modoRiesgo, salud, oro)
                                    salud = resultados.first
                                    oro = resultados.second
                                }, salud == 0,
                                Modifier
                            )
                            boton(
                                stringResource(R.string.botonPosada),
                                colores,
                                {
                                    var resultados = accionDescansarPosada(salud, oro)
                                    salud = resultados.first
                                    oro = resultados.second
                                }, salud == 100 || oro == 0,
                                Modifier
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(colores.first)
                                        .border(
                                            width = 3.dp,
                                            color = colores.second
                                        )
                                        .fillMaxWidth(0.4f)
                                        .height(70.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                    ) {
                                        Text(
                                            stringResource(R.string.mostrarSalud).format(salud),
                                            fontWeight = FontWeight.Black
                                        )
                                        Text(
                                            stringResource(R.string.mostrarOro).format(oro),
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier.fillMaxWidth(0.6f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            stringResource(R.string.modoRiesgo),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Switch(
                                            checked = modoRiesgo,
                                            onCheckedChange = { modoRiesgo = it },
                                            colors = SwitchDefaults
                                                .colors(
                                                    checkedThumbColor = colores.first,
                                                    checkedTrackColor = colores.second,
                                                    uncheckedThumbColor = colores.first,
                                                    uncheckedTrackColor = colores.second
                                                )
                                        )
                                    }
                                }
                            }
                        }
                        boton(
                            stringResource(R.string.botonFinal),
                            colores,
                            { goToEndActivity(context, salud, oro,
                                intent.getStringExtra("HEROE_KEY") ?: "Invitado") },
                            false,
                            Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Esta función va a cambiar el fondo de pantalla dependiendo
 * de si está el modo riesgo activado o no
 */
var cambiarFondo: @Composable (Boolean) -> Painter = { riesgo ->
    if (!riesgo) painterResource(R.drawable.gran_canon)
    else painterResource(R.drawable.gran_cano_noche)
}

/**
 * Esta función va a cambiar los colores de algunos elementos
 * dependiendo de
 */
var cambiarColores: @Composable (Boolean) -> Pair<Color, Color> = { riesgo ->
    if (!riesgo) Pair(colorResource(R.color.atardecer), colorResource(R.color.marron))
    else Pair(colorResource(R.color.anochecer), colorResource(R.color.anochecer2))
}

/**
 * Este "Widget" va a representar un botón personalizado
 */
var boton: @Composable (String, Pair<Color, Color>, () -> Unit, Boolean, Modifier) -> Unit =
    { texto, colores, accion, disponible, modifier ->
        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = colores.first,
                contentColor = colores.second
            ), onClick = accion,
            enabled = !disponible
        ) {
            Text(text = texto)
        }
    }

/**
 * Esta función va a calcular al explorar las cuevas dependiendo de si estamos
 * en modo riesgo o no el oro y salud, la salud se va restando, el oro se va ganando
 */
var accionExplorarCuevas: (Boolean, Int, Int) -> Pair<Int, Int> = { modoRiesgo, salud, oro ->
    var saludP = salud
    var oroP = oro
    if (!modoRiesgo) {
        var restarSalud = (5..10).random()
        saludP = if (salud >= restarSalud) salud - restarSalud else 0
        oroP += (10..20).random()

    } else {
        var restarSalud = (20..30).random()
        saludP = if (salud >= restarSalud) salud - restarSalud else 0
        oroP += (40..60).random()
    }
    Pair(saludP, oroP)
}

/**
 * Esta función va sumar la salud en un número aleatorio y va a ir restando
 * el oro de 5 en 5
 */
var accionDescansarPosada: (Int, Int) -> Pair<Int, Int> = { salud, oro ->
    var saludP = salud
    var oroP = oro
    var sumarVida = (10..20).random()
    saludP = if ((salud + sumarVida) < 100) salud + sumarVida else 100
    oroP = if ((oro - 5) > 0) oro - 5 else 0
    Pair(saludP, oroP)
}

/**
 * Esta función lleva a la 'EndGameActivity' llevando a su vez el nombre, salud
 * y la cantidad de oro obtenido
 */
var goToEndActivity: (Context, Int, Int, String) -> Unit = { context, salud, oro, nombre ->
    var intent = Intent(context, EndGameActivity::class.java)
    intent.putExtra("HEROE_KEY", nombre)
    intent.putExtra("SALUD_KEY", salud)
    intent.putExtra("ORO_KEY", oro)
    context.startActivity(intent)
}
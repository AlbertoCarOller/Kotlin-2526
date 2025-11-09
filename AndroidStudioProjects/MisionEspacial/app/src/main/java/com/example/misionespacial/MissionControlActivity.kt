package com.example.misionespacial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.misionespacial.ui.theme.MisionEspacialTheme

class MissionControlActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Creamos un saver para que 'rememberSaveable' sepa como guardar un objeto propio, en este caso la clase 'Datos'
            val datosSaver = Saver<Datos, List<Any>>(
                save = {
                    listOf(
                        it.nombre.value,
                        it.combustible.value,
                        it.escudos.value
                    )
                }, // -> Los datos a guardar
                restore = { /* -> Como crear un objeto de este tipo, cuando los extrae, los extrae
                 como tipos básicos, por eso hay que pasarlos a mutableStateOf */
                    Datos(
                        mutableStateOf(it[0] as String),
                        mutableIntStateOf(it[1] as Int),
                        mutableIntStateOf(it[2] as Int)
                    )
                }
            )
            /* Aquí no se utiliza el 'by' porque no se crea directamente un 'mutableStateOf()',
            * no se delega directamente a una varible simple, se deben de crear con todos los datos, la clase Datos */
            val datos = rememberSaveable(saver = datosSaver) {
                Datos(
                    // Le damos el valor del nombre, para acceder al valor hay que hacer .value, ya que son mutableStateOf()
                    nombre = mutableStateOf(intent.getStringExtra("NAME_KEY") ?: "Invitado"),
                    combustible = mutableIntStateOf(100),
                    escudos = mutableIntStateOf(100)

                )
            }
            // Creamos las variables
            //var  combustible by rememberSaveable { mutableIntStateOf(0) }
            //var  escudos by rememberSaveable { mutableIntStateOf(0) }
            //var nombre = intent.getStringExtra("NAME_KEY") ?: "Invitado"
            MisionEspacialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                stringResource(R.string.tituloMissionControl)
                                    .format(datos.nombre.value),
                                fontFamily = FontFamily.Monospace
                            )
                        })
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Box(modifier = Modifier.weight(0.6f)) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Espacio",
                                painter = painterResource(R.drawable.espacio)
                            )
                            textosInfo(
                                stringResource(R.string.mostrarCombustible)
                                    .format(datos.combustible.value),
                                Modifier.align(Alignment.TopStart)
                            )
                            textosInfo(
                                stringResource(R.string.mostrarEscudos)
                                    .format(datos.escudos.value),
                                Modifier.align(Alignment.TopEnd)
                            )
                            Image(
                                modifier = Modifier.align(Alignment.Center),
                                contentDescription = "Nave espacial",
                                painter = painterResource(R.drawable.nave_espacial)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.4f)
                                .background(color = colorResource(R.color.gris)),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            botonesAcelerarEscudos(datos) {
                                datos.combustible.value = it.first
                                datos.escudos.value = it.second
                            }
                            var mensaje = stringResource(R.string.mensajeCompartir)
                            Button(
                                onClick = { compartir(this@MissionControlActivity, mensaje) },
                                colors = ButtonDefaults.buttonColors(colorResource(R.color.colorMoradoEspacial))
                            ) {
                                Text(stringResource(R.string.botonEnviarInforme))
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Creamos un "widget" que va a representar el texto de información
 * como el combustible o escudos
 */
@Composable
fun textosInfo(mensaje: String, modifier: Modifier) {
    Text(
        mensaje,
        fontFamily = FontFamily.Monospace,
        color = colorResource(R.color.white),
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier
    )
}

fun accionAcelerar(datos: Datos): Pair<Int, Int> {
    var combustibleC = datos.combustible.value
    var escudosC = datos.escudos.value
    combustibleC -= (10..20).random()
    escudosC -= (5..10).random()
    return Pair(
        combustibleC.coerceAtLeast(0),
        escudosC.coerceAtLeast(0)
    )
}

fun accionRecargarEscudos(datos: Datos): Pair<Int, Int> {
    var combustibleC = datos.combustible.value
    var escudosC = datos.escudos.value
    combustibleC -= 5
    escudosC += (10..15).random()
    return Pair(
        combustibleC.coerceAtLeast(0),
        escudosC.coerceAtMost(100)
    )

}

@Composable
fun botonesAcelerarEscudos(datos: Datos, subirValor: (Pair<Int, Int>) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { subirValor(accionAcelerar(datos)) },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.colorMoradoEspacial)),
            enabled = datos.combustible.value >= 20 && datos.escudos.value >= 10,
        ) {
            Text(stringResource(R.string.botonAcelerar), fontFamily = FontFamily.Monospace)
        }
        Button(
            onClick = { subirValor(accionRecargarEscudos(datos)) },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.colorMoradoEspacial)),
            enabled = datos.combustible.value >= 5 && datos.escudos.value < 100
        ) {
            Text(stringResource(R.string.botonRecargarEscudo), fontFamily = FontFamily.Monospace)
        }
        Button(
            onClick = { subirValor(Pair(100, 100)) },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.colorMoradoEspacial)),
            enabled = datos.combustible.value != 100 || datos.escudos.value != 100
        ) {
            Text(stringResource(R.string.botonRepostar), fontFamily = FontFamily.Monospace)
        }
    }
}

fun compartir(context: Context, mensaje: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra("MENSAJE_KEY", mensaje)
    context.startActivity(intent)
}
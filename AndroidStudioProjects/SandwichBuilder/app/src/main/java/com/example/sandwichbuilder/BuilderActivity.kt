package com.example.sandwichbuilder

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sandwichbuilder.ui.theme.SandwichBuilderTheme

class BuilderActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Creamos las variables
            var lechugaCount by rememberSaveable { mutableStateOf(0) }
            var quesoCount by rememberSaveable { mutableStateOf(0) }
            var baconCount by rememberSaveable { mutableStateOf(0) }
            /* Creamos un estado del scroll de la columna de botones
            * para que al redibujar tenga un marcador y no se pierda
            * el estado de por donde iba el scroll */
            var scrollState = rememberScrollState()
            var context = LocalContext.current
            var nombre = intent.getStringExtra("NAME_KEY")
            SandwichBuilderTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            stringResource(R.string.tituloBuilder)
                                .format(nombre),
                            fontSize = 27.sp, fontFamily = FontFamily.Cursive
                        )
                    })
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Box(modifier = Modifier.weight(0.7f)) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.pan_arriba),
                                    contentDescription = "Pan de arriba", Modifier.fillMaxSize(0.2f)
                                )
                                if (lechugaCount > 0) {
                                    Image(
                                        painter = painterResource(R.drawable.lechuga),
                                        contentDescription = "Lechuga", Modifier.fillMaxSize(0.2f)
                                    )
                                }
                                if (quesoCount > 0) {
                                    Image(
                                        painter = painterResource(R.drawable.queso),
                                        contentDescription = "Queso", Modifier.fillMaxSize(0.2f)
                                    )
                                }
                                if (baconCount > 0) {
                                    Image(
                                        painter = painterResource(R.drawable.bacon),
                                        contentDescription = "Bacon", Modifier.fillMaxSize(0.2f)
                                    )
                                }
                                Image(
                                    painter = painterResource(R.drawable.pan_abajo),
                                    contentDescription = "Pan de abajo", Modifier.fillMaxSize(0.2f)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxWidth()
                                // Con verticalScroll permitimos que la columna pueda hacer scroll, SE LE DEBE PASAR UN SCROLLSTATE
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            seccionCondimento(
                                stringResource(R.string.nombreLechuga),
                                lechugaCount
                            ) { newValue -> lechugaCount = newValue }
                            seccionCondimento(
                                stringResource(R.string.nombreQueso).plus("   "),
                                quesoCount
                            ) { newValue -> quesoCount = newValue }
                            seccionCondimento(
                                stringResource(R.string.nombreBacon).plus("   "),
                                baconCount
                            ) { newValue -> baconCount = newValue }
                        }
                        var mensaje = stringResource(R.string.mensajeCompartir)
                            .format(nombre, lechugaCount, quesoCount, baconCount)
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults
                                .buttonColors(containerColor = colorResource(R.color.colorMostaza)),
                            onClick = {compartir(context, mensaje)}) {
                            Text(stringResource(R.string.botonCompartir))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Esta función va a sumar el condimento concreto en 1,
 * subiendo su cantidad
 */
var sumarCondimento: (Int) -> Int = { cantCondimento ->
    var cantidadCondimentoC = cantCondimento
    ++cantidadCondimentoC
}

/**
 * Esta función va a restar el condimento concreto en 1,
 * bajando su cantidad
 */
var restarCondimento: (Int) -> Int = { cantCondimento ->
    var cantCondimentoC = cantCondimento
    --cantCondimentoC
}

/**
 * Este "widget" se va a encargar de contener las filas que
 * representan cada apartado de condimentos, para restar la cantidad
 * y sumarla
 */
var seccionCondimento: @Composable (String, Int, (Int) -> Unit) -> Unit =
    { nombreIngrediente, cantidadIngrediente, subirResultado ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(nombreIngrediente, modifier = Modifier.weight(0.4f))
            Text("$cantidadIngrediente", Modifier.weight(0.2f))
            Button(
                modifier = Modifier.weight(0.2f),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.colorQueso)),
                // Subimos el valor que ha devuelto el incremento para dárselo a la variable real
                onClick = { subirResultado(sumarCondimento(cantidadIngrediente)) }) {
                Text("+")
            }
            Button(
                modifier = Modifier.weight(0.2f),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.colorQueso)),
                // Subimos el valor que ha devuelto el decremento para dárselo a la variable real
                onClick = { subirResultado(restarCondimento(cantidadIngrediente)) },
                enabled = cantidadIngrediente != 0,
            ) {
                Text("-")
            }
        }
    }

var compartir: (Context, String) -> Unit = { context, mensaje ->
    var intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra("MENSAJE_KEY", mensaje)
    context.startActivity(intent)
}
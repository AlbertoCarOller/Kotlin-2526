package com.example.heroesdeoro

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heroesdeoro.ui.theme.HeroesDeOroTheme

class EndGameActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var context = LocalContext.current
            HeroesDeOroTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            stringResource(R.string.despedida)
                                .format(intent.getStringExtra("HEROE_KEY")),
                            fontSize = 18.sp, color = colorResource(R.color.marron)
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
                            contentDescription = "Entrada de taberna",
                            painter = painterResource(R.drawable.taberna_oro)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .background(color = colorResource(R.color.marron)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    stringResource(R.string.mensajeSalud)
                                        .format(intent.getIntExtra("SALUD_KEY", 0)),
                                    fontSize = 22.sp, color = colorResource(R.color.atardecer)
                                )
                                Text(
                                    stringResource(R.string.mensajeOro)
                                        .format(intent.getIntExtra("ORO_KEY", 0)),
                                    fontSize = 22.sp, color = colorResource(R.color.atardecer)
                                )
                            }
                        }
                        var mensaje = stringResource(R.string.mensajeCompartir)
                            .format(
                                intent.getStringExtra("HEROE_KEY"),
                                intent.getIntExtra("SALUD_KEY", 0),
                                intent.getIntExtra("ORO_KEY", 0)
                            )
                        Button(
                            onClick = { compartir(context, mensaje) },
                            modifier = Modifier.align(Alignment.BottomCenter),
                            colors = ButtonDefaults
                                .buttonColors(
                                    containerColor = colorResource(R.color.marron),
                                    contentColor = colorResource(R.color.atardecer)
                                ),
                        ) {
                            Text(stringResource(R.string.botonCompartir))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Esta función comporte un mensaje personalizado con otras aplicaciones
 * de texto plano
 */
var compartir: (Context, String) -> Unit = { context, mensaje ->
    var intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plane"
    intent.putExtra("MENSAJE_KEY", mensaje)
    context.startActivity(intent)
}
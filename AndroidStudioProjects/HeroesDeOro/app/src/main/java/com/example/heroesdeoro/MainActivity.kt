package com.example.heroesdeoro

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
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
            HeroesDeOroTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                stringResource(R.string.saludoHeroe)
                                    .format(intent.getStringExtra("HEROE_KEY")),
                                fontSize = 18.sp,
                                color = cambiarColores(modoRiesgo).second,
                                fontWeight = FontWeight.ExtraBold
                            )
                        })
                    }) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Gran Cañón",
                            painter = cambiarFondo(modoRiesgo)
                        )
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
                                        .background(cambiarColores(modoRiesgo).first)
                                        .border(width = 3.dp, color = cambiarColores(modoRiesgo).second)
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
                                                    checkedThumbColor = cambiarColores(modoRiesgo).first,
                                                    checkedTrackColor = cambiarColores(modoRiesgo).second,
                                                    uncheckedThumbColor = cambiarColores(modoRiesgo).first,
                                                    uncheckedTrackColor = cambiarColores(modoRiesgo).second
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

var cambiarFondo: @Composable (Boolean) -> Painter = { riesgo ->
    if (!riesgo) painterResource(R.drawable.gran_canon)
    else painterResource(R.drawable.gran_cano_noche)
}

var cambiarColores: @Composable (Boolean) -> Pair<Color, Color> = { riesgo ->
    if (!riesgo) Pair(colorResource(R.color.dorado), colorResource(R.color.marron))
    else Pair(colorResource(R.color.anochecer), colorResource(R.color.anochecer2))
}
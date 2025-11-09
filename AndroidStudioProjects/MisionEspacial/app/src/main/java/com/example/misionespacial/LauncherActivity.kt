package com.example.misionespacial

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.example.misionespacial.ui.theme.MisionEspacialTheme

class LauncherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nombre by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current
            MisionEspacialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(stringResource(R.string.tituloLauncher),
                                fontFamily = FontFamily.Monospace
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
                            contentDescription = "Astronauta",
                            painter = painterResource(R.drawable.astronauta)
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextField(
                                value = nombre,
                                onValueChange = { newValue -> nombre = newValue },
                                label = { Text(stringResource(R.string.labelLaucher),
                                    fontFamily = FontFamily.Monospace) })
                            Button(
                                onClick = {goToMissionControlActivity(context, nombre)}, enabled = nombre.isNotBlank(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.colorMoradoEspacial)
                                )
                            ) {
                                Text(stringResource(R.string.botonEmpezar),
                                    fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Esta función va a llevar al usuario a la actividad principal llevando
 * el nombre del usuario a la otra actividad
 */
var goToMissionControlActivity: (Context, String) -> Unit = { context, nombre ->
    var intent = Intent(context, MissionControlActivity::class.java)
    intent.putExtra("NAME_KEY", nombre)
    context.startActivity(intent)
}
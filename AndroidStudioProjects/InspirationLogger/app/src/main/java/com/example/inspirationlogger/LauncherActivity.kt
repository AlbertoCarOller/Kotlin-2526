package com.example.inspirationlogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.inspirationlogger.ui.theme.InspirationLoggerTheme

class LauncherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Las varibles a utilizar y que necesitamos guardar
            var nombre by rememberSaveable { mutableStateOf("") }
            var edad by rememberSaveable { mutableStateOf("") }
            // Creamos una función lambda que nos va a realizar la acción al apretar el botón
            var onClickButtonPer: () -> Unit = {
                if (!nombre.isEmpty() && (edad.isDigitsOnly() && !edad.isEmpty())) {
                    // TODO: métdo que lleva a otra pantalla
                }
            }
            InspirationLoggerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                stringResource(R.string.tituloLauncher),
                                fontSize = 30.sp
                            )
                        })
                    }
                ) { innerPadding ->
                    // Columna principal
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Los campos, es decir la solicitud del nombre y edad
                        Campo(
                            nombre, { newNombre -> nombre = newNombre },
                            stringResource(R.string.mensajeIntroduceNombre),
                            stringResource(R.string.nombreLabel)
                        )
                        Spacer(Modifier.height(height = 50.dp))
                        Campo(
                            edad, { newEdad -> edad = newEdad },
                            stringResource(R.string.mensajeIntroduceEdad),
                            stringResource(R.string.edadLabel)
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(onClick = onClickButtonPer) {
                            Text(stringResource(R.string.login))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Esta lambda "widget" representa el campo a introducir, es decir el
 * nombre o la edad, representa el texto que le solicita lo que debe de
 * introducir y el 'TextField' para el campo concreto
 */
var Campo: @Composable (String, (String) -> Unit, String, String) -> Unit =
    { valor, onValueChangeText, stringResourceText, stringResourceLabel ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResourceText)
            TextField(
                value = valor,
                label = { Text(stringResourceLabel) },
                /* Con esta función le pasamos el valor introducido en el 'TextField' a la variable
                 que está fuera de la lambda, 'newValue' pasa hacia arriba */
                onValueChange = { newValue -> onValueChangeText(newValue) }
            )
        }
    }
package com.example.juegosupercontador

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juegosupercontador.ui.theme.JuegoSuperContadorTheme

class LauncherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuegoSuperContadorTheme {
                // Creamos el nombre vacío
                var nombre by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(stringResource(R.string.tituloMain), fontSize = 30.sp)
                        })
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Obtenemos el contexto de esta actividad
                        val contextLaucher = LocalContext.current
                        // Le solicitamos al usuario que introduzca su nombre
                        Text(stringResource(R.string.textField))
                        // Con TextField cambiamos el valor de la varible, del nombre
                        TextFieldPersonalizado(nombre, stringResource(R.string.label),
                            ) { newName -> nombre = newName }
                        Spacer(modifier = Modifier.height(40.dp))
                        Button(onClick = {
                            // Comprobamos que haya un nombre válido antes de pasar a la siguiente actividad
                            if (!nombre.isBlank()) {
                                goToMainActivity(contextLaucher, nombre)
                            }
                        }) {
                            Text(stringResource(R.string.botonJugar))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Esta función va a llevar el nombre a la MainActivity a la vez
 * que al propio usuario
 */
var goToMainActivity: (context: Context, nombre: String) -> Unit = { context, nombre ->
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra("NAME_LOG_KEY", nombre)
    context.startActivity(intent)
}

/**
 * Este "widget" va representa la personalización de un TextField a nuestro gusto,
 * en este caso utilizamos una función de orden superior para modificar fuera de este
 * el valor del nombre
 */
@Composable
fun TextFieldPersonalizado(value: String, label: String, onClickText: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { newName -> onClickText(newName) },
        label = { Text(label) })
}
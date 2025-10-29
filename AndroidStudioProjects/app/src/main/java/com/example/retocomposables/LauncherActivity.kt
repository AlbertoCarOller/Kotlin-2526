package com.example.retocomposables

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocomposables.ui.theme.RetoComposablesTheme

class LauncherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetoComposablesTheme {
                // Creamos el nombre vacío
                var nombre by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text("LauncherActivity", fontSize = 50.sp)
                        })
                    }) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        // Obtenemos el contexto de esta actividad
                        val contextLaucher = LocalContext.current
                        // Le solicitamos al usuario que introduzca su nombre
                        Text("Introduce tu nombre:")
                        // Con TextField cambiamos el valor de la varible, del nombre
                        TextField(nombre, onValueChange = { newText ->
                            nombre = newText
                        })
                        Spacer(modifier = Modifier.height(40.dp))
                        Button(onClick = {
                            goToMainActivity(contextLaucher, nombre)
                        }) {
                            Text("Jugar")
                        }
                    }
                }
            }
        }
    }
}

fun goToMainActivity(context: Context, nombre: String) {
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra("NAME_LOG_KEY", nombre)
    context.startActivity(intent)
}
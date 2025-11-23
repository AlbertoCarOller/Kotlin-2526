package com.example.lazycolum

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lazycolum.ui.theme.GrupoNumeros
import com.example.lazycolum.ui.theme.LazyColumTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Esta lista se reconstrirá por cada destrucción de la pantalla
            val listaNumeros by rememberSaveable { mutableStateOf(GrupoNumeros.agregarNumeros()) }
            LazyColumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(0.4f)
                                .padding(innerPadding)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Items permite crear el número seleccionado de ese componente
                            items(listaNumeros) { num -> // Acepta una key que es una lambda, un entero, en este caso el índice
                                Box(modifier = Modifier.background(color = Color.Magenta)) {
                                    Text("Hola buenas tardes $num")
                                    Spacer(Modifier.height(50.dp))
                                }
                            }
                        }
                        VerticalDivider()
                        LazyColumn(
                            modifier = Modifier
                                .weight(0.4f)
                                .padding(innerPadding)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Items permite crear el número seleccionado de ese componente
                            items(listaNumeros) { num -> // Acepta una key que es una lambda, un entero, en este caso el índice
                                Box(modifier = Modifier.background(color = Color.Yellow)) {
                                    Text("Hola buenas tardes $num")
                                    Spacer(Modifier.height(50.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
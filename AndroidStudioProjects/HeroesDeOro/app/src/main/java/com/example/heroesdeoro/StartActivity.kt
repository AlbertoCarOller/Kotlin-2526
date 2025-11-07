package com.example.heroesdeoro

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heroesdeoro.MainActivity
import com.example.heroesdeoro.R

class StartActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nombreHeroe by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current
            _root_ide_package_.com.example.heroesdeoro.ui.theme.HeroesDeOroTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            stringResource(R.string.tituloStart),
                            fontSize = 30.sp
                        )
                    })
                }, containerColor = colorResource(R.color.dorado)) { innerPadding ->
                    Box(Modifier.fillMaxSize()) {
                        Image(
                            modifier = Modifier.fillMaxSize().alpha(0.7f),
                            painter = painterResource(R.drawable.dorado),
                            contentDescription = "Dorado",
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(R.string.pedirHeroe),
                                fontWeight = FontWeight.Bold
                            )
                            TextField(
                                value = nombreHeroe,
                                onValueChange = { newName -> nombreHeroe = newName },
                                label = { Text(stringResource(R.string.labelHeroe)) })
                            Spacer(Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    if (nombreHeroe.isNotBlank()) goToMainActivity(
                                        context,
                                        nombreHeroe
                                    )
                                    // ButtonDefaults.buttonColors -> Para cambiar los colores de un botón
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.dorado),
                                    contentColor = colorResource(R.color.marron)
                                )
                            ) {
                                Text(stringResource(R.string.botonParaComenzar))
                            }
                        }
                    }
                }
            }
        }
    }
}

var goToMainActivity: (Context, String) -> Unit = { context, nombre ->
    var intent = Intent(context, MainActivity::class.java)
    intent.putExtra("HEROE_KEY", nombre)
    context.startActivity(intent)
}
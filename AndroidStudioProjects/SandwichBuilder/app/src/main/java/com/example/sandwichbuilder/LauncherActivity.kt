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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sandwichbuilder.ui.theme.SandwichBuilderTheme

class LauncherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var nombre by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current
            SandwichBuilderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                stringResource(R.string.launcherTitulo),
                                fontSize = 27.sp, fontFamily = FontFamily.Cursive
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
                            painter = painterResource(R.drawable.ingredientes_sandwich),
                            contentDescription = "Ingredientes Sándwiches"
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextField(
                                label = { Text(stringResource(R.string.labelNombre)) },
                                value = nombre,
                                onValueChange = { nombre = it })
                            Spacer(modifier = Modifier.height(height = 15.dp))
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.colorMostaza)
                                ),
                                onClick = {if (nombre.isNotBlank()) goToMainActivity(context, nombre)}
                            ) {
                                Text(stringResource(R.string.botonComenzar))
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Esta función va a llevar a la 'MainActivity' al usuario y con él su
 * nombre de usuario
 */
var goToMainActivity: (Context, String) -> Unit = { context, nombre ->
    var intent = Intent(context, BuilderActivity::class.java)
    intent.putExtra("NAME_KEY", nombre)
    context.startActivity(intent)
}
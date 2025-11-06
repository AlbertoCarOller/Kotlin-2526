package com.example.inspirationlogger

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspirationlogger.ui.theme.InspirationLoggerTheme

class EndActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InspirationLoggerTheme {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.mostrarEnergia)
                                .format(intent.getIntExtra("ENERGIA_KEY", 0)),
                            fontSize = 30.sp, color = colorResource(R.color.verde)
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            stringResource(R.string.mostrarIdeas)
                                .format(intent.getIntExtra("IDEAS_KEY", 0)),
                            fontSize = 30.sp, color = colorResource(R.color.verde)
                        )
                        var context = LocalContext.current
                        var mensaje = stringResource(R.string.mensajeEnvio)
                        Spacer(modifier = Modifier.height(35.dp))
                        Button(onClick = { compartir(context, mensaje) }) {
                            Text(stringResource(R.string.botonCompartir))
                        }
                    }
                }
            }
        }
    }
}

var compartir: (Context, String) -> Unit = { context, mensaje ->
    var intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plane"
    intent.putExtra(
        "MENSAJE_KEY", mensaje
            .format(intent.getStringExtra("NAME_KEY"))
    )
    context.startActivity(intent)
}
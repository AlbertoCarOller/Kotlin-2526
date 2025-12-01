package com.santosgo.marvelheroescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.santosgo.marvelheroescompose.ui.components.StandardTextComp
import com.santosgo.marvelheroescompose.ui.theme.MarvelHeroesComposeTheme
import com.santosgo.marvelheroescompose.ui.components.NavigatorContent
import com.santosgo.marvelheroescompose.utils.getWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Obtenemos el objeto que nos va a devolver el ancho de la Activity actual
            val widowSize = getWindowSizeClass(this)
            MarvelHeroesComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigatorContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        windowSize = widowSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListPreview() {
    MarvelHeroesComposeTheme {
        StandardTextComp("SantosGo")
    }
}
package com.santosgo.marvelheroescompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.santosgo.marvelheroescompose.ui.components.HeroListScreenCompact
import com.santosgo.marvelheroescompose.ui.components.StandardTextComp
import com.santosgo.marvelheroescompose.ui.theme.MarvelHeroesComposeTheme
import com.santosgo.marvelheroescompose.model.Datasource
import com.santosgo.marvelheroescompose.ui.components.HeroListScreenExp
import com.santosgo.marvelheroescompose.utils.getWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Obtenemos el objeto que nos va a devolver el ancho de la Activity actual
            val widowSize = getWindowSizeClass(LocalContext.current as Activity)
            MarvelHeroesComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (widowSize) {
                        // Si el tamaño es compacto, elegimos ese composable
                        WindowWidthSizeClass.Compact -> HeroListScreenCompact(
                            heroes = Datasource.getListXtimes(10),
                            modifier = Modifier.padding(innerPadding)
                        )

                        else -> HeroListScreenExp(
                            heroes = Datasource.getListXtimes(10),
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
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
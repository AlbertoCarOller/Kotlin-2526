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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.MarvelHeroesComposeTheme
import com.santosgo.marvelheroescompose.ui.components.BottomNavigationBar
import com.santosgo.marvelheroescompose.ui.screens.FavListCompactScreen
import com.santosgo.marvelheroescompose.ui.screens.FavListMedExpScreen
import com.santosgo.marvelheroescompose.ui.screens.FightScreen
import com.santosgo.marvelheroescompose.ui.screens.HeroDetailCompactScreen
import com.santosgo.marvelheroescompose.ui.screens.HeroListCompactScreen
import com.santosgo.marvelheroescompose.ui.screens.HeroListMedExpScreen
import com.santosgo.marvelheroescompose.ui.screens.ProfileCompactScreen
import com.santosgo.marvelheroescompose.utils.getWindowSizeClass
import com.santosgo.mavelheroes.data.Datasource
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MarvelHeroesComposeTheme {
                MarvelHeroesApp()
            }
        }
    }
}

@Composable
fun MarvelHeroesApp() {
    // Crear la lista de héroes y añadir duplicados para probar el desplazamiento
    val heroes = Datasource.getListXtimes(1)
    val windowSize = getWindowSizeClass(LocalContext.current as Activity)
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsState(initial = null)

    MarvelHeroesComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { /* Aquí puedes agregar lógica dinámica para la top bar */ },
            bottomBar = {
                if (
                    windowSize == WindowWidthSizeClass.Compact &&
                    currentRoute?.contains("hero_detail") == false
                )
                    if (currentRoute?.contains("fightScreen") == false) BottomNavigationBar(navController, currentRoute)
            },
            floatingActionButton = { } // Lógica del FAB si aplica
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "hero_list",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("hero_list") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            HeroListCompactScreen(
                                heroes,
                                navController,
                                Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        else -> {
                            HeroListMedExpScreen(
                                heroes,
                                navController,
                                Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
                composable("fav_list") {
                    val favList = remember { Datasource.getSomeRandHeroes(4) }
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            FavListCompactScreen(favList, Modifier.padding(horizontal = 8.dp))
                        }

                        else -> {
                            FavListMedExpScreen(favList, Modifier.padding(horizontal = 8.dp))
                        }
                    }
                }
                composable("profile") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            ProfileCompactScreen(Modifier.padding(horizontal = 8.dp))
                        }

                        else -> {
                            ProfileCompactScreen(Modifier.padding(horizontal = 8.dp))
                        }
                    }
                }
                composable("hero_detail/{hero_name}") {
                    val heroName = it.arguments?.getString("hero_name") ?: "NoName"
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            HeroDetailCompactScreen(
                                heroName,
                                navController,
                                Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        else -> {
                            HeroDetailCompactScreen(
                                heroName,
                                navController,
                                Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
                // Creamos la ruta hacia la fightScreen pasándole dos héroes (nombres)
                composable("fightScreen/{hero_name_1}/{hero_name_2}") {
                    // Obtenemos el nombre del héroe 1
                    val heroName1 = it.arguments?.getString("hero_name_1") ?: "NoName"
                    // Obtenemos el nombre del héroe 2
                    val heroName2 = it.arguments?.getString("hero_name_2") ?: "NoName"
                    // Pasamos los nombres y el navegador a la fightScreen
                    FightScreen(
                        navController = navController,
                        player1Name = heroName1,
                        player2Name = heroName2
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarvelHeroesAppPreview() {
    MarvelHeroesComposeTheme {
        MarvelHeroesApp()
    }
}
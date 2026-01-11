package com.santosgo.marvelheroescompose.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.ui.components.HeroCard
import com.santosgo.marvelheroescompose.ui.components.HeroCardLand
import com.santosgo.marvelheroescompose.ui.components.MedHeaderComp
import com.santosgo.marvelheroescompose.ui.components.StandardButtonComp
import com.santosgo.mavelheroes.data.Datasource

@Composable
fun HeroListCompactScreen(
    heroes: MutableList<Hero>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    // Guarda los héroes seleccionados
    val selectedHeroes = remember { mutableStateListOf<String>() }
    Column(modifier = modifier.fillMaxSize()) {
        when (selectedHeroes.size) {
            // Si no hay héroes seleccionados se muestra que seleccione dos héroes
            0 -> MedHeaderComp(title = stringResource(id = R.string.select_two_heroes))
            // Si hay uno seleccionado, se muestra el nombre
            1 -> MedHeaderComp(title = "${selectedHeroes[0]} VS ")
            // En caso de que sea 2 se muestran los 2 coches
            else -> {
                MedHeaderComp(title = "${selectedHeroes[0]} VS ${selectedHeroes[1]}")
                // El botón para viajar a la fightScreen
                StandardButtonComp(
                    onClick = {
                        navController.navigate("fightScreen/${selectedHeroes[0]}/${selectedHeroes[1]}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, start = 8.dp, end = 8.dp),
                    label = stringResource(R.string.fight)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(heroes, key = { hero -> hero.name }) { hero ->
                val isSelected = selectedHeroes.contains(hero.name)
                HeroCard(
                    hero,
                    isSelected,
                    onFavClick = { Log.d("FavHeroCard", "${hero.name} es mi favorito") },
                    onCardClick = {
                        // Si está selecionado y le damos click se lo quitamos de la lista
                        if (isSelected) {
                            selectedHeroes.remove(hero.name)
                            // En caso de que clickemos y no esté seleccionado se añade a la lista
                        } else {
                            selectedHeroes.add(hero.name)
                            if (selectedHeroes.size > 2) {
                                selectedHeroes.removeAt(0)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HeroListMedExpScreen(
    heroes: MutableList<Hero>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = "Pantalla media o grande")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(heroes) { hero ->
                HeroCardLand(hero) {
                    navController.navigate("hero_detail/${hero.name}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
    HeroListCompactScreen(Datasource.heroList(), navController = rememberNavController())
}
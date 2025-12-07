package com.santosgo.marvelheroescompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Datasource
import com.santosgo.marvelheroescompose.model.Hero

/**
 * Una pantalla que muestra las card de los héroes favoritos
 */
@Composable
fun HeroListFavCompact(
    //heroes: MutableList<Hero>,
    modifier: Modifier = Modifier,
    subirHeroe: (Hero) -> Unit,
    goToDeatails: (Hero) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.tituloFav))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = Datasource.heroesListFav) {
                CardHeroFav(it, subirHeroe = subirHeroe, goToDetails = goToDeatails)
            }
        }
    }
}
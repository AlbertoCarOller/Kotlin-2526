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
import com.santosgo.marvelheroescompose.model.Hero

@Composable
fun HeroListScreenExp(
    heroes: MutableList<Hero>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.titulo_app_medio_grande))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = heroes) {
                CardHeroLand(it)
            }
        }
    }
}
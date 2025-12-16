package com.santosgo.marvelheroescompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.ui.components.ConfirmDeleteDialog
import com.santosgo.marvelheroescompose.ui.components.FavHeroCard
import com.santosgo.marvelheroescompose.ui.components.FavHeroCardLand
import com.santosgo.marvelheroescompose.ui.components.MedHeaderComp


@Composable
fun FavListCompactScreen(heroes : MutableList<Hero>, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var heroNameSelected by remember { mutableStateOf("") }
    if (showDialog) {
        ConfirmDeleteDialog(
            heroName = heroNameSelected,
            onCancel = { showDialog = false },
            onConfirm = {
                heroes.removeIf { it.name == heroNameSelected }
                showDialog = false
            }
        )
    }
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.marvel_fav_list))
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(heroes) { hero ->
                FavHeroCard(hero) {
                    heroNameSelected = hero.name
                    showDialog = true
                }
            }
        }
    }
}

@Composable
fun FavListMedExpScreen(heroes : MutableList<Hero>, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var heroNameSelected by remember { mutableStateOf("") }
    if (showDialog) {
        ConfirmDeleteDialog(
            heroName = heroNameSelected,
            onCancel = { showDialog = false },
            onConfirm = {
                heroes.removeIf { it.name == heroNameSelected }
                showDialog = false
            }
        )
    }
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(heroes) { hero ->
                FavHeroCardLand(hero) {
                    heroNameSelected = hero.name
                    showDialog = true
                }
            }
        }
    }
}
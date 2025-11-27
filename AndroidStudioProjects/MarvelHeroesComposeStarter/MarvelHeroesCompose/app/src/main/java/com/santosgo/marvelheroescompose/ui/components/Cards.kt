package com.santosgo.marvelheroescompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.ui.theme.extendedColors


/**
 * Este composable es una card con información básica de los héroes
 */
@Composable
fun CardHero(hero: Hero, modifier: Modifier = Modifier) {
    // Creamos una card que ocupe todo el ancho, pero con un margin en este caso de 8.dp
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.extendedColors.customCard.color,
            contentColor = MaterialTheme.extendedColors.customCard.onColor
        )
    ) {
        /*La card tiene una row principal que contiene la imagen del héroe,
         una columna con su información y un IconButton*/
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // La imagen del héroe
            StandardImage(height = 100, width = 100, hero = hero)
            // La columna que contiene algo de información
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                // Contenido de la columna
                Text(hero.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(4.dp))
                Text("${stringResource(R.string.nombrePoder)} ${hero.power}")
                Text("${stringResource(R.string.nombreInteligencia)} ${hero.intelligence}")
            }
            // Los IconButton de la flecha y el corazón en una Colum
            Column {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = "Icono de favorito", Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Icono flecha abajo", Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

/**
 * Este composable es una card con información más completa de los héroes
 */
@Composable
fun CardHeroLand(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.extendedColors.customCard.color,
            contentColor = MaterialTheme.extendedColors.customCard.onColor
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardImage(height = 100, width = 100, hero = hero)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(hero.name, style = MaterialTheme.typography.headlineMedium)
                    Text("${stringResource(R.string.nombrePoder)} ${hero.power}")
                    Text("${stringResource(R.string.nombreInteligencia)} ${hero.intelligence}")
                }
                Text("${stringResource(R.string.descripcion)} ${hero.description}")
            }
        }
    }
}
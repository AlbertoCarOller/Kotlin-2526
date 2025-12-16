package com.santosgo.marvelheroescompose.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.LocalExtendedColorScheme
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.mavelheroes.data.Datasource

@Composable
fun HeroCard(hero: Hero, isSelected: Boolean, onFavClick : () -> Unit, onCardClick: () -> Unit) {
    val extendedColorScheme = LocalExtendedColorScheme.current
    val color =
        if(!isSelected) extendedColorScheme.customCard.color
        else extendedColorScheme.customCard.colorContainer
    val onColor =
        if (!isSelected) extendedColorScheme.customCard.onColor
        else extendedColorScheme.customCard.onColorContainer
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCardClick()
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardColors(
            containerColor = color,
            contentColor = onColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Imagen del héroe
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(hero.photo),
                height = 100,
                width = 100
            )

            // Columna con el nombre y atributos del héroe
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                StandardTextComp(
                    text = hero.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                StandardTextComp(
                    text = stringResource(R.string.power, hero.power),
                    style = MaterialTheme.typography.bodyMedium
                )
                StandardTextComp(
                    text = stringResource(R.string.intelligence, hero.intelligence),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(verticalArrangement = Arrangement.SpaceAround) {
                // Botón de acción con ícono
                IconButton(
                    onClick = {
                        onFavClick()
                    }, //incluir el héroe en favoritos
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        modifier = Modifier.size(48.dp),
                        contentDescription = stringResource(R.string.delete_desc),
                        tint = extendedColorScheme.customCard.onColorContainer
                    )
                }
                // Botón de acción con ícono
                IconButton(
                    onClick = { Log.d("FavHeroCard", "Botón \"mostrar más\" pulsado") },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        modifier = Modifier.size(48.dp),
                        contentDescription = stringResource(R.string.more_content_desc),
                        tint = extendedColorScheme.customCard.onColorContainer
                    )
                }
            }
        }
    }
}

@Composable
fun HeroCardLand(hero: Hero, onClick: () -> Unit) {
    val extendedColorScheme = LocalExtendedColorScheme.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        colors = CardColors(
            containerColor = extendedColorScheme.customCard.color,
            contentColor = extendedColorScheme.customCard.onColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Imagen del héroe
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(hero.photo),
                height = 100,
                width = 100
            )

            // Columna con el nombre y atributos del héroe
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom) {
                    StandardTextComp(
                        text = hero.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    StandardTextComp(
                        text = stringResource(R.string.power, hero.power),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    StandardTextComp(
                        text = stringResource(R.string.intelligence, hero.intelligence),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(
                        onClick = { Log.d("FavHeroCard", "Botón favorito pulsado") }, //incluir el héroe en favoritos
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            modifier = Modifier.size(48.dp),
                            contentDescription = stringResource(R.string.delete_desc),
                            tint = extendedColorScheme.customCard.colorContainer
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                StandardTextComp(
                    text = stringResource(R.string.hero_description, hero.description),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FavHeroCard(hero: Hero, onClickClear: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Imagen del héroe
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(hero.photo),
                height = 100,
                width = 100
            )

            // Columna con el nombre y atributos del héroe
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                StandardTextComp(
                    text = hero.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                StandardTextComp(
                    text = stringResource(R.string.power, hero.power),
                    style = MaterialTheme.typography.bodyMedium
                )
                StandardTextComp(
                    text = stringResource(R.string.intelligence, hero.intelligence),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(verticalArrangement = Arrangement.SpaceAround) {
                // Botón de acción con ícono
                IconButton(
                    onClick = { onClickClear() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        modifier = Modifier.size(48.dp),
                        contentDescription = stringResource(R.string.delete_desc),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                // Botón de acción con ícono
                IconButton(
                    onClick = { Log.d("FavHeroCard", "Botón \"mostrar más\" pulsado") },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        modifier = Modifier.size(48.dp),
                        contentDescription = stringResource(R.string.more_content_desc),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun FavHeroCardLand(hero: Hero, onClickClear: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Imagen del héroe
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(hero.photo),
                height = 150,
                width = 150
            )

            // Columna con el nombre y atributos del héroe
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom) {
                    StandardTextComp(
                        text = hero.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    StandardTextComp(
                        text = stringResource(R.string.power, hero.power),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    StandardTextComp(
                        text = stringResource(R.string.intelligence, hero.intelligence),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(
                        onClick = { onClickClear() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            modifier = Modifier.size(48.dp),
                            contentDescription = stringResource(R.string.delete_desc),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                StandardTextComp(
                    text = stringResource(R.string.hero_description, hero.description),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

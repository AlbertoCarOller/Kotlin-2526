package com.santosgo.marvelheroescompose.ui.screens

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.LocalExtendedColorScheme
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.model.Technique
import com.santosgo.marvelheroescompose.ui.components.MedHeaderComp
import com.santosgo.marvelheroescompose.ui.components.StandardButtonComp
import com.santosgo.marvelheroescompose.ui.components.StandardTextComp
import com.santosgo.marvelheroescompose.ui.components.TextWinner
import com.santosgo.marvelheroescompose.ui.components.badgedIcon
import com.santosgo.mavelheroes.data.Datasource
import com.santosgo.mavelheroes.data.FightType
import kotlinx.coroutines.delay

@Composable
fun FightScreen(
    navController: NavController,
    player1Name: String,
    player2Name: String,
    modifier: Modifier = Modifier
) {
    val player1 = Datasource.getHeroByName(player1Name)
    val player2 = Datasource.getHeroByName(player2Name)
    val DRAW = "Draw"

    if (player1 == null || player2 == null) {
        StandardTextComp(text = "Héroe no encontrado")
        return
    }

    // Determinamos el tipo de lucha al azar
    val fightType by remember {
        mutableStateOf<FightType?>(
            FightType.entries.toTypedArray().random()
        )
    }

    // Estados para las técnicas usadas y los valores finales
    var player1Technique by remember { mutableStateOf<Technique?>(null) }
    var player2Technique by remember { mutableStateOf<Technique?>(null) }
    var player1FinalValue by remember { mutableIntStateOf(0) }
    var player2FinalValue by remember { mutableIntStateOf(0) }
    var winner by remember { mutableStateOf<String?>(null) }

    val onThrowTechnique: (Int) -> Unit = { index ->
        when (index) {
            1 -> {
                player1Technique = player1.techniques.random()
                player1FinalValue = player1Technique?.damage ?: 0
                player1FinalValue += when (fightType) {
                    FightType.POWER -> {
                        Log.d(
                            "FightScreen",
                            "Poder(x2): ${player1.power * 2} + Inteligencia: ${player1.intelligence}"
                        )
                        player1.power * 2 + player1.intelligence
                    }

                    else -> {
                        Log.d(
                            "FightScreen",
                            "Poder: ${player1.power} + Inteligencia (x2):  ${player1.intelligence * 2}"
                        )
                        player1.intelligence * 2 + player1.power
                    }
                }
            }

            else -> {
                player2Technique = player2.techniques.random()
                player2FinalValue = player2Technique?.damage ?: 0
                player2FinalValue += when (fightType) {
                    FightType.POWER -> {
                        player2.power * 2 + player2.intelligence
                    }

                    else -> {
                        player2.intelligence * 2 + player2.power
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Título con el tipo de lucha
        when (fightType) {
            FightType.POWER -> MedHeaderComp(title = stringResource(R.string.power_battle))
            else -> MedHeaderComp(title = stringResource(R.string.intelligence_battle))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Representación del primer héroe
        HeroFightCard(
            hero = player1,
            isTop = true,
            techniqueThrown = player1Technique,
            finalValue = player1FinalValue,
            onUseTechnique = { onThrowTechnique(1) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (player1Technique != null && player2Technique != null && winner == null) {
            when {
                player1FinalValue > player2FinalValue -> winner = player1.name
                player2FinalValue > player1FinalValue -> winner = player2.name
                else -> winner = DRAW
            }
        }

        if (winner == null) {
            Image(
                painter = painterResource(R.drawable.versus),
                contentDescription = stringResource(R.string.default_content_descrip),
                alignment = Alignment.Center
            )
            // En caso de que no haya ganador se mostrará el texto de ganador y el botón para volver
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // El texto del ganador o empate
                TextWinner(winner = winner!!)
                // El botón para volver hacia atrás
                StandardButtonComp(
                    label = stringResource(R.string.back_button),
                    onClick = { navController.popBackStack() })
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Representación del segundo héroe
        HeroFightCard(
            hero = player2,
            isTop = false,
            techniqueThrown = player2Technique,
            finalValue = player2FinalValue,
            onUseTechnique = { onThrowTechnique(2) }
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun HeroFightCard(
    hero: Hero,
    isTop: Boolean,
    techniqueThrown: Technique?,
    finalValue: Int,
    onUseTechnique: () -> Unit
) {
    Column(
        verticalArrangement = if (isTop) Arrangement.Top else Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!isTop) {
            techniqueThrown?.let { technique ->
                TechniqueCard(technique = technique, value = finalValue)
            } ?: StandardButtonComp(label = "Usar Técnica") {
                onUseTechnique()
            }
        }

        // Información del héroe
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            // Imagen y nombre del héroe
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.size(200.dp)
            ) {
                Image(
                    painter = painterResource(id = Datasource.getDrawableIdByName(hero.photo)),
                    contentDescription = hero.name,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = hero.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Niveles de inteligencia y poder
                //StandardTextComp(stringResource(R.string.power, hero.power))
                //StandardTextComp(stringResource(R.string.intelligence, hero.intelligence))
                // Niveles de inteligencia y poder pero con BadgedBox para crear un espacio donde poder poner la insignia roja
//                BadgedBox(badge = {
//                    // Badge -> La insignia roja, en este caso va a contener el poder e inteligencia del personaje
//                    Badge { Text(text = hero.power.toString()) }
//                }) {
//                    Image(painterResource(R.drawable.strenght), "")
//                }
//                BadgedBox(badge = {
//                    Badge { Text(text = hero.intelligence.toString()) }
//                }) {
//                    Image(painterResource(R.drawable.intelligence), "")
//                }
                // Los niveles de fuerza e inteligencia con los componentes ya existentes
                badgedIcon(R.drawable.strenght, contentDescription = "", value = hero.power)
                badgedIcon(
                    R.drawable.intelligence,
                    contentDescription = "",
                    value = hero.intelligence
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (isTop) {
            // Mostrar técnica usada si existe
            techniqueThrown?.let { technique ->
                TechniqueCard(technique = technique, value = finalValue)
            } ?: StandardButtonComp(label = "Usar Técnica") {
                onUseTechnique()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun TechniqueCard(technique: Technique, value: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardTextComp(
                text = stringResource(R.string.technique_name, technique.name),
                style = MaterialTheme.typography.bodyMedium,
            )
            StandardTextComp(
                text = stringResource(
                    R.string.technique_desciption,
                    technique.description
                ) + " " + stringResource(R.string.damage, technique.damage),
                style = MaterialTheme.typography.bodySmall,
            )
            // Total obtenido en la lucha
            Text(
                text = stringResource(R.string.total_attack, value),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

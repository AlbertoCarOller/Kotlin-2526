package com.santosgo.dicebattleapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santosgo.dicebattleapp.R

@Composable
fun BattleScreen(modifier: Modifier = Modifier, onClickEndGame: (String, String) -> Unit) {
    // Se define las variables de estado que se van a usar
    var player1 by rememberSaveable { mutableStateOf("") }
    var player2 by rememberSaveable { mutableStateOf("") }
    var num1 by rememberSaveable { mutableIntStateOf(0) }
    var num2 by rememberSaveable { mutableIntStateOf(0) }
    var total1 by rememberSaveable { mutableIntStateOf(0) }
    var total2 by rememberSaveable { mutableIntStateOf(0) }
    // Variables que controlan si los usuarios han tirado ya los dados
    var player1HasThrowed by rememberSaveable { mutableStateOf(false) }
    var player2HasThrowed by rememberSaveable { mutableStateOf(false) }

    /**
     * Esta función se encarga de controlar el cambio de ronda,
     * si los dos jugadores han tirado los dados, se reinician las variables
     * de estado y se llama a la función onClickEndGame para finalizar el juego
     */
    val onNextRound: () -> Unit = {
        if (num1 > num2) {
            total1++
        } else if (num1 < num2) {
            total2++
        }
        // Se reinician las variables de estado de tirada para la siguiente ronda
        player1HasThrowed = false
        player2HasThrowed = false
        // Se comprueba que ya hayan pasado las 3 rondas
                if(total1 == 3) onClickEndGame(player1, player2)
                if (total2 == 3) onClickEndGame(player2, player1)
    }

    /**
     * Esta función se encarga de actualizar el nombre del jugador
     * dependiendo del índice pasado
     */
    val onPlayerNameChange: (Int, String) -> Unit = { playerIndex, newString ->
        if (playerIndex == 1) {
            player1 = newString
        } else {
            player2 = newString
        }
    }

    /**
     * Esta función se encarga de generar un número aleatorio
     * entre 1 y 6 y actualizar el valor de la variable correspondiente,
     * ya sea num1 o num2 dependiendo del índice pasado, aparte,
     * llamará a la función onNextRound para controlar el cambio de ronda
     * si los dos jugadores ya han lanzado
     */
    val onThrowDice: (Int) -> Unit = { index ->
        if (index == 1) {
            num1 = (1..6).random()
            player1HasThrowed = true
        } else {
            num2 = (1..6).random()
            player2HasThrowed = true
        }
        // Si los dos jugadores han lanzado, se llama a la función onNextRound
        if (player1HasThrowed && player2HasThrowed) {
            onNextRound()
        }
    }


    // Row con los PlayerDisplay, en este caso son 2
    Row(
        modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
    ) { // TODO: terminar y arreglar el diseño
        PlayerDisplay(
            playerIndex = 1,
            playerName = player1,
            label = "Jugador",
            onThrowDice = { index ->
                onThrowDice(index)
                // Cambiamos el estado de la varible para saber que ha lanzado
                player1HasThrowed = true
            },
            enabled = player1.isNotBlank() && !player1HasThrowed,
            onPlayerNameChange = { newInt, newString ->
                onPlayerNameChange(newInt, newString)
            },
            diceNum = num1,
            wins = total1,
            modifier = modifier
        )
        VerticalDivider()
        PlayerDisplay(
            playerIndex = 2,
            playerName = player2,
            label = "Jugador",
            onThrowDice = { index ->
                onThrowDice(index)
            },
            enabled = player2.isNotBlank() && !player2HasThrowed,
            onPlayerNameChange = { index, newString ->
                onPlayerNameChange(index, newString)
            },
            diceNum = num2,
            wins = total2,
            modifier = modifier
        )
    }

}

@Composable
fun PlayerDisplay(
    playerIndex: Int, // -> Índice del jugador (1 o 2)
    label: String, // -> Etiqueta del jugador (por ejemplo, "Jugador 1") en el label del input text
    playerName: String, // -> Nombre del jugador
    onPlayerNameChange: (Int, String) -> Unit, // -> Función de actualización del nombre del jugador
    diceNum: Int, // -> Número del dado
    onThrowDice: (Int) -> Unit, // -> Función de lanzamiento del dado
    wins: Int, // -> Número de victorias del jugador
    enabled: Boolean, // -> Habilitado o deshabilitado
    modifier: Modifier // -> Modificador que se le pasa a Colum
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Aquí se solicita el nombre del jugador
            StandardInputTextComp(
                //Ajusta el ancho del input text, como mínimo 150dp y máximo 300dp
                modifier = Modifier.widthIn(min = 150.dp, max = 300.dp),
                label = label.plus(" $playerIndex"),
                // Valor inicial del nombre del jugador
                value = playerName,
                //Actualiza el nombre del jugador basándose en el índice pasado y el nombre pasado
                onValueChange = { newString ->
                    onPlayerNameChange(
                        playerIndex,
                        newString
                    )
                }
            )
        }
        // Aquí se define la imagen, tendrá más peso que el resto de Rows
        Row(
            Modifier
                .weight(4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiceComp(num = diceNum)
        }
        // Aquí se define el botón de lanzamiento del dado
        Row(
            Modifier
                .weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardButtonComp(
                label = stringResource(R.string.throw_dice),
                enabled = enabled,
                onClick = {
                    onThrowDice(playerIndex)
                }
            )
        }
        // Aquí se define la fila de resultados
        Row(
            Modifier
                .weight(2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (diceNum != 0) {
                Column {
                    LabelAndValueComp(
                        label = stringResource(R.string.result),
                        value = diceNum.toString()
                    )
                    LabelAndValueComp(
                        label = stringResource(R.string.total),
                        value = wins.toString()
                    )
                }
            }
        }
    }
}
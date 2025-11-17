package com.santosgo.dicebattleapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.santosgo.dicebattleapp.R

@Composable
fun FinalScreen(
    modifier: Modifier = Modifier,
    winner : String = "",
    looser: String = "") {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StandardTextComp(
            text = stringResource(R.string.winner, winner, looser),
        )
    }
}
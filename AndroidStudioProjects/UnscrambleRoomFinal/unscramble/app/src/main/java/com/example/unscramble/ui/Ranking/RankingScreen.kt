package com.example.unscramble.ui.Ranking

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R
import com.example.unscramble.ui.Game.GameLayout
import com.example.unscramble.ui.Game.GameStatus
import com.example.unscramble.ui.Game.GameViewModel
import com.example.unscramble.ui.Game.SettingsDialog
import com.example.unscramble.ui.Game.UserMessage

@Composable
fun RankingScreen(
    rankingViewModel: RankingViewModel = viewModel(factory = RankingViewModel.Factory)
) {

    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val uiState by rankingViewModel.uiState.collectAsState()
    val activity = (LocalContext.current as Activity)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            return
        }

        uiState.userMessage?.let {
            if (it == UserMessage.ERROR_GETTING_GAMES) {
                Image(
                    painter = painterResource(R.drawable.no_words_found),
                    contentDescription = R.string.no_games_found.toString(),
                    modifier = Modifier.clip(shape = Shapes().extraLarge)
                )
                return
            }
        }

        Text(
            text = stringResource(R.string.ranking),
            style = typography.titleLarge,
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                activity.finish()
            }) {
            Text(text = stringResource(R.string.exit))
        }

        LazyColumn(
            Modifier.fillMaxWidth()
        ){
            items(uiState.games){ game ->
                Card(
                    Modifier.padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = game.user)
                        Text(text = game.score.toString())
                        Text(text = game.date)
                    }
                }
            }
        }
    }

}

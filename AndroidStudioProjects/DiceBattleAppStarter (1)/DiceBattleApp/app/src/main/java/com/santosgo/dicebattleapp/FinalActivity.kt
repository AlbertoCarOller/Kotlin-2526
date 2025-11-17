package com.santosgo.dicebattleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.santosgo.dicebattleapp.ui.components.FinalScreen
import com.santosgo.dicebattleapp.ui.components.StandardTextComp
import com.santosgo.dicebattleapp.ui.theme.DiceBattleAppTheme

class FinalActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val winner = intent.getStringExtra(MainActivity.WINNER_KEY)
        val looser = intent.getStringExtra(MainActivity.LOOSER_KEY)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceBattleAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.final_result)) }) }
                ) { innerPadding ->
                    FinalScreen(
                        Modifier.padding(innerPadding),
                        winner = winner.toString(),
                        looser =  looser.toString())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinalScrennPreview() {
    DiceBattleAppTheme {
        FinalScreen(winner = "Player 1", looser = "Player 2")
    }
}
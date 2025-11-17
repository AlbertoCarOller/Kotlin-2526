package com.santosgo.dicebattleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
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
import com.santosgo.dicebattleapp.ui.components.BattleScreen
import com.santosgo.dicebattleapp.ui.components.DiceComp
import com.santosgo.dicebattleapp.ui.theme.DiceBattleAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val WINNER_KEY = "winner"
        const val LOOSER_KEY = "looser"
    }

    private val goToFinalScreen: (String, String) -> Unit = { ganador, perdedor ->
        val intent = Intent(this, FinalActivity::class.java)
        intent.putExtra(WINNER_KEY, ganador)
        intent.putExtra(LOOSER_KEY, perdedor)
        startActivity(intent)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceBattleAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.app_name)) }) }
                ) { innerPadding ->
                    BattleScreen(
                        Modifier.padding(innerPadding),
                        /* Obtenemos los valores de los nombres de la BattleScreen y aquí arriba lo
                         pasamos a la función goToFinalScreen() */
                        onClickEndGame = { ganador, perdedor -> goToFinalScreen(ganador, perdedor) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BattleScreenPreview() {
    DiceBattleAppTheme {
        BattleScreen(onClickEndGame = { _, _ -> })
    }
}
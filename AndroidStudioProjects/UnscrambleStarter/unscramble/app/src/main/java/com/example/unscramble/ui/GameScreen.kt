/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.R
import com.example.unscramble.data.Language
import com.example.unscramble.data.LevelGame
import com.example.unscramble.datamodel.GameModel

/**
 * Este composable contiene toda la pantalla de juego
 * completa, contiene los composables 'GameStatus' y
 * 'GameLayout', le pasamos el viewModel por parámetros
 * para que la 'GameScreen' pueda suscribirse para oír
 * constantemente los cambios de valores y recomponerse
 */
@Composable
fun GameScreen(
    /* Pasamos la función viewModel, en caso de que no se le pase la clase ViewModel directamente,
    * la función viewModel() es una fábrica inteligente, es decir, buscará la última instancia de
    * mi viewModel, es decir mi 'GameViewModel', ya sea por giros de pantalla en la cual se pueda
    * perder por la destrucción del composable pues busca la instancia creada del viewModel, en
    * caso de que no haya una instacia del viewModel se creará una nueva y en caso de que ni siquiera
    * tengamos la clase viewModel creada en nuestro proyecto, saltará excepción porque la fábrica
    * inteligente no sabría donde mirar ni como crear, no crearía un viewModel genérico ni nada así */
    // Ahora le pasamos a viewModel() el Factory para que sepa constrirse con el Repository
    gameViewModel: GameViewModel = viewModel(factory = GameViewModel.Factory),
    navController: NavController,
) {
    // Creamos una variable boolean que va a estar en false que va a controlar si se muestra o no un Dialog
    var isSettingsDialogVisible: Boolean by remember { mutableStateOf(false) }
    // Creamos un padding
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    /* Creamos una variable que va a ser el suscriptor en Compose (la vista),
    * este lo hemos llamado 'gameUiState' utiliza el 'by' porque gracias a él
    * no hay que acceder siempre al .value del objeto, directamente da el valor,
    * llamamos al suscriptor del viewModel 'uiState' y con la función collectAsState()
    * nos suscribimos finalmente al suscriptor (uiState), la función se queda escuchando
    * los cambios que pasan por el flujo constantemente */
    val gameUiState by gameViewModel.uiState.collectAsState()

    // Comprobamos si nos da este mensaje de error para mostrar la imagen de que no hay palabras
    if (gameUiState.userMessage?.name == "ERROR_GETTING_WORDS") {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // Mosmtramos la imagen de que no hay palabras, en caso de que tengamos el error 'ERROR_GETTING_WORDS'
            Image(
                painter = painterResource(R.drawable.no_words_found),
                contentDescription = "No hay palabras"
            )
        }
        // En caso de que sí haya palabras
    } else {
        // Mientras que carga el juego, se muestra una carga
        if (gameUiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = colorScheme.secondary,
                trackColor = colorScheme.surfaceVariant,
            )
            return
        }

        // En caso de que el juego haya terminado, es decir se lleguen a las 10 palabras
        if (gameUiState.isGameOver) {
            // Se muestra el AlertDialog, pasándole la función de resetGame()
            FinalScoreDialog(
                gameUiState.score, onPlayAgain = { gameViewModel.resetGame() },
                onValueChange = { gameViewModel.updateUserName(it) },
                userName = gameViewModel.userName,
                navController = navController,
                gameViewModel = gameViewModel
            )
        }

        // Comprobamos si está activo el Dialog de configuración para mostrarlo o no
        if (isSettingsDialogVisible) {
            SettingsDialog(
                gameUiState.language, gameUiState.levelGame,
                onDismiss = { isSettingsDialogVisible = false }, onSave = { language, level ->
                    gameViewModel.setSettings(language, level)
                    isSettingsDialogVisible = false
                })
        }

        // Columna principal que contiene todos los campos de la 'GameScreen'
        Column(
            modifier = Modifier
                .statusBarsPadding()
                // Scroll vertical que recuerda su estado, por donde iba el scroll
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Metemos en un Box el IconButton para alinearlo
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                // Creamos un botón de configuración, activará el Dialog
                IconButton(onClick = { isSettingsDialogVisible = !isSettingsDialogVisible }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
            // Texto donde se muestra el nombre del juego
            Text(
                text = stringResource(R.string.app_name),
                style = typography.titleLarge,
            )
            // Este composable representa todos los elementos de la Card, inluida ella misma
            GameLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(mediumPadding),
                // Le pasamos como argumento la palabra desordenada
                gameUiState.currentScrambledWord,
                /* Definimos las funciones para enviar los datos de la palabra pasada por el usuario
                 al ViewModel y provocar un cambio, ya que es un mutableStateOf() */
                onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
                onKeyBoardDone = { gameViewModel.checkUserGuess() },
                // Le pasamos la palabra vacía, el atributo de la viewModel
                gameViewModel.userGuess,
                // Pasamos el atributo para saber si es correcto
                gameUiState.isGuessedWordWrong,
                // Aquí pasamos el número/índice de palabra por donde va
                gameUiState.currentWordCount,
                gameUiState.levelGame
            )
            // Una columna que contiene los botones de 'submit' y 'skip'
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding),
                verticalArrangement = Arrangement.spacedBy(mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botón de 'submit' para confirmar la respuesta
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    /* Esta acción comprobará que la palabra esté ordenada,
                    * llamamos a la función checkUserGuess() del viewModel
                    * para verificar lo introducido por el usuario con la
                    * palabra que debe ser */
                    onClick = { gameViewModel.checkUserGuess() }
                ) {
                    Text(
                        text = stringResource(R.string.submit),
                        fontSize = 16.sp
                    )
                }

                // El botón de 'skip' para saltar a otra palabra a ordenar
                OutlinedButton(
                    /* Aquí se cambiará a otra palabra para ordenar y el contador de palabras
                     disponibles debe aumentar, se skipea la palabra */
                    onClick = { gameViewModel.skipWord() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.skip),
                        fontSize = 16.sp
                    )
                }
            }
            // Muestra la puntuación actual del usuario, pasamos la puntuación del usuario
            GameStatus(score = gameUiState.score, modifier = Modifier.padding(20.dp))
        }
    }
}

/**
 * Este composable muestra básicamente la puntuación (score)
 * del jugador, se le pasa el score directamente por parámetros
 */
@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    // Una card
    Card(
        modifier = modifier
    ) {
        // Se muestra 'Score' y al lado la puntuación
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

/**
 * La card que representa la palabra a ordenar,
 * el subtítulo, el número de palabra sobre de 10
 * por donde va el usuario, el TextField para meter
 * la palabra ordenada, le pasamos como argumentos
 * la palabra desordenada
 */
@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    // Como argumentos la palabra desordenada
    currentScrambleWord: String,
    // Función para cambiar el valor que se cree el usuario que es la palabra
    onUserGuessChanged: (String) -> Unit,
    // Función para confirmar en el teclado la palabra colocada
    onKeyBoardDone: () -> Unit,
    // Como argumentos pasamos la variable que va a guardar la palabra correcta (el intento)
    userGuess: String,
    // Pasamos por parámetros la variable si el usuario falló o no
    isGuessWrong: Boolean,
    // La palabra por la que va
    wordCount: Int,
    // El nivel del juego
    levelGame: Int
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    // Una card
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        // La card tiene dentro una columna principal
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            // Un texto con fondo azul que mnuestra el número de palabras disponibles y cuantas llevas
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                // Le pasamos el número de palabra por donde va
                text = stringResource(R.string.word_count).format(wordCount, levelGame),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            // Texto donde aparece la palabra desordenada a adivinar (por hacer)
            Text(
                // Le pasamos al text la palabra aleatoria desordenada a ordenar
                text = currentScrambleWord,
                style = typography.displayMedium
            )
            // Un pequeño subtítulo debajo de la palabra a ordenar
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            // El TextField que va a permitir introducir la palabra ordenada
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                // Esto cambia el valor de la palabra escrita por el usuario (por hacer)
                onValueChange = onUserGuessChanged,
                // Lo que se muestra en TextField
                label = {
                    // En caso de error muestra 'Wrong Guess!'
                    if (isGuessWrong) {
                        Text(stringResource(R.string.wrong_guess))
                        // En caso de que se haya introducido bien la palabra se muestra 'Enter your word'
                    } else {
                        Text(stringResource(R.string.enter_your_word))
                    }
                },
                /* Habrá un error dependiendo de la variable que depende de si el usuario introduce
                 bien la palabra */
                isError = isGuessWrong,
                /* Esto, más especificamente 'imeAction = ImeAction.Done' cambia el intro del
                * teclado por un tick */
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                /* Esto es la accción que ocurre cuando el usuario le da al tick del teclado,
                * en este caso será para la confirmación de una palabra */
                keyboardActions = KeyboardActions(
                    // Aquí se confirmará la palabra para comprobar que esté ordenada (por hacer)
                    onDone = { onKeyBoardDone() }
                )
            )
        }
    }
}

/**
 * Este composable es un Dialog el cual se va a mostrar
 * cuando se haya llegado al final de la ronda, es decir
 * cuando el usuario adivine o gaste las 10 palabras que tiene
 * por adivinar, aquí se mostrará su 'Score'
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    userName: String,
    navController: NavController,
    gameViewModel: GameViewModel
) {
    val activity = (LocalContext.current as Activity)

    // El Dialog
    AlertDialog(
        // En caso de que el usuario pulse fuera de la ventana del dialog, no hacemos nada
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        // Se muestra un título
        title = { Text(text = stringResource(R.string.congratulations)) },
        // Se muestra el texto del mensaje
        text = {
            TextField(
                label = { Text(stringResource(R.string.nicknameLabel)) },
                onValueChange = onValueChange,
                value = userName
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    // Guardamos el registro del juego
                    gameViewModel.saveGame()
                    // Navegamos a la pantalla de ranking
                    navController.navigate("rankingScreen")
                    // finish() -> Finaliza la aplicación
                    //activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        // Se muestra el botón para volver a jugar al juego
        confirmButton = {
            // Le pasamos la función lambda 'onPlayAgain'
            TextButton(onClick = {
                // Guaramos el juego
                gameViewModel.saveGame()
                onPlayAgain()
            }) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

/**
 * Este composable es un Dialog para guardar la configuración
 * que queramos de nivel de juego y lenguaje
 */
@Composable
fun SettingsDialog(
    // El lenguaje actual
    currentLanguage: String,
    // El nivel actual
    currentLevel: Int,
    // La función para cerrar
    onDismiss: () -> Unit,
    // La función para guardar las preferencias
    onSave: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Esta variable va a contener el lenguaje seleccionado
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    // Esta variable va a contener el nivel seleccionado
    var selectedLevel by remember { mutableIntStateOf(currentLevel) }
    AlertDialog(
        // Al pulsar fuera
        onDismissRequest = onDismiss,
        // El título
        title = { Text(text = stringResource(R.string.settings)) },
        // El texto que hay dentro
        text = {
            // Una columna que va a contener las diferentes opciones de selección
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Grupo de RadioButtons para el idioma
                Text(
                    text = stringResource(R.string.select_language),
                    style = typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Language.entries.forEach { language ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            // Para ver si está o no seleccionado
                            selected = (language.language == selectedLanguage),
                            // Cuando pulsamos sobre el RadioButton
                            onClick = { selectedLanguage = language.language }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = language.name)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grupo de RadioButtons para el nivel del juego
                Text(
                    text = stringResource(R.string.select_game_level),
                    style = typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                LevelGame.entries.forEach { level ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            // Lo mismo de los lenguajes pero con el nivel
                            selected = (level.level == selectedLevel),
                            onClick = { selectedLevel = level.level }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = level.name)
                    }
                }
            }
        },
        // Al pulsar se confirma las preferencias
        confirmButton = {
            TextButton(
                // Se llama a la función onSave() con los nuevos valores
                onClick = { onSave(selectedLanguage, selectedLevel) }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        // Al pulsar el botón de cancelar, se cierra el dialog sin guardar las preferencias
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}


@Composable
fun HistorialScreen(listGame: MutableList<GameModel>) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Obtenemos el context y lo transformamos en una Activity
        val activity = (LocalContext.current as Activity)
        // Ponemos el título
        Text(
            text = stringResource(R.string.tituloRanking),
            modifier = Modifier
                .fillMaxWidth()
                // Para mantener el safeArea
                .safeDrawingPadding()
                .padding(vertical = 6.dp),
            fontSize = 18.sp
        )
        TextButton(
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            // Al pulsar cerramos la app
            onClick = { activity.finish() }) {
            Text(stringResource(R.string.exit))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            items(listGame) { juego ->
                Card(modifier = Modifier.padding(6.dp).fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        // El username del juegador en el juego
                        Text(text = juego.username)
                        // El score del juego
                        Text(text = juego.score.toString())
                        // La fecha en la que se guardó el juego
                        Text(text = juego.date)
                    }
                }

            }
        }
    }
}

// Le pasamos directamente el parametro, la lista, para no complicarlo mucho
@Composable
fun viaje() {
    // Creamos el viewModel aquí para que sea compartido por todas las pantallas
    val viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
    // Creamos el controlador de la navegación
    val navController = rememberNavController()
    // Creamos el navHost (el contenedor para vijar entre pantallas)
    NavHost(navController = navController, startDestination = "gameScreen") {
        // La pantalla de la GameScreen
        composable("gameScreen") {
            // Pasamos el navegador para navegar a la pantalla de Ranking y el viewModel compartido
            GameScreen(navController = navController, gameViewModel = viewModel)
        }
        // La pantalla de los Ranking
        composable("rankingScreen") {
            // Por cada cambio los recogemos para que cambie la pantalla cada vez que pase esto
            val uiState by viewModel.uiState.collectAsState()
            // Le pasamos la lista de juegos
            HistorialScreen(uiState.listGame)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GameScreenPreview() {
//    UnscrambleTheme {
//        GameScreen()
//    }
//}

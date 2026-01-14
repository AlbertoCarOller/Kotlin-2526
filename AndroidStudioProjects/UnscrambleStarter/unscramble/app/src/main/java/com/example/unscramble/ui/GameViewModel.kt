package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Creamos el StateFlow, el controlador de datos, aquí por lo general siemre va a ir una sola variable uiState
class GameViewModel : ViewModel() {
    /* Vamos a explicar de que se compone y que hace esta varible, para empezar es privada,
    * GameUiState() -> es la data class que contienen los datos iniciales,
    * MutableStateFlow() -> metemos la GameUiState() dentro de una caja, esa caja es MutableStateFlow(),
    * dentro de esta caja están los datos iniciales que lo que hace especial a esta caja es que cuando
    * los datos iniciales de GameUiState() cambian, avisa a la pantalla para que se redibuje */
    private val _uiState = MutableStateFlow(GameUiState())
    // Esta es la versión a la que se suscribe la UI (Compose), asStateFlow() hace que sea de solo lectura
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
}
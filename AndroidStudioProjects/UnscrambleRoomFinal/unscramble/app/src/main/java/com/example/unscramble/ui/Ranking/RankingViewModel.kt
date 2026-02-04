package com.example.unscramble.ui.Ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.unscramble.repository.GamesRepository
import com.example.unscramble.ui.Game.UserMessage
import com.example.unscramble.unscramblerelease.UnscrambleReleaseApplication
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RankingViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UnscrambleReleaseApplication)
                RankingViewModel(application.gamesRepository)
            }
        }
    }

    val uiState = gamesRepository.getAllGames
        .map { games ->
            RankingUiState(
                games = games,
                isLoading = false
            )
        }
        .catch {
            emit(
                RankingUiState(
                    userMessage = UserMessage.ERROR_GETTING_GAMES,
                    isLoading = false
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RankingUiState(isLoading = true)
        )

}
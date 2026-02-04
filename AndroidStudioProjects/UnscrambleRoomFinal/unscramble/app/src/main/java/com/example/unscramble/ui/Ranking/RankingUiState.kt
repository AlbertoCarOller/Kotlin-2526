package com.example.unscramble.ui.Ranking

import com.example.unscramble.datamodel.GameModel
import com.example.unscramble.ui.Game.UserMessage

data class RankingUiState(
    val isLoading: Boolean = true,
    val games: List<GameModel> = emptyList(),
    val userMessage: UserMessage? = null
)
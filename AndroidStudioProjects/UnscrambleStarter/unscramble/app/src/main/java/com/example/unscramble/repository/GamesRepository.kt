package com.example.unscramble.repository

import com.example.unscramble.datamodel.GameModel
import com.example.unscramble.localdatabase.GamesDao
import kotlinx.coroutines.flow.Flow

class GamesRepository(
    private val gamesDao: GamesDao
) : GamesInterface {
    override suspend fun insertGame(game: GameModel) {
        gamesDao.insertGame(game)
    }

    override suspend fun deleteGame(game: GameModel) {
        gamesDao.deleteGame(game)
    }

    override suspend fun update(game: GameModel) {
        gamesDao.update(game)
    }

    override val getAllGames: Flow<List<GameModel>> = gamesDao.getAllGames()

    override suspend fun clearGames() {
        gamesDao.clearGames()
    }

}
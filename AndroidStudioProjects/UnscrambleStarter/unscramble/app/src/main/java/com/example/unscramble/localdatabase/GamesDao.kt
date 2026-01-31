package com.example.unscramble.localdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.unscramble.datamodel.GameModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    /**
     * Esta función va a insertar un nuevo juego en la bd
     */
    @Insert
    suspend fun insertGame(game: GameModel)

    /**
     * Esta función va a eliminar un juego de la bd
     */
    @Delete
    suspend fun deleteGame(game: GameModel)

    /**
     * Esta función va a actualizar el juego con los
     * datos nuevos
     */
    @Update
    suspend fun update(game: GameModel)

    /**
     * Esta función va a devolver una lista de todos los
     * juegos ordenados por la fecha, es un (Flow) se queda
     * escuchando
     */
    @Query("select * from games order by date")
    fun getAllGames(): Flow<List<GameModel>>

    /**
     * Esta función va a eliminar todos los datos
     * de la tabla de juegos
     */
    @Query("delete from games")
    suspend fun clearGames()
}
package com.example.unscramble.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Creamos otra entidad para la base de datos
@Entity(tableName = "games")
data class GameModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Creamos el id
    @ColumnInfo(name = "date")
    val date: String, // Creamos la fecha del comienzo del juego
    @ColumnInfo(name = "score")
    val score: Int, // Creamos la puntuación del juego
    @ColumnInfo(name = "right_words")
    val rightWords: String, // Creamos las palabras correctas
    @ColumnInfo(name = "wrong_words")
    val wrongWords: String, // Creamos las palabras incorrectas
    val username: String // Creamos el username del jugador de este juego
)

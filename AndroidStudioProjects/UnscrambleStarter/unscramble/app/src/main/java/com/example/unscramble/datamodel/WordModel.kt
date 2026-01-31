package com.example.unscramble.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/* Esta data class será una entidad en la base de datos, esta es la entidad
* de las palabras del juego, las entidades se crean a partir de una data class,
* Room trabaja con SQLite, las pequeñas bases de datos que se almacenan en el dispositivo
* móvil del usuario */
@Entity(tableName = "words") // Esta etiqueta define la entidad con el nombre específico
data class WordModel(
    @PrimaryKey(autoGenerate = true) /* Esta etiqueta crea la primaryKey de la tabla,
     en este caso está activa la opción de que sea autoincremental (auto-generada) */
    val id: Long = 0, // El id de la palabra, comienzan a auto-generarse en 0
    @ColumnInfo(name = "title") // Esta etiqueta marca este campo como una columna con el nombre pasado
    val title: String, // El título (la palabra)
    @ColumnInfo(name = "language")
    val language: String // El lenguaje de la palabra
)

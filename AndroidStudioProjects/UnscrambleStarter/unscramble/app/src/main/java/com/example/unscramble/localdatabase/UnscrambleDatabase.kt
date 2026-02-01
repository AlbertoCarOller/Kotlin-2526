package com.example.unscramble.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unscramble.datamodel.GameModel
import com.example.unscramble.datamodel.WordModel

/* La etiqueta @Database es la que marca el comportamiento/reglas de la base de datos,
* 'entities' define las entidades/tablas que va a tener la base de datos, 'version' es
* la versión de la estructura de la base de datos, ahora es 1, porque va a ser la primera
* versión, pero si en el futuro cambio la estructura debo de poner 2 y así constantemente,
* por último la propiedad 'exportSchema' a false indica que no queremos guardar un archivo
* de texto con el historial de versiones, en otros casos puede ser útil tener un control
* de versiones, esta clase es la capa directa con la base de datos */
@Database(entities = [GameModel::class, WordModel::class], version = 1, exportSchema = false)
/* Esta clase es abstracta ya que hereda de RoomDatabase(), esta es la que sabe como conectar
* con SQLite */
abstract class UnscrambleDatabase : RoomDatabase() {

    /* Las funciones DAO son los puntos de acceso a las diferentes
    * funciones de escritura y lectura de la base de datos */
    abstract fun gamesDAO(): GamesDao
    abstract fun wordsDAO(): WordsDAO

    /* Utilizamos el 'companion object' para crear una variable que va a ser
    * la instacia estática de esta clase */
    companion object {
        /* @Volatile esta etiqueta hace que al un hilo crear la base de datos
        * los demás se enteren */
        @Volatile
        // Esta variable representa el singleton, la instacia única de esta clase
        private var Instance: UnscrambleDatabase? = null

        // Se crea una función que va a crear la base de datos o buscarla en caso de que no exista
        fun getDatabase(context: Context): UnscrambleDatabase {
            // Devuelve la instacia en caso de que exista y en caso de que no, la crea, se hace syncronized para que los hilos no se pisen
            return Instance ?: synchronized(this) {
                // Room.databaseBuilder() prepara los planos para construir la base de datos
                Room.databaseBuilder(
                    // Se le pasa el context para saber donde se crea, UnscrambleDatabase::class.java para que sepa las tablas que debe tener
                    context, UnscrambleDatabase::class.java,
                    // El nombre que va a tener la base de datos
                    "unscramble_database"
                    // build() construye la base de datos
                    // also{} hace algo antes de devolver la clase, la guarda en la varible de instancia
                ).build().also { Instance = it }
            }
        }
    }
}
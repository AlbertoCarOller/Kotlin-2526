package com.example.unscramble.localdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.unscramble.datamodel.WordModel
import kotlinx.coroutines.flow.Flow

/* Esta interfaz es un DAO, aquí se crean las diferentes funciones
* suspendidas (corrutinas) para comunicarnos con la base de datos
* directamente, como buena práctica siempre se crea un DAO por entidad */
@Dao
interface WordsDAO {

    /* Con @Insert le decimos a la función que inserte la palabra (data class) pasada por parámetros,
    * en caso de que haya un conflicto 'onConflict' su estategia va a ser abortar 'OnConflictStrategy.ABORT' */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    /**
     * Creamos una función (corrutina) la cual recibe una palabra para ser insertada en la bd
     */
    suspend fun insert(word: WordModel)

    /* Con la etiqueta @Delete marcamos esta función de borrado, es decir, se borra
    * la palabra pasada por parámetros */
    @Delete
    /**
     * Esta función (corrutina) va a eliminar la palabra pasada por parámetros
     * de la bd
     */
    suspend fun delete(word: WordModel)

    /* La etiqueta @Update va a actualizar la palabra con el id concreto, es decir
    * la palabra pasada por parámetros, pero con los campos a querer actualizar cambiados
    * posteriormente en la base de datos, sustituyendo el antiguo registro por el nuevo
    * (gracias al id) */
    @Update
    /**
     * Esta función va a actualizar la palabra pasada por parámetros (la identifica gracias al
     * id, debe ser por lo tanto el mismo) con los campos que cambian del antiguo registro
     * al nuevo
     */
    suspend fun update(word: WordModel)

    /* La etiqueta @Query sirve para ejecutar SQL directamente entre los
    * paréntesis de la etiqueta, esto se hace para tareas/acciones más complejas,
    * se pueden acceder a los objetos definidos en Kotlin */
    @Query("delete from words")
    /**
     * Esta función va a eliminar todas las palabras de la tabla words
     */
    suspend fun clearWords()

    /**
     * Esta función es un flujo (Flow) que se queda constantemente escuhando
     * ya crea corrutina de por sí para quedarse escuchando cambios,
     * por eso no necesita ser 'suspend', va a devolver la lista de palabras
     * de words ordenado de forma ascente por el título (z-a)
     */
    @Query("select * from words order by title asc")
    fun getAllWords(): Flow<List<WordModel>>

    /**
     * Esta función (Flow) selecciona el número de palabras que le pasamos
     * por parámetros, las palabras de forma de random
     */
    @Query("select * from words order by random() limit :number")
    fun getSomeRandomWords(number: Int): Flow<List<WordModel>>

    /**
     * Esta función (Flow) va a devolver una lista con las palabras
     * en el lengauge pasado por parámetros
     */
    @Query("select * from words where language = :language")
    fun getAllWordsByLanguage(language: String): Flow<List<WordModel>>

    /**
     * Esta función (Flow) va a devolver una lista con palabras ordenadas
     * de forma random en un lenguaje concreto, va a devolver el número
     * de palabras pasado por parámetros, si se insertan palabras nuevas,
     * room se cree que puedo tener una lista obsoleta por eso se vuelve a
     * ejecutar la consulta
     */
    @Query("select * from words where language = :language order by random() limit :number")
    fun getSomeRandomWordsByLanguage(language: String, number: Int): Flow<List<WordModel>>

    /**
     * Esta función va a devolver una lista con con palabras ordenadas
     * de forma random de un lenguaje concreto, va a devolver el
     * número de palabras pasada por parámetros, esta función es una
     * corrutina, no se queda escuchando los cambios constantes
     */
    @Query("select * from words where language = :language order by RANDOM() limit :number")
    suspend fun getOnceSomeRandomWordsByLanguage(language: String, number: Int): List<WordModel>
}
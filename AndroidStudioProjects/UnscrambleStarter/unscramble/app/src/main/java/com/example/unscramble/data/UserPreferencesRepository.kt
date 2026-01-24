package com.example.unscramble.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/* Creamos la clase Repository que es el puente entre el ViewModel y la DataStore (almacén
* de información), esta clase va a obtener los datos/información de la fuente (almacén, etc)
* sin importar la fuente que sea, ni de que forma, solo quiere la información */
class UserPreferencesRepository(
    /* Creamos este valor que hace referencia al almacenaje (DataStore), inferimos el tipo
    * <Preferences> este tipo solo almacena datos básicos, no complejos, como si lo hace 'Proto',
    * que es el otro tipo de DataStore, pero para ese hay que hacer un esquema previo que vuelve
    * más complejo el proceso */
    private val dataStore: DataStore<Preferences>
) {
    /* Tenemos que crear un companion object para que sea accesible los campos (claves)
    * sin crear una instancia de la clase, como static en JAVA */
    companion object {
        /* Creamos las claves, con stringPreferencesKey y intPreferencesKey en este caso,
        * lo que ponemos entre los '()' son los nombres que van a tener los campos (claves)
        * en el XML interno de la app en el móvil del usuario */
        val LANGUAJE = stringPreferencesKey("languaje")
        val LEVEL_GAME = intPreferencesKey("level_game")

        // El TAG para mostrar el error en el LogCat
        const val TAG = "UserPreferencesRepo"
    }

    /**
     * Esta función va a actualizar los valores en el almacenaje,
     * accedemos a los values mediante las claves (dentro del companion object),
     * después con la data class de UserPreferences pasada por parámetros le damos
     * el valor que tenga esa data class en el momento al almacén, es una función
     * 'suspend' es decir 'hilos', crea un hilo hasta que haga termine de realizar
     * su acción, esto se hace para que la app no se quede cargando, algo importante
     * es que los datos no existen en el documento hasta que tenemos los valores tanto
     * de la clave como el valor, no vale solo clave, sin valor
     */
    suspend fun savePreferences(userPrefs: UserPreferences) {
        /* Gracias a la función edit {} creamos un nuevo documento, con los datos
        * ya sea creando nuevos, eliminando datos o actualizándolos, preferences
        * es básicamente la copia editable, dentro de la función es editale, una vez
        * que terminamos se sustituye el documento antiguo por el nuevo, y ya no es
        * mutable, a menos que se vuelve a utilizar edit(), el documento es DataStore */
        dataStore.edit { preferences ->
            // Cambiamos el valor de los campos de la DataStore
            preferences[LANGUAJE] = userPrefs.languaje
            preferences[LEVEL_GAME] = userPrefs.levelGame
        }
    }

    /**
     * Esta función va a crear un flujo que siempre va a escuchar los cambios (Flow)
     * de tipo UserPreferences (la data class), esto devuelve una data class UserPreferences
     * con los nuevos datos leídos, los datos actualizados, data() devuelve un Flow, el flujo
     * que siempre se queda abierto escuchando los cambios del tipo concreto de la clase
     */
    val userPrefs: Flow<UserPreferences> = dataStore.data
        // El catch como en JAVA atrapa excepciones, pero aquí funciona a modo de filtro dentro el flujo
        .catch { e ->
            // En caso de que la excepción sea por lectura entra
            if (e is IOException) {
                // Mostramos la excepción en el LogCat
                Log.e(TAG, e.message!!)
                // Emitimos un valor vacío (null) para así no parar el flujo
                emit(emptyPreferences())
                // En caso de que sea otro tipo de excepción entra
            } else {
                // Lanzamos la excepción para saber cuál es y poder corregirla
                throw e
            }
        }.map { preferences ->
            // Guardamos el lenguaje, inglés en caso de que sea null
            val languaje = preferences[LANGUAJE] ?: "en"
            // Guardamso el nivel, fácil en caso de que sea null
            val levelGame = preferences[LEVEL_GAME] ?: LevelGame.EASY.level
            // Devolevemos la data class con los datos obtenidos
            UserPreferences(languaje, levelGame)
        }
}
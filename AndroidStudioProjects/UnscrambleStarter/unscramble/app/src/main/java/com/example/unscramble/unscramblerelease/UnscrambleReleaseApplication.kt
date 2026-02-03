package com.example.unscramble.unscramblerelease

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.unscramble.data.UserPreferences
import com.example.unscramble.data.UserPreferencesRepository
import com.example.unscramble.localdatabase.UnscrambleDatabase
import com.example.unscramble.repository.GamesRepository
import com.example.unscramble.repository.WordsRepository

/* Aquí creamos el valor que va a contener la DataStore, la clase 'Context' es el contexto
* de la app, como puede tener los composables de la app, se hace '.dataStore' para pegarle a los
* composables que tienen 'Context' (todos) la DataStore (los datos), by para acceder directamente
* al a la DataStore<Preferences> que crea el delegador (la función que sabe como crearla o buscarla)
* preferencesDataStore(), el nombre que le pasamos por parámetros es el nombre que va a tener el fichero
* que contiene la información en el móvil, la DataStore tiene que vivir globalmente en la app, por eso
* la colocamos fuera de la clase */
val Context.dataStore by preferencesDataStore(name = UserPreferences.SETTINGS_FILE)

/* Esta clase funciona como contenedor en este caso de UserPreferencesRepository, este
* es necesario para no crear muchas instancias de la misma clase (Repository) que es la que
* actúa de puente y accede a los datos, ya que tendríamos muchos flujos abiertos y con uno
* que sepa como obtener la información es suficiente, esta clase es la primera que actua cuando
* se inicia la app */
class UnscrambleReleaseApplication : Application() {
    // Creamos la instancia única del Repository como dijimos anteriormente
    lateinit var userPreferencesRepository: UserPreferencesRepository

    // Creamos las instancias de los repositorios de las palabras y juegos
    lateinit var gamesRepository: GamesRepository
    lateinit var wordsRepository: WordsRepository

    // Con el onCreate nace lo que pase dentro, en este caso queremos que sea el Repository (contenedor de dependecias)
    override fun onCreate() {
        super.onCreate()
        // Creamos la instancia del Repository pasándole el dataStore creado arriba
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        // Creamos las instancias de la base de datos, para que así puedan acceder a sus DAOs que necesitan los repositorios
        wordsRepository = WordsRepository(UnscrambleDatabase.getDatabase(this).wordsDAO())
        gamesRepository = GamesRepository(UnscrambleDatabase.getDatabase(this).gamesDAO())
    }
}
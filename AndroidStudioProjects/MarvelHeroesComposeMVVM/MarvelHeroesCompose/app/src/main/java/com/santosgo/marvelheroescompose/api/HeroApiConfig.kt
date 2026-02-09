package com.santosgo.marvelheroescompose.api

import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory


/* Esta clase es una fábrica de configuración, la cual se encarga de preparar y entregar
* una única instancia de Retrofit, que es la herramienta que se usa para que la app se pueda
* comunicar con el servidor, en este caso la API de superhéroes */
class HeroApiConfig {

    // Bloque de estáticos
    companion object {

        //mi access_token = 10159208673232717

        // Define el content type para JSON
        private val contentType = "application/json".toMediaType()

        // Configura el objeto Json con algunas opciones (por ejemplo, ignorar claves desconocidas)
        // Este es el traducto de JSON a Kotlin, transforma los JSON a clases de Kotlin (data clases)
        private val json = Json {
            ignoreUnknownKeys = true
            // Puedes configurar otras opciones según lo necesites
        }

        //Definición de la api de Retrofit2.
        // Esta función construye un cliente de Retrofit en 3 pasos
        fun provideRetrofit(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                /* addConverterFactory() -> Su función es que cuando reciba datos (JSON)
                * los pasa a clases Kotlin.
                * json.asConverterFactory(contentType) -> Esto es una fábrica que sabe
                * leer el tipo de archivo especificado, el 'contentType' */
                .addConverterFactory(json.asConverterFactory(contentType))
                // El URL donde de la API concreta
                .baseUrl(baseUrl)
                // Crea y devuelve el cliente de Retrofit
                .build()
        }
    }
}
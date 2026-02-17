package com.santosgo.marvelheroescompose.api.data_clases

import com.santosgo.marvelheroescompose.api.HeroApiService
import com.santosgo.marvelheroescompose.model.Hero

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

/* Esta clase es la que contiene la logica de negocio a las llamadas a las
* APIs, se le pasa el HeroApiService que es la que contiene el acceso a la API */
class HeroRepository(private val apiService: HeroApiService) {
    companion object {
        // El número de héroes de la API
        const val NUM_HEROES = 731
    }

    /**
     * Emite periódicamente la información completa de un héroe,
     * es un while infinito que siempre solicita a la API la información
     * de un héroe cada 10 segundos para prevenir snapshot
     *
     * @param id Identificador del héroe.
     * @param intervalMillis Intervalo en milisegundos entre cada consulta (por defecto, 10 segundos).
     */
    fun getFullHeroUpdates(id: Int, intervalMillis: Long = 10_000L): Flow<Result<FullHero>> = flow {
        // Bucle infinito
        while (true) {
            try {
                // Una variable de respuesta por parte de la API, un héroe por el id
                val response = apiService.getFullHero(id)
                // Si la respuesta ha sido 200 entra
                if (response.isSuccessful) {
                    // Guardamos el hero
                    val hero = response.body()
                    // Si es distinto de null entra
                    if (hero != null) {
                        // Emite el resultado, el héroe
                        emit(Result.success(hero))
                        // En caso de que no haya héroe entra
                    } else {
                        // Emite una excepción de que el cuerpo de respuesta es null
                        emit(Result.failure(Exception("Cuerpo de respuesta nulo")))
                    }
                    // En caso de que la respuesta no sea 200 entra
                } else {
                    // Emite una excepción
                    emit(Result.failure(Exception("Error en la consulta: ${response.code()}")))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
            // Espera el intervalo definido antes de la siguiente consulta
            delay(intervalMillis)
        }
        /* flowOn -> Transforma en este caso en un flujo de hilos de escritura que tratan muchos
         datos por ejemplo de APIs, como es el caso */
    }.flowOn(Dispatchers.IO)

    /**
     * Esta función va a devolver los Powerstats del héroe
     * con el id pasado por parámetros, no es un flujo (Flow)
     */
    suspend fun getHeroPowerstatsUpdates(id: Int): Result<Powerstats> {
        return try {
            // Realiza la llamada a la API para obtener las powerstats del héroe.
            val response = apiService.getHeroPowerstats(id)
            // Si la respuesta es 200 entra
            if (response.isSuccessful) {
                // Verifica que el cuerpo de la respuesta no sea nulo.
                val powerstats = response.body()
                if (powerstats != null) {
                    Result.success(powerstats)
                    // En caso de que el cuerpo sea null, devuelve una excepción
                } else {
                    Result.failure(Exception("El cuerpo de la respuesta es nulo."))
                }
                // En caso de que la llamada a la API sea distinta de 200 devuelve una excepción
            } else {
                Result.failure(Exception("Error en la consulta: Código ${response.code()}"))
            }
        } catch (e: Exception) {
            // Captura y devuelve cualquier excepción ocurrida durante la llamada.
            Result.failure(e)
        }
    }

    /**
     * Esta función va a devolver un héroe de forma aleatoria entre todos
     * los que hay en la API
     */
    suspend fun getRandFullHero(): Result<FullHero> {
        // Esta variable es el tiempo en mili-segundos
        val seed = System.currentTimeMillis()
        // Genera un número entre 1 y número de héroes a partir de la semilla de arriba
        val x = (1..NUM_HEROES).random(Random(seed))
        return try {
            // Esta función devuelve un héroe por su id
            val response = apiService.getFullHero(x)
            // Si la respuesta es correcta entra
            if (response.isSuccessful) {
                // Si no es null el cuerpo devuelve el héroe, en caso contrario excepción
                response.body()?.let { fullHero ->
                    Result.success(fullHero)
                } ?: Result.failure(Exception("El cuerpo de la respuesta es nulo"))
                // En caso de que fuera distinto de 200 la respuesta, excepción
            } else {
                Result.failure(Exception("Error en la consulta: Código ${response.code()}"))
            }
            // En caso de cualquier error, excepción
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtenemos un Héroe por un id pasado por parámetros
     */
    suspend fun getHero(id: Int): Result<Hero> {
        return try {
            val heroResponse: retrofit2.Response<FullHero> = apiService.getFullHero(id)
            // Si la respuesta es 200
            if (heroResponse.isSuccessful) {
                heroResponse.body()?.let { Result.success(it.toHero()) }
                    ?: Result.failure(Exception("El cuerpo de la llamada es nulo"))
                // En caso de que no haya sido una llamada exitosa, lanzamos excepción
            } else {
                Result.failure(Exception("Error de llamada: " + heroResponse.code()))
            }
            // En caso de error devuelve una respuesta de error
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Esta función va a devolver una respuesta con Héroe
     * random
     */
    suspend fun getRadomHero(): Result<Hero> {
        return try {
            // Obtenemos un fullHero random y lo transformamos a Hero
            val hero: Hero? = getRandFullHero().getOrNull()?.toHero()
            // En caso de que el Heroe sea null lanzamos excepción
            if (hero == null) {
                Result.failure(Exception("Se ha intentado devolver el héroe pero es null"))
                // En caso de que no, lo devolvemos como Result
            } else {
                Result.success(hero)
            }
            // En caso de cualquier error, lanzamos excepción
        } catch (e: Exception) {
            Result.failure(Exception("Ha ocurrido un error: " + e.message))
        }
    }

    /**
     * Esta función va a añadir héroes en una lista
     * el número de veces que se ha pasado por parámetros
     */
    suspend fun getSomeRandHeroes(num: Int): Result<List<Hero>> {
        return try {
            // Creamos una lista de los héroes aleatorios
            val heroList: MutableList<Hero> = mutableListOf()
            // Añadimos héroes de forma aleatoria hasta que la lista tenga el número de héroes pedidos
            while (heroList.size != num) {
                val hero: Hero? = getRadomHero().getOrNull()
                // Se añade cuando la respuesa es distinta de null
                if (hero != null) {
                    heroList.add(hero)
                }
            }
            Result.success(heroList.toList())
        } catch (e: Exception) {
            Result.failure(Exception("Ha ocurrido un error: " + e.message))
        }
    }

    /**
     * Esta función/flujo va a devolver cada 5 segundos una lista
     * de héroes aleatorios
     */
    fun getSomeRandHeroesUpdates(numHeroes: Int): Flow<Result<List<Hero>>> =
        // Se le indica que es un flujo por eso ya no debe indicarse que es Suspendida
        flow {
            // Bucle infinito
            while (true) {
                try {
                    // Emitimos/devolvemos la lista de héroes
                    emit(getSomeRandHeroes(numHeroes))
                } catch (e: Exception) {
                    emit(Result.failure(Exception("Ha ocurrido un error: " + e.message)))
                }
                // Hay una espera de 5 segundos
                delay(5_000L)
            }
            // Flujo de escritura/lectura
        }.flowOn(Dispatchers.IO)
}
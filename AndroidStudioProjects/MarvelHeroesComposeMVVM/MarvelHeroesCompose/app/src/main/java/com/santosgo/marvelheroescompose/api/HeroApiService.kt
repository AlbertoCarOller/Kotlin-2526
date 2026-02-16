package com.santosgo.marvelheroescompose.api

import com.santosgo.marvelheroescompose.api.data_clases.FullHero
import com.santosgo.marvelheroescompose.api.data_clases.Powerstats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/* En esta interfaz se crean las funciones que van a servir para acceder
* a los datos de la API y transformarlos a objetos Kotlin */
interface HeroApiService {
    @GET("{id}/powerstats")
    suspend fun getHeroPowerstats(@Path("id") id: Int): Response<Powerstats>

    @GET("{id}")
    suspend fun getFullHero(@Path("id") id: Int): Response<FullHero>
}
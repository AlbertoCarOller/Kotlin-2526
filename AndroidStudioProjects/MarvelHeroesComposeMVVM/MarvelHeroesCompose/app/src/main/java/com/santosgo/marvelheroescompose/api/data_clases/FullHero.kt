package com.santosgo.marvelheroescompose.api.data_clases

import com.santosgo.marvelheroescompose.model.Hero
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullHero(
    //No es necesario incluirlo @SerialName("response") val response: String,
    @SerialName("id") val id: String,
    @SerialName("name") val heroName: String,
    @SerialName("powerstats") val powerstats: Powerstats,
    @SerialName("biography") val biography: Biography,
    @SerialName("appearance") val appearance: Appearance,
    @SerialName("work") val work: Work,
    @SerialName("connections") val connections: Connections,
    @SerialName("image") val image: Image
) {
    /**
     * Esta función va a transformar la respuesta JSON a un objeto Kotlin héroe
     */
    fun toHero(): Hero {
        return Hero(
            name = heroName,
            power = if (powerstats.power == "null") 0 else powerstats.power.toInt(),
            intelligence = if (powerstats.intelligence == "null") 0 else powerstats.intelligence.toInt(),
            photo = image.url,
            description = work.occupation,
            techniques = mutableListOf()
        )
    }

}
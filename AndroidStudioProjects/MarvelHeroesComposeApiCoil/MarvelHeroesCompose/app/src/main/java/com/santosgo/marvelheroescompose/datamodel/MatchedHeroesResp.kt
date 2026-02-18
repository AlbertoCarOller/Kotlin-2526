package com.santosgo.marvelheroescompose.datamodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchedHeroesResp (
    // La lista de héroes obtenidos de la filtración por nombre
    @SerialName("results") val heroes: List<FullHero>
)

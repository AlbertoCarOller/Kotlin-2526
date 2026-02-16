package com.santosgo.marvelheroescompose.api.data_clases

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Biography(
    @SerialName("full-name") val fullName: String,
    @SerialName("alter-egos") val alterEgos: String,
    @SerialName("aliases") val aliases: List<String>,
    @SerialName("place-of-birth") val placeOfBirth: String,
    @SerialName("first-appearance") val firstAppearance: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("alignment") val alignment: String
)

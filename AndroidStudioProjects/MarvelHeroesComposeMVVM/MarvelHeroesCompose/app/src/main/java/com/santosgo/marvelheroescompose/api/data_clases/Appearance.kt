package com.santosgo.marvelheroescompose.api.data_clases

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Appearance(
    @SerialName("gender") val gender: String,
    @SerialName("race") val race: String,
    @SerialName("height") val height: List<String>,
    @SerialName("weight") val weight: List<String>,
    @SerialName("eye-color") val eyeColor: String,
    @SerialName("hair-color") val hairColor: String
)

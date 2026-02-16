package com.santosgo.marvelheroescompose.api.data_clases

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Connections(
    @SerialName("group-affiliation") val groupAffiliation: String,
    @SerialName("relatives") val relatives: String
)

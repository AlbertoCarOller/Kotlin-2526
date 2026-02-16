package com.santosgo.marvelheroescompose.api.data_clases

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Work(
    @SerialName("occupation") val occupation: String,
    @SerialName("base") val base: String
)

package com.santosgo.marvelheroescompose.api.data_clases

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("url") val url: String
)

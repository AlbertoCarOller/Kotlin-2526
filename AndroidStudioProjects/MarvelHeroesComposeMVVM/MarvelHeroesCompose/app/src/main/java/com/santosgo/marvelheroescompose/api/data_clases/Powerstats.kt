package com.santosgo.marvelheroescompose.api.data_clases

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* Las data clases de la API sirven para almacenar las respuestas JSON de la API
* como objetos de Kotlin, se le debe poner la etiqueta @Serializable */
@Serializable
data class Powerstats(
    // La etiqueta @SerialName sabe por el nombre identificar los campos del JSON
    @SerialName("intelligence") val intelligence: String,
    @SerialName("strength") val strength: String,
    @SerialName("speed") val speed: String,
    @SerialName("durability") val durability: String,
    @SerialName("power") val power: String,
    @SerialName("combat") val combat: String
)

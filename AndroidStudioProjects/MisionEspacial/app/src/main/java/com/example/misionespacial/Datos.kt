package com.example.misionespacial

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class Datos(
    /* MutableState<String> = mutableStateOf("") -> Lo utilizamos para crear un saver posteriormente
     y para que se redibuje el Composable en caso de que cambie el estado de la variable */
    var nombre: MutableState<String> = mutableStateOf(""),
    var combustible: MutableState<Int> = mutableIntStateOf(0),
    var escudos: MutableState<Int> = mutableIntStateOf(0)
)
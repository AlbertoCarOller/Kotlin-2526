package com.santosgo.marvelheroescompose.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

/**
 * Esta función devuelve un objeto WindowWidthSizeClass que devuelve el
 * ancho y alto de la activity pasada por parámetros
 */
@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun getWindowSizeClass(activity: Activity): WindowWidthSizeClass {
    /* calculateWindowSizeClass -> es la función que devuelve el objeto con la anchura y altura
    *  widthSizeClass -> solo da el ancho del objeto anteriormente hablado*/
    return calculateWindowSizeClass(activity).widthSizeClass
}
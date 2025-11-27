package com.santosgo.marvelheroescompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.ui.theme.extendedColors
import com.santosgo.marvelheroescompose.model.Datasource

@Composable
fun StandardInputTextComp(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) }
    )
}

@Composable
fun ImageComp(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    drawable: Int,
    contentDesc: String = "",
    height: Int = 0,
    width: Int = 0
) {
    val contentDescription =
        if (contentDesc == "")
            stringResource(id = R.string.default_content_descrip)
        else
            contentDesc
    if (height != 0 && width != 0) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            modifier
                .height(height.dp)
                .width(width.dp),
            contentScale = contentScale
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}

@Composable
fun StandardButtonComp(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .padding(8.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = label)
    }
}

@Composable
fun LabelAndValueComp(label: String, modifier: Modifier = Modifier, value: String = "") {
    Text(
        modifier = modifier,
        text = "$label = $value"
    )
}

@Composable
fun StandardTextComp(
    text: String,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}

/**
 * Esta función va a definir la imagen del héroe con su descripción,
 * el tamaño de la imagen y modifier si se quisiera
 */
@Composable
fun StandardImage(
    modifier: Modifier = Modifier,
    height: Int,
    width: Int,
    hero: Hero
) {
    Image(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        contentDescription = hero.photo,
        painter = painterResource(id = Datasource.getDrawableIdByName(hero.photo)),
    )
}

/**
 * Este composable representa la cabecera del título de la app
 */
@Composable
fun MedHeaderComp(title: String) {
    Surface(
        // RoundedCornerShape para redondear los corner
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.extendedColors.customHeader.color,
        contentColor = MaterialTheme.extendedColors.customHeader.onColor
    ) {
        // Metemos el texto en un Box para poder centrar el título
        Box(modifier = Modifier.fillMaxWidth()) {
            StandardTextComp(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(10.dp).align(Alignment.Center)
            )
        }
    }
}

package com.santosgo.marvelheroescompose.ui.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.compose.LocalExtendedColorScheme
import com.example.compose.extendedLight
import com.santosgo.marvelheroescompose.R

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
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}

@Composable
fun MedHeaderComp(title: String) {
    val extendedColorScheme = LocalExtendedColorScheme.current
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = extendedColorScheme.customHeader.color,
        contentColor = extendedColorScheme.customHeader.onColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun badgedIcon(
    icono: Int,
    value: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    BadgedBox(
        badge = {
            Badge {
                Text(text = value.toString(), style = MaterialTheme.typography.bodyLarge)
            }
        },
        modifier = modifier,
        content = {
            Icon(painter = painterResource(icono), contentDescription = contentDescription)
        }
    )
}

/**
 * Este composable va a mostrar el héroe ganador en caso de que hubiera,
 * en caso de que haya empate se mostrará otro texto
 */
/*@Composable
fun TextWinner(
    winner: String,
) {
    Surface(modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = colorResource(R.color.white),
        shape = MaterialTheme.shapes.large,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = if (winner == "Draw") stringResource(R.string.draw_text)
                else stringResource(R.string.fight_winner).format(winner),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}*/

/**
 * Este composable va a mostrar el héroe ganador en caso de que hubiera,
 * en caso de que haya empate se mostrará otro texto, V2
 */
@Composable
fun TextWinner(
    winner: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.large)
            .background(color = MaterialTheme.colorScheme.secondary),
    ) {
        Text(
            color = colorResource(R.color.white),
            modifier = Modifier.align(Alignment.Center),
            text = if (winner == "Draw") stringResource(R.string.draw_text)
            else stringResource(R.string.fight_winner).format(winner),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
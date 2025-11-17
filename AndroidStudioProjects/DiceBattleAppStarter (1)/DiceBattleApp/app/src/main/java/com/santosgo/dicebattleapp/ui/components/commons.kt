package com.santosgo.dicebattleapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santosgo.dicebattleapp.R

@Composable
fun StandardInputTextComp(modifier: Modifier = Modifier, label: String, value: String, onValueChange: (String) -> Unit = {}) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) }
    )
}

@Composable
fun DiceComp(modifier: Modifier = Modifier, num : Int = 0) {
    val drawable = when(num) {
        1 -> { R.drawable.dice_1 }
        2 -> { R.drawable.dice_2 }
        3 -> { R.drawable.dice_3 }
        4 -> { R.drawable.dice_4 }
        5 -> { R.drawable.dice_5 }
        6 -> { R.drawable.dice_6 }
        else -> { R.drawable.empty_dice }
    }
    ImageComp(
        drawable = drawable,
        contentDesc = stringResource(id = R.string.dice_content_descrip)
    )
}

@Composable
fun ImageComp(modifier: Modifier = Modifier, drawable : Int, contentDesc : String, height : Int = 0, width : Int = 0) {
    if(height != 0 && width != 0) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = stringResource(id = R.string.dice_content_descrip),
            modifier
                .height(height.dp)
                .width(width.dp),
            contentScale = ContentScale.Fit
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(id = drawable),
            contentDescription = stringResource(id = R.string.dice_content_descrip),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun StandardButtonComp(modifier: Modifier = Modifier, label: String, enabled: Boolean = true, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier
            .padding(8.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(text = label)
    }
}

@Composable
fun LabelAndValueComp(modifier: Modifier = Modifier, label: String, value : String = "") {
    Text(
        modifier = modifier,
        text = "$label = $value"
    )
}

@Composable
fun StandardTextComp(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text
    )
}
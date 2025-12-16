package com.santosgo.marvelheroescompose.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.santosgo.marvelheroescompose.R

@Composable
fun ConfirmDeleteDialog(
    heroName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(stringResource(R.string.delete_hero)) },
        text = { Text(stringResource(R.string.delete_hero_confirmation, heroName)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
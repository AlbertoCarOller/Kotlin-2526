package com.example.ejemplonavigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailsScreen(modifier: Modifier, item: String, goToPrincipalScreen: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(item, style = MaterialTheme.typography.headlineLarge)
        ElevatedButton(onClick = { goToPrincipalScreen() }) {
            Text("Volver")
        }
    }
}
package com.example.ejemplonavigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PrincipalScreen(modifer: Modifier, goToDetails: () -> Unit) {
    Column(
        modifier = modifer,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = { goToDetails() }) {
            Text("Ir")
        }
    }
}
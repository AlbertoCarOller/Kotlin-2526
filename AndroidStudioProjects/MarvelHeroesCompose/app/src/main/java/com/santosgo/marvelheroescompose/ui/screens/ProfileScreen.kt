package com.santosgo.marvelheroescompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.ui.components.ImageComp
import com.santosgo.marvelheroescompose.ui.components.StandardButtonComp
import com.santosgo.marvelheroescompose.ui.components.StandardInputTextComp

@Composable
fun ProfileCompactScreen(modifier: Modifier = Modifier) {
    var profileName by remember { mutableStateOf("") }
    var isLogged by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StandardInputTextComp(stringResource(R.string.profile_name),profileName, Modifier.padding(8.dp)) {
            profileName = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImageComp(drawable = R.drawable.mh_icono, modifier = Modifier.clip(CircleShape), height = 250, width = 250)
        Spacer(modifier = Modifier.height(16.dp))
        StandardButtonComp(
            if (isLogged) stringResource(R.string.logout) else stringResource(R.string.login),
            Modifier.padding(8.dp),
            onClick = { isLogged = !isLogged })
    }
}
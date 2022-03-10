package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun CommonBackButton(navController: NavController, enabled: Boolean = true, tint: Color = MaterialTheme.colors.onSecondary, modifier : Modifier = Modifier) {
    Row(modifier = modifier) {
        IconButton(
            onClick = {
                navController.navigateUp()
            }, enabled = enabled
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go back",
                tint = tint,
            )
        }
    }
}
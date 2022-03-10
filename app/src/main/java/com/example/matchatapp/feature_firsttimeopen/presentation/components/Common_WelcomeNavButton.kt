package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CommonWelcomeNavButton(
    navController: NavController,
    navigateTo: String,
    popUpTo: String,
    stringResource: Int, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = modifier
    ) {
        Button(
            onClick = {
                navController.navigate(navigateTo) {
                    popUpTo(popUpTo)
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ), shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = stringResource(id = stringResource),
                style = MaterialTheme.typography.h6.copy(letterSpacing = 2.sp),
                modifier = Modifier.padding(30.dp, 10.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}
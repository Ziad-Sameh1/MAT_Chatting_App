package com.example.matchatapp.feature_firsttimeopen.presentation.second_welcome_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.matchatapp.R

@Composable
fun SecondPagePageIndicator() {
    Image(
        painter = painterResource(id = R.drawable.ic_circle),
        contentDescription = "",
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .size(12.dp, 12.dp)
    )
    Box(
        modifier = Modifier
            .padding(top = 0.5.dp)
            .padding(horizontal = 3.dp)
            .size(30.dp, 12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
    )
    Image(
        painter = painterResource(id = R.drawable.ic_circle),
        contentDescription = "",
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .size(12.dp, 12.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.ic_circle),
        contentDescription = "",
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .size(12.dp, 12.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.ic_circle),
        contentDescription = "",
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .size(12.dp, 12.dp)
    )
}
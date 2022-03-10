package com.example.matchatapp.feature_firsttimeopen.presentation.components

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.matchatapp.R

@Composable
fun CommonImage(configuration: Configuration, modifier: Modifier = Modifier, imageDrawable: Int) {
    val topPadding = configuration.screenHeightDp.dp / 20
    Image(
        painter = painterResource(id = imageDrawable),
        contentDescription = "",
        modifier = modifier
            .padding(top = topPadding)
    )
}
package com.example.matchatapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.matchatapp.feature_firsttimeopen.util.isStatusColorPrimary
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Red500,
    primaryVariant = Red700,
    secondary = Secondary,
    onBackground = Gray,
    onSecondary = Color.White,
    onPrimary = Color.White
)

private val LightColorPalette = lightColors(
    primary = Red500,
    primaryVariant = Red700,
    secondary = Secondary,
    onBackground = Gray,
    onSecondary = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MatChatAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setStatusBarColor(
            color = darkMode
        )
    } else {
        systemUiController.setStatusBarColor(
            color = if (isStatusColorPrimary) Red500 else Color.White
        )
    }
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
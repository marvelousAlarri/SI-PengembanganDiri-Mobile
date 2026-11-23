package com.example.sipengembangandiridtedi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    error = ErrorRed,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content,
    )
}

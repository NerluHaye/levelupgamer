package com.example.levelupgamer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.Green,
    secondary = Color.Cyan,
    tertiary = Color.Red,
    background = Color.Black,    // Color para las tarjetas y otros componentes
    surfaceVariant = Color(0xFF87CEEB) // Azul cielo
)

@Composable
fun LevelUpGamerTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
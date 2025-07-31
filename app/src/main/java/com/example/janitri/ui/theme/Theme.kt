package com.example.janitri.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PregnancyColors.Purple80,
    secondary = PregnancyColors.PurpleGrey80,
    tertiary = PregnancyColors.Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = PregnancyColors.Purple40,
    secondary = PregnancyColors.PurpleGrey40,
    tertiary = PregnancyColors.Pink40,
    background = PregnancyColors.SurfaceWhite,
    surface = PregnancyColors.SurfaceWhite,
    onPrimary = PregnancyColors.TextWhite,
    onSecondary = PregnancyColors.TextWhite,
    onTertiary = PregnancyColors.TextWhite,
    onBackground = PregnancyColors.TextOnBackground,
    onSurface = PregnancyColors.TextOnSurface,
)

@Composable
fun JanitriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.example.technovation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8F85FF),
    secondary = Color(0xFFB6B0FF),
    tertiary = Color(0xFF9FA8DA),
    background = Color(0xFF1E1A29),
    surface = Color(0xFF252236),
    onBackground = Color(0xFFEAEAF4),
    onSurface = Color(0xFFEAEAF4),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF5B4BB7),
    secondary = Color(0xFF4B3AAB),
    tertiary = Color(0xFFE48A2E),
    background = Color(0xFFE4DCFA),
    surface = Color(0xFFF4E9FC),
    onBackground = Color(0xFF0F1220),
    onSurface = Color(0xFF0F1220),
)


private val AppTypography = Typography()

@Composable
fun TechnovationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

package com.vasudev.dialer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NeonGreen = Color(0xFF00FF41)
private val LightGreen = Color(0xFF66FF66)
private val DarkGreen = Color(0xFF003D0F)
private val DarkerGreen = Color(0xFF001A06)
private val RedNeon = Color(0xFFFF0040)
private val DarkRed = Color(0xFF3D0010)
private val Black = Color(0xFF000000)
private val DarkGray = Color(0xFF1A1A1A)
private val DarkerGray = Color(0xFF0D0D0D)

private val NeonColorScheme = darkColorScheme(
    primary = NeonGreen,
    onPrimary = Black,
    primaryContainer = DarkGreen,
    onPrimaryContainer = NeonGreen,
    secondary = NeonGreen,
    onSecondary = Black,
    tertiary = RedNeon,
    onTertiary = Black,
    error = RedNeon,
    errorContainer = DarkRed,
    onError = Black,
    onErrorContainer = RedNeon,
    background = Black,
    onBackground = NeonGreen,
    surface = DarkGray,
    onSurface = NeonGreen,
    surfaceVariant = DarkerGray,
    onSurfaceVariant = LightGreen,
    outline = NeonGreen,
    outlineVariant = DarkGreen
)

private val ClassicColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBBDEFB),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF424242),
    onSecondary = Color.White,
    tertiary = Color(0xFFD32F2F),
    onTertiary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black
)

@Composable
fun VasudevDialerTheme(
    isNeonTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isNeonTheme) NeonColorScheme else ClassicColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

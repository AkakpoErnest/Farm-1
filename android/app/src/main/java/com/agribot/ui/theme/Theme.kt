package com.agribot.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors: ColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = LightGreen,
    onPrimaryContainer = DarkText,
    secondary = AccentGold,
    onSecondary = DarkText,
    secondaryContainer = Cream,
    onSecondaryContainer = DarkText,
    tertiary = WarmBrown,
    onTertiary = Color.White,
    tertiaryContainer = Cream,
    onTertiaryContainer = DarkText,
    surface = SurfaceLight,
    onSurface = DarkText,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = MediumText,
    background = OffWhite,
    onBackground = DarkText,
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFFFDE8E8),
    onErrorContainer = ErrorRed
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = LightGreen,
    onPrimary = DarkText,
    primaryContainer = PrimaryGreen,
    onPrimaryContainer = Color.White,
    secondary = AccentGold,
    onSecondary = DarkText,
    secondaryContainer = WarmBrown,
    onSecondaryContainer = Color.White,
    tertiary = WarmBrown,
    onTertiary = Color.White,
    tertiaryContainer = AccentGold,
    onTertiaryContainer = DarkText,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFCCCCCC),
    background = Color(0xFF121212),
    onBackground = Color.White,
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFF3D1F1F),
    onErrorContainer = Color(0xFFFCA5A5)
)

@Composable
fun AgribotTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(colorScheme = colors, typography = Typography, content = content)
}



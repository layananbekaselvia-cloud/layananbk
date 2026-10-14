package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PastelMintPrimary,
    onPrimary = Color.White,
    primaryContainer = PastelMintContainer,
    onPrimaryContainer = PastelMintOnContainer,
    secondary = VintageBrassPrimary,
    onSecondary = Color.White,
    secondaryContainer = VintageBrassContainer,
    onSecondaryContainer = VintageBrassOnContainer,
    tertiary = VintageDenimBlue,
    onTertiary = Color.White,
    tertiaryContainer = VintageDenimContainer,
    background = ParchmentBgLight,
    onBackground = VintageTextDark,
    surface = ParchmentSurface,
    onSurface = VintageTextDark,
    surfaceVariant = ParchmentSurfaceVariant,
    onSurfaceVariant = VintageTextMuted,
    outline = Color(0xFFC4B8A5)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF1B3822),
    primaryContainer = Color(0xFF2E6B4E),
    onPrimaryContainer = Color(0xFFD3EEDF),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color(0xFF4E2600),
    secondaryContainer = Color(0xFF8D4F11),
    tertiary = Color(0xFF90CAF9),
    background = Color(0xFF1E1C1A),
    onBackground = Color(0xFFEDE8E1),
    surface = Color(0xFF292622),
    onSurface = Color(0xFFEDE8E1),
    surfaceVariant = Color(0xFF3E3A34),
    onSurfaceVariant = Color(0xFFC7C1B6),
    outline = Color(0xFF756D63)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

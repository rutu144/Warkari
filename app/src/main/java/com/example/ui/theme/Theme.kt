package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = Color.White,
    primaryContainer = SaffronContainer,
    onPrimaryContainer = MaroonDark,
    
    secondary = MaroonPrimary,
    onSecondary = Color.White,
    secondaryContainer = MaroonContainer,
    onSecondaryContainer = MaroonDark,
    
    tertiary = GoldAccent,
    onTertiary = TextPrimary,
    tertiaryContainer = IvorySurfaceVariant,
    onTertiaryContainer = TextPrimary,
    
    background = IvoryBackground,
    onBackground = TextPrimary,
    
    surface = IvorySurface,
    onSurface = TextPrimary,
    surfaceVariant = IvorySurfaceVariant,
    onSurfaceVariant = TextSecondary,
    
    outline = Color(0xFFD7CCC8),
    outlineVariant = Color(0xFFEFEBE9),
    error = UrgentRed,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = SaffronDarkTheme,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF5D2000),
    onPrimaryContainer = Color(0xFFFFDBCF),
    
    secondary = MaroonDarkTheme,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF521316),
    onSecondaryContainer = Color(0xFFFFDAD9),
    
    tertiary = GoldAccent,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF3F3200),
    onTertiaryContainer = Color(0xFFFFE176),
    
    background = DarkBackground,
    onBackground = Color(0xFFECE0DA),
    
    surface = DarkSurface,
    onSurface = Color(0xFFECE0DA),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFD0C3BC),
    
    outline = Color(0xFF53433C),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
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

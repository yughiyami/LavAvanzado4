package com.example.lavavanzado4.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

enum class AppThemeMode {
    MODERN_BLUE,
    GREEN,
    PURPLE,
    ORANGE
}

val ModernBlueLightScheme = lightColorScheme(
    primary = ModernBluePrimary,
    onPrimary = ModernBlueOnPrimary,
    primaryContainer = ModernBlueSecondary.copy(alpha = 0.2f),
    onPrimaryContainer = ModernBluePrimary,
    background = ModernNeutral,
    surface = LightSurface,
    onSurface = LightOnSurface,
    outline = LightOutline
)

val GreenLightScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,
    primaryContainer = GreenPrimaryContainer,
    onPrimaryContainer = GreenOnPrimaryContainer,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    outline = LightOutline
)

val PurpleLightScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = PurpleOnPrimary,
    primaryContainer = PurplePrimaryContainer,
    onPrimaryContainer = PurpleOnPrimaryContainer,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    outline = LightOutline
)

val OrangeLightScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = OrangeOnPrimary,
    primaryContainer = OrangePrimaryContainer,
    onPrimaryContainer = OrangeOnPrimaryContainer,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    outline = LightOutline
)

@Composable
fun LavAvanzado4Theme(
    themeMode: AppThemeMode = AppThemeMode.MODERN_BLUE,
    content: @Composable () -> Unit
) {
    val colorScheme = when(themeMode) {
        AppThemeMode.MODERN_BLUE -> ModernBlueLightScheme
        AppThemeMode.GREEN -> GreenLightScheme
        AppThemeMode.PURPLE -> PurpleLightScheme
        AppThemeMode.ORANGE -> OrangeLightScheme
    }

    val typography = if (themeMode == AppThemeMode.ORANGE) OrangeTypography else Typography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

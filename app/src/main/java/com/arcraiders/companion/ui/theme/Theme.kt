package com.arcraiders.companion.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ARC Raiders Dark Theme (Primary theme for retrofuturistic 1980s arcade aesthetic)
private val ArcRaidersDarkColorScheme = darkColorScheme(
    primary = ArcCyan,              // Bright cyan for primary actions
    onPrimary = ArcBlack,           // Dark text on primary
    primaryContainer = ArcDarkGray,  // Dark container for primary
    onPrimaryContainer = ArcCyan,    // Cyan text in containers
    
    secondary = ArcRed,              // Red/orange for secondary actions  
    onSecondary = ArcWhite,          // White text on secondary
    secondaryContainer = ArcDarkGray,
    onSecondaryContainer = ArcRed,
    
    tertiary = ArcYellow,            // Yellow for tertiary actions
    onTertiary = ArcBlack,
    tertiaryContainer = ArcDarkGray,
    onTertiaryContainer = ArcYellow,
    
    background = ArcBlack,           // Near-black background
    onBackground = ArcCream,         // Cream text on background
    
    surface = ArcDarkGray,           // Dark surface
    onSurface = ArcCream,            // Cream text on surface
    surfaceVariant = ArcMediumGray,  // Medium surface variant
    onSurfaceVariant = ArcLightGray, // Light gray text
    
    error = ArcError,                // Red error color
    onError = ArcWhite,
    errorContainer = ArcDarkGray,
    onErrorContainer = ArcError,
    
    outline = ArcLightGray,          // Outlines
    outlineVariant = ArcMediumGray,
    
    scrim = ArcBlack.copy(alpha = 0.8f)
)

// Light theme (optional - app is designed for dark mode)
private val ArcRaidersLightColorScheme = lightColorScheme(
    primary = ArcCyan,
    onPrimary = ArcWhite,
    primaryContainer = ArcCyan.copy(alpha = 0.2f),
    onPrimaryContainer = ArcBlack,
    
    secondary = ArcRed,
    onSecondary = ArcWhite,
    secondaryContainer = ArcRed.copy(alpha = 0.2f),
    onSecondaryContainer = ArcBlack,
    
    tertiary = ArcYellow,
    onTertiary = ArcBlack,
    tertiaryContainer = ArcYellow.copy(alpha = 0.2f),
    onTertiaryContainer = ArcBlack,
    
    background = ArcWhite,
    onBackground = ArcBlack,
    
    surface = ArcWhite,
    onSurface = ArcBlack,
    surfaceVariant = ArcLightGray.copy(alpha = 0.3f),
    onSurfaceVariant = ArcBlack,
    
    error = ArcError,
    onError = ArcWhite,
    errorContainer = ArcError.copy(alpha = 0.2f),
    onErrorContainer = ArcBlack
)

@Composable
fun ARCRaidersCompanionTheme(
    darkTheme: Boolean = true, // Default to dark theme for 1980s aesthetic
    dynamicColor: Boolean = false, // Disable dynamic color to preserve brand identity
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> ArcRaidersDarkColorScheme
        else -> ArcRaidersLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Use dark status bar to match retrofuturistic aesthetic
            window.statusBarColor = ArcBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

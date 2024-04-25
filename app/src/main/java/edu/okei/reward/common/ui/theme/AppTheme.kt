package edu.okei.reward.common.ui.theme

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import edu.okei.reward.common.model.TypeDevise


private val DarkColorPalette = darkColors(
    primary = Gray200,
    background = Black900
)

private val LightColorPalette = lightColors(
    primary = Blue,
    background = Gray200,
)


@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val activity = LocalContext.current as? Activity
    SideEffect {
        activity?.window?.let { window->
            WindowCompat
                .getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isDark
        }
    }
    val colorsTheme = if (isDark) DarkColorPalette else LightColorPalette
    val typeDevise  = identifyDevice()
    val dimensions = remember(typeDevise) {
        val coefficient = when(typeDevise){
            TypeDevise.Phone -> 1f
            TypeDevise.Table -> 1.5f
            TypeDevise.TV -> 2f
            else -> 2.5f
        }
        Dimensions(coefficient)
    }
    val shapes = remember(typeDevise) {
        when(typeDevise){
            TypeDevise.Phone -> ShapesPhone
            TypeDevise.Table -> ShapesTable
            else -> ShapesTV
        }
    }
    CompositionLocalProvider(LocalDimensions provides dimensions) {
        MaterialTheme(
            colors = colorsTheme,
            shapes = shapes,
            typography = Typography,
            content = content,
        )
    }
}
package edu.okei.reward.common.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Gray200,
    primaryVariant = Black900,
    background = Black900,
    onBackground = Black900,
    surface =  Gray200
)

//@SuppressLint("ConflictingOnColor")
//private val LightColorPalette = lightColors(
//    primary = Blue700,
//    primaryVariant = Blue500,
//    secondary = Green500,
//    background = Gray200,
//    onBackground = Blue700,
//    surface = Color.White
//)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

}
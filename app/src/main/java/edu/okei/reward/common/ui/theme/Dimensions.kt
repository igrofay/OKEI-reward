package edu.okei.reward.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val coefficient: Float,
    val grid_1: Dp = 2.dp*coefficient,
    val grid_1_5: Dp = 4.dp*coefficient,
    val grid_2: Dp = 6.dp*coefficient,
    val grid_2_5: Dp= 8.dp*coefficient,
    val grid_3: Dp= 10.dp*coefficient,
    val grid_3_5: Dp = 12.dp*coefficient,
    val grid_4: Dp= 14.dp*coefficient,
    val grid_4_5: Dp = 16.dp*coefficient,
    val grid_5: Dp = 18.dp*coefficient,
    val grid_5_5: Dp = 20.dp*coefficient,
//    val font_1_5: TextUnit = 10.sp*coefficient,
//    val font_2: TextUnit = 12.sp*coefficient,
//    val font_2_5: TextUnit = 14.sp*coefficient,
//    val font_3: TextUnit = 16.sp*coefficient,
//    val font_3_5: TextUnit =18.sp*coefficient,
//    val font_4: TextUnit = 20.sp*coefficient,
//    val font_4_5: TextUnit = 22.sp*coefficient,
//    val font_5: TextUnit = 24.sp*coefficient,
//    val font_6_5: TextUnit = 30.sp*coefficient,
    val borderSmall: Dp = 1.dp*coefficient,
)
val MaterialTheme.dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current

val LocalDimensions = compositionLocalOf { Dimensions(1f) }
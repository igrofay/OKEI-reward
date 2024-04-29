package edu.okei.reward.common.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.okei.reward.R

private val gilroy = FontFamily(
    Font(R.font.gilroy_heavy, FontWeight.W900),
    Font(R.font.gilroy_extra_bold, FontWeight.W800),
    Font(R.font.gilroy_bold, FontWeight.W700),
    Font(R.font.gilroy_semi_bold, FontWeight.W600),
    Font(R.font.gilroy_medium, FontWeight.W500),
    Font(R.font.gilroy_regular, FontWeight.W400),
    Font(R.font.gilroy_light, FontWeight.W300),
    Font(R.font.gilroy_ultra_light, FontWeight.W200),
    Font(R.font.gilroy_thin, FontWeight.W100),
)
val Typography = Typography(
    defaultFontFamily = gilroy,
    // EditText
    body2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
        fontFamily = gilroy,
    ),
    // Text Button
    button = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
        fontFamily = gilroy
    ),
    // AppTopBar
    h6 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        fontFamily = gilroy,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W800,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontFamily = gilroy,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        fontFamily = gilroy
    ),
)
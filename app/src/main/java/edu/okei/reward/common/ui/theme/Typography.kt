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
val TypographyPhone = Typography(
    defaultFontFamily = gilroy,
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    // EditText
    body1 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
        fontFamily = gilroy,
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 14.sp,
        lineHeight = 14.sp,
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
    overline = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        fontFamily = gilroy

    )
)
val TypographyTable = Typography(
    defaultFontFamily = gilroy,
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 30.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.225.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 21.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.15.sp
    ),
    // EditText
    body1 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 27.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.375.sp,
        fontFamily = gilroy,
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 21.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.25.sp,
        fontFamily = gilroy,
    ),
    // Text Button
    button = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 27.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.375.sp,
        fontFamily = gilroy
    ),
    // AppTopBar
    h6 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 30.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp,
        fontFamily = gilroy,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W800,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        fontFamily = gilroy,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.6.sp,
        fontFamily = gilroy
    ),
    overline = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        fontFamily = gilroy
    )
)
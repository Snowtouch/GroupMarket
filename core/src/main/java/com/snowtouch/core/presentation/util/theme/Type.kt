package com.snowtouch.core.presentation.util.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.snowtouch.core.R

// Set of Material typography styles to start with
val Cabin = FontFamily(
    Font(
        resId = R.font.cabin_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.cabin_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.cabin_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.cabin_bold,
        weight = FontWeight.Bold
    )
)

val Literata = FontFamily(
    Font(
        resId = R.font.literata_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.literata_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.literata_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.literata_bold,
        weight = FontWeight.Bold
    )
)

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Cabin,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
    ),
)
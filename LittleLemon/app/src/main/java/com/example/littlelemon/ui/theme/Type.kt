package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R


val MarkaziText = FontFamily(
    Font(R.font.markazitext_regular, FontWeight.Normal),
)

val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    displayLarge = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize = 54.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


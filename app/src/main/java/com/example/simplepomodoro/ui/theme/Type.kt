package com.example.simplepomodoro.ui.theme
import com.example.simplepomodoro.R

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val customFontFamily = FontFamily(
    Font(R.font.righteous_regular) // Replace with your actual font file name
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 80.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.5.sp
    ),
    // Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.5.sp
    ),
    displaySmall = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.5.sp
    )
    //
)
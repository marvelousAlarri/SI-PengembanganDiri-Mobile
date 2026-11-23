package com.example.sipengembangandiridtedi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Sementara memakai font bawaan supaya langsung bisa dijalankan.
// Cara mengganti ke Plus Jakarta Sans ada di bagian "Font (opsional)" di bawah.
val AppFontFamily: FontFamily = FontFamily.Default

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = TextPrimary,
    ),
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
)

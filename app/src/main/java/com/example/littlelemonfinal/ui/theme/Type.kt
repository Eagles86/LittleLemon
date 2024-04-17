package com.example.littlelemonfinal.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h1 = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray
    ),
    h2 = TextStyle(
        color = Color.DarkGray,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    body1 = TextStyle(
        color = Color.Green
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Bold,
        color = Color.Green
    ),
    button = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
)

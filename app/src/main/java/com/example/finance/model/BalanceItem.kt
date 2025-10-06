package com.example.finance.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class BalanceItem(
    val title: String,
    val amount: Double,
    val color: Color,
    val icon: ImageVector
)
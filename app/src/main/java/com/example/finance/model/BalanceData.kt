package com.example.finance.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color

object PersonalTheme {
    val primaryColor = Color(0xFF00796B)
    val accentColor = Color(0xFF4DB6AC)
}

object BalanceData {
    val totalIngresos: Double = 00.0
    val totalGastos: Double = 00.0

    val balanceTotal: Double = totalIngresos - totalGastos

    val balanceItems: List<BalanceItem> = listOf(
        BalanceItem(
            title = "Ingresos",
            amount = totalIngresos,
            color = Color(0xFF4CAF50),
            icon = Icons.Filled.Check
        ),
        BalanceItem(
            title = "Gastos",
            amount = totalGastos,
            color = Color(0xFFF44336),
            icon = Icons.Filled.Close
        ),
        BalanceItem(
            title = "Balance Total",
            amount = balanceTotal,
            color = PersonalTheme.primaryColor,
            icon = Icons.Filled.Favorite
        )
    )
}
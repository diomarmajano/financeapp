package com.example.finance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("transacciones")
data class Transacciones(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "monto")
    val monto: Double,

    @ColumnInfo(name = "movimiento")
    val movimiento: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "fecha")
    val fecha: String
)
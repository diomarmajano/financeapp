package com.example.finance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finance.model.Transacciones
@Dao
interface TransaccionDao {

    @Query("SELECT * FROM transacciones")
    suspend fun getAll(): List<Transacciones>

    @Query("SELECT SUM(monto) FROM transacciones WHERE movimiento = 'ingresos'")
    suspend fun buscarIngresos():Double?
    @Query("SELECT SUM(monto) FROM transacciones WHERE movimiento = 'egresos'")
    suspend fun buscarEgresos():Double?

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertar(transacciones: Transacciones)

}
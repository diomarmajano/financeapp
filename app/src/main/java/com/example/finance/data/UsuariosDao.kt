package com.example.finance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finance.model.Usuarios

@Dao
interface UsuariosDao {

    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<Usuarios>
    @Query("SELECT * FROM usuarios WHERE name = :name AND password = :password LIMIT 1")
    suspend fun buscarPorCredenciales(name: String, password: String): Usuarios?

    @Query("SELECT * FROM usuarios WHERE name = :name LIMIT 1")
    suspend fun buscarPorNombre(name: String): Usuarios? // Útil para registrar

    @Query("UPDATE usuarios SET password = :newPassword WHERE name = :name")
    suspend fun actualizarPassword(name: String, newPassword: String): Int
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertar(usuario: Usuarios)
}
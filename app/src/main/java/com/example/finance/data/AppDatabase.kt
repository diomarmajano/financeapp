package com.example.finance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finance.model.Usuarios
import com.example.finance.model.Transacciones
@Database(entities = [Usuarios::class, Transacciones::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuariosDao(): UsuariosDao
    abstract fun TransaccionDao(): TransaccionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
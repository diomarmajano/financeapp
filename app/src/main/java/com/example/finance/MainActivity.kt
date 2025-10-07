package com.example.finance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.finance.ui.theme.FinanceTheme
import com.example.finance.resources.views.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.finance.data.AppDatabase
import com.example.finance.model.Usuarios
import com.example.finance.resources.views.InicioApp
import com.example.finance.resources.views.MiUbicacion
import com.example.finance.resources.views.RecoverScreen
import com.example.finance.resources.views.RegisterScreen
import com.example.finance.resources.views.WelcomeScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Construcción de la base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "usuarios_db"
        ).build()
        val usuariosDao = db.usuariosDao()

        setContent {
            FinanceTheme {
                val navController = rememberNavController()

                // Inicialización de usuarios predeterminados
                LaunchedEffect(Unit) {
                    // Ejecutamos en hilo IO
                    withContext(Dispatchers.IO) {
                        val usuariosExistentes = usuariosDao.getAll()
                        if (usuariosExistentes.isEmpty()) {
                            val usuariosPredeterminados = listOf(
                                Usuarios(name = "diomar", password = "diomar123"),
                                Usuarios(name = "luz", password = "luz123"),
                                Usuarios(name = "gregory", password = "gregory123"),
                                Usuarios(name = "maria", password = "maria123"),
                                Usuarios(name = "edgar", password = "edgar123")
                            )
                            usuariosPredeterminados.forEach { usuariosDao.insertar(it) }
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("buscar_dispositivo") { MiUbicacion(context = this@MainActivity, navController = navController) }
                        composable("login") { HomeScreen(navController, usuariosDao) }
                        composable("register") { RegisterScreen(navController, usuariosDao) }
                        composable("recover") { RecoverScreen(navController, usuariosDao) }
                        composable("welcome") { WelcomeScreen(navController) }
                        composable("Inicio") { InicioApp(navController) }
                    }
                }
            }
        }
    }
}




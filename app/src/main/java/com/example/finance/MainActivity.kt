package com.example.finance

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.finance.ui.theme.FinanceTheme
import com.example.finance.resources.views.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finance.resources.views.RecoverScreen
import com.example.finance.resources.views.RegisterScreen
import com.example.finance.resources.views.WelcomeScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceTheme {
                val navController = rememberNavController()
                val usuariosPredeterminados = remember {
                    mutableStateListOf(
                        "diomar" to "diomar123",
                        "luz" to "luz123",
                        "gregory" to "gregory123",
                        "maria" to "maria123",
                        "edgar" to "edgar123",
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") { HomeScreen(navController, usuariosPredeterminados) }
                        composable("register") { RegisterScreen(navController, usuariosPredeterminados)}
                        composable("recover") { RecoverScreen(navController, usuariosPredeterminados)}
                        composable("welcome") { WelcomeScreen(navController) }
                    }
                }
            }
        }
    }
}






package com.example.finance.resources.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finance.ui.theme.PersonalTheme
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.finance.model.BalanceItem
import com.example.finance.model.TransaccionesModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioApp(
    navController: NavHostController,
    usuarioNombre: String = "Usuario",
    viewModel: TransaccionesModel = viewModel()) {

    val transacciones by viewModel.transacciones.collectAsState()


    val totalIngresos by viewModel.totalIngresos.collectAsState()
    val totalGastos by viewModel.totalGastos.collectAsState()
    val balanceTotal by viewModel.balanceTotal.collectAsState()


    val balanceItems = listOf(
        BalanceItem("Ingresos", totalIngresos, Color(0xFF4CAF50), Icons.Filled.Check),
        BalanceItem("Gastos", totalGastos, Color(0xFFF44336), Icons.Filled.Close),
        BalanceItem("Balance Total", balanceTotal, PersonalTheme.primaryColor, Icons.Filled.Favorite)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mis finanzas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = {navController.navigate("login")}) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "inicio"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PersonalTheme.primaryColor,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("Transaccion") },
                containerColor = PersonalTheme.primaryColor,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Hola $usuarioNombre, bienvenido a tu dashboard",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            balanceItems.forEach { item ->
                BalanceCard(item = item)
            }

            Text(
                text = "Transacciones Recientes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transacciones) { t ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (t.movimiento == "ingresos") Color(0xFFDFF7DF) else Color(0xFFFDE0E0)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(t.descripcion, fontWeight = FontWeight.Medium)
                                Text(t.fecha, fontSize = 12.sp, color = Color.Gray)
                            }
                            Text(
                                "$${"%.2f".format(t.monto)}",
                                color = if (t.movimiento == "ingresos") Color(0xFF4CAF50) else Color(0xFFF44336),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                { navController.navigate("login")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
                    .semantics { contentDescription = "Boton para cerrar sesión" },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PersonalTheme.primaryColor,
                    contentColor = PersonalTheme.TextPrimary
                )
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppScaffold() {
    MaterialTheme {
        val navController = rememberNavController()
        InicioApp(navController = navController)
    }
}
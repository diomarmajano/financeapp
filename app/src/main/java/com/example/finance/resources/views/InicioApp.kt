package com.example.finance.resources.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.finance.model.BalanceData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioApp(navController: NavHostController) {

    val balanceItems = BalanceData.balanceItems
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
                    IconButton(onClick = {{}}) {
                        Icon(
                            imageVector = Icons.Filled.Home,
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
                onClick = { },
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
            balanceItems.forEach { item ->
                BalanceCard(item = item)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Transacciones Recientes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aquí irá la lista de transacciones")
                }
            }
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
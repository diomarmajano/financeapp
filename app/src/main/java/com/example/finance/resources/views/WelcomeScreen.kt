package com.example.finance.resources.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finance.ui.theme.PersonalTheme

@Composable
fun WelcomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PersonalTheme.backgroundPrimary)
            .padding(24.dp)
            .semantics { contentDescription = "Pantalla de bienvenida" },
        verticalArrangement = Arrangement.Center
    )
    {
        //Mensaje principal de la pantalla de inicio
        Text(
            text = "¡Bienvenido a tu aplicacion de finanzas, próximamente tendremos novedades para tí!",
            color = PersonalTheme.primaryColor,
            fontFamily = PersonalTheme.TypeText,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

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


package com.example.finance.resources.views

import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finance.data.UsuariosDao
import com.example.finance.ui.theme.PersonalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@Composable
fun HomeScreen(
    navController: NavHostController,
    usuarioDao: UsuariosDao,
    modifier: Modifier = Modifier
) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // usamos este

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PersonalTheme.backgroundPrimary)
            .padding(24.dp)
            .semantics { contentDescription = "Pantalla principal" },
        verticalArrangement = Arrangement.Center
    ) {
        //Mensaje principal de la pantalla de inicio
        Text(
            text = "Bienvenido, Ingresa tus credenciales",
            color = PersonalTheme.primaryColor,
            fontFamily = PersonalTheme.TypeText,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Input usuario
        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Usuario") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar tu usuario" },
            shape = RoundedCornerShape(16.dp),
        )

        // Input password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar tu contraseña" },
            shape = RoundedCornerShape(16.dp),
        )

        // Botón de login
        Button(
            onClick = {
                coroutineScope.launch {
                    val usuario = withContext(Dispatchers.IO) {
                        usuarioDao.buscarPorCredenciales(user, password)
                    }
                    if (usuario != null) {
                        Toast.makeText(context, "Bienvenido ${usuario.name}", Toast.LENGTH_SHORT).show()
                        navController.currentBackStackEntry?.savedStateHandle?.set("usuarioNombre", usuario.name)
                        navController.navigate("Inicio")
                    } else {
                        Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("Iniciar sesión")
        }
        Button(
            onClick = {
                navController.navigate("buscar_dispositivo")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                    containerColor = PersonalTheme.primaryColor,
                    contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("¿Quieres saber donde estas?")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "¿Aun no te has registrado?",
                color = PersonalTheme.primaryColor,
                fontFamily = PersonalTheme.TypeText,
                fontSize = 12.sp,
                modifier = Modifier
                    .clickable { navController.navigate("register") }
                    .semantics { contentDescription = "Link para registrarse" },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = PersonalTheme.primaryColor,
                fontFamily = PersonalTheme.TypeText,
                fontSize = 12.sp,
                modifier = Modifier
                    .clickable { navController.navigate("recover") }
                    .semantics { contentDescription = "Link para recuperar contraseña" },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RecoverScreen(
    navController: NavHostController,
    usuarioDao: UsuariosDao,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PersonalTheme.backgroundPrimary)
            .padding(24.dp)
            .semantics { contentDescription = "Pantalla de recuperación de contraseña" },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Recuperar contraseña",
            color = PersonalTheme.primaryColor,
            fontFamily = PersonalTheme.TypeText,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar tu usuario" },
            shape = RoundedCornerShape(16.dp),
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Nueva contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar tu nueva contraseña" },
            shape = RoundedCornerShape(16.dp),
        )

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repite la contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para repetir tu nueva contraseña" },
            shape = RoundedCornerShape(16.dp),
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    val usuario = withContext(Dispatchers.IO) {
                        usuarioDao.buscarPorNombre(username)
                    }

                    if (usuario != null) {
                        if (newPassword == repeatPassword) {
                            withContext(Dispatchers.IO) {
                                usuarioDao.actualizarPassword(username, newPassword)
                            }
                            Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .semantics { contentDescription = "Botón para recuperar contraseña" },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("Recuperar")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ir al inicio",
                color = PersonalTheme.primaryColor,
                fontFamily = PersonalTheme.TypeText,
                fontSize = 12.sp,
                modifier = Modifier.clickable { navController.navigate("login") },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}


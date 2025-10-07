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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.finance.data.UsuariosDao
import com.example.finance.model.Usuarios
import com.example.finance.model.UsuariosModel
import com.example.finance.ui.theme.PersonalTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@Composable
fun RegisterScreen(
    navController: NavHostController,
    usuarioDao: UsuariosDao,
    modifier: Modifier = Modifier,
    viewModel: UsuariosModel = viewModel(),
) {
    var newUser by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PersonalTheme.backgroundPrimary)
            .padding(24.dp)
            .semantics { contentDescription = "Pantalla de registro" },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registro de usuario",
            color = PersonalTheme.primaryColor,
            fontFamily = PersonalTheme.TypeText,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo nombre de usuario
        OutlinedTextField(
            value = newUser,
            onValueChange = { newUser = it },
            label = { Text("Nombre de usuario") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para crear tu nombre de usuario" },
            shape = RoundedCornerShape(16.dp),
        )

        // Campo contraseña
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Crear contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para crear tu contraseña" },
            shape = RoundedCornerShape(16.dp),
        )

        // Campo repetir contraseña
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

        // Botón de registro
        Button(
            onClick = {
                coroutineScope.launch {
                    val usuarioExistente = withContext(Dispatchers.IO) {
                        usuarioDao.buscarPorNombre(newUser)
                    }
                    if (usuarioExistente == null) {
                        if (newPassword == repeatPassword) {
                            withContext(Dispatchers.IO) {
                                usuarioDao.insertar(Usuarios(name = newUser, password = newPassword))
                            }
                            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Usuario ya registrado", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .semantics { contentDescription = "Boton para registrarte" },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("Registro")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "¿Ya estás registrado?",
                color = PersonalTheme.primaryColor,
                fontFamily = PersonalTheme.TypeText,
                fontSize = 12.sp,
                modifier = Modifier.clickable { navController.navigate("login") },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

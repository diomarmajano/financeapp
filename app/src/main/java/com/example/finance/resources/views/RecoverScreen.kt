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
import com.example.finance.ui.theme.PersonalTheme

@Composable
fun RecoverScreen(navController: NavHostController, usuariosPredeterminados: SnapshotStateList<Pair<String, String>>, modifier: Modifier = Modifier) {
    var NewUser by remember { mutableStateOf("") }
    var NewPassword by remember { mutableStateOf("") }
    var RepeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PersonalTheme.backgroundPrimary)
            .padding(24.dp)
            .semantics { contentDescription = "Pantalla de recuperacin de contareseña" },
        verticalArrangement = Arrangement.Center
    )
    {
        //Mensaje principal de la pantalla de ´recuperacion
        Text(
            text = "Recuperar contraseña",
            color = PersonalTheme.primaryColor,
            fontFamily = PersonalTheme.TypeText,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            value = NewUser,
            onValueChange = { NewUser = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
        )

        //inpun datos password
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            value = NewPassword,
            onValueChange = { NewPassword = it },
            label = { Text("Nueva contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
        )
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            value = RepeatPassword,
            onValueChange = { RepeatPassword = it },
            label = { Text("Repite la contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
        )

        val context = LocalContext.current
        //Boton de login
        Button(
            {
                val usuario = usuariosPredeterminados.indexOfFirst { it.first == NewUser }
                if(usuario != -1){
                    usuariosPredeterminados[usuario] = NewUser to NewPassword
                    Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "!Usuario no encontrado, intenta de nuevo¡", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("Recuperar")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Link login
            Text(
                text = "Ir al iniicio",
                color = PersonalTheme.primaryColor,
                fontFamily = PersonalTheme.TypeText,
                fontSize = 12.sp,
                modifier = Modifier.clickable { navController.navigate("login")},
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}


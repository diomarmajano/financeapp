package com.example.finance.resources.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.finance.data.TransaccionDao
import com.example.finance.model.Transacciones
import com.example.finance.model.TransaccionesModel
import com.example.finance.ui.theme.PersonalTheme
import kotlinx.coroutines.launch

@Composable
fun TransaccionesView(
    navController: NavHostController,
    transaccionDao: TransaccionDao,
    modifier: Modifier = Modifier,
    viewModel: TransaccionesModel= viewModel()
    ) {

    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var movimiento by remember {mutableStateOf("")}
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val transacciones by viewModel.transacciones.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PersonalTheme.backgroundPrimary)
            .padding(24.dp)
            .semantics { contentDescription = "Pantalla para agregar nueva transacción" },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Regitrar nuevo movimiento",
            color = PersonalTheme.primaryColor,
            fontFamily = PersonalTheme.TypeText,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        //Campo para agregar registro de movimiento
        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar monto del movimiento" },
            shape = RoundedCornerShape(16.dp),
        )

        //Campo para agregar tipo movimiento
        OutlinedTextField(
            value = movimiento,
            onValueChange = { movimiento = it },
            label = { Text("Tipo de movimiento") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar tipo de movimiento" },
            shape = RoundedCornerShape(16.dp),
        )

        // Campo para agregar la descripcion del movimiento
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Agregar detalles") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar detalles del movimiento" },
            shape = RoundedCornerShape(16.dp),
        )

        // Campo agregar fecha
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PersonalTheme.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .semantics { contentDescription = "Campo para agregar la fecha del movimiento" },
            shape = RoundedCornerShape(16.dp),
        )

        // Boton para guardar transacción
        Button(
            onClick = {
                viewModel.registrarTransaccion(
                    monto = monto.toDouble(),
                    movimiento = movimiento,
                    descripcion = descripcion,
                    fecha = fecha,
                    onSuccess = {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        // Limpiar campos si quieres
                        monto = ""
                        movimiento = ""
                        descripcion = ""
                        fecha = ""
                    },
                    onError = { msg ->
                        Toast.makeText(context, "Error: $msg", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .semantics { contentDescription = "Boton para guardar movimiento" },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("Registrar")
        }
        Button(
            onClick = {
                navController.navigate("inicio")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .semantics { contentDescription = "Boton para ir al inicio" },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            )
        ) {
            Text("Ir al inicio")
        }


    }

}
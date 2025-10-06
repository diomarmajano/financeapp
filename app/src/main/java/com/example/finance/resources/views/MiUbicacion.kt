package com.example.finance.resources.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.finance.ui.theme.PersonalTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MiUbicacion(context: Context,   navController: NavController) {
    // Permiso de ubicación
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    var locationText by remember { mutableStateOf("Ver donde me encuentro 🙈") }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = locationText,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                if (locationPermissionState.status.isGranted) {
                    obtenerUbicacionActual(fusedLocationClient) { latLng ->
                        locationText = "Latitud: ${latLng.latitude}, Longitud: ${latLng.longitude}"
                    }
                } else {
                    locationPermissionState.launchPermissionRequest()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Ver mi ubicacion 📍")
        }
        Button(
            onClick = {
                navController.navigate("login") {
                    // Esto limpia la pila de navegación para que al volver atrás no regrese a esta pantalla
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PersonalTheme.primaryColor,
                contentColor = PersonalTheme.TextPrimary
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("volver al inicio")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!locationPermissionState.status.isGranted) {
            Text(
                text = "Permiso de ubicación requerido",
            )
        }
    }
}

@SuppressLint("MissingPermission")
fun obtenerUbicacionActual(
    fusedLocationClient: FusedLocationProviderClient,
    onLocation: (LatLng) -> Unit
) {
    // Obtener ubicación actual
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        if (location != null) {
            onLocation(LatLng(location.latitude, location.longitude))
        } else {

            onLocation(LatLng(0.0, 0.0))
        }
    }.addOnFailureListener {
        it.printStackTrace()
    }
}
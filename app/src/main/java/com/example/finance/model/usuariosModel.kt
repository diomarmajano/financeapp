package com.example.finance.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.finance.data.AppDatabase
import com.example.finance.data.UsuariosRepositoryImpl
import com.example.finance.model.Usuarios
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsuariosModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuariosDao()
    private val repository = UsuariosRepositoryImpl(usuarioDao)

    private val _usuarios = MutableStateFlow<List<Usuarios>>(emptyList())
    val usuarios = _usuarios.asStateFlow()

    fun registrarUsuario(
        name: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val existente = usuarioDao.buscarPorNombre(name)
                if (existente != null) {
                    onError("Este usuario ya está registrado")
                    return@launch
                }

                repository.registrarUsuario(Usuarios(name = name, password = password))
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al registrar usuario")
            }
        }
    }

    fun buscarUsuario(name: String, password: String, onResult: (Usuarios?) -> Unit) {
        viewModelScope.launch {
            val usuario = repository.buscarUsuario(name, password)
            onResult(usuario)
        }
    }

    fun cargarUsuarios() {
        viewModelScope.launch {

        }
    }
}

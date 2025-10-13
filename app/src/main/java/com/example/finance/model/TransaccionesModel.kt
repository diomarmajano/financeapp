package com.example.finance.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finance.data.AppDatabase
import com.example.finance.data.TransaccionDao
import com.example.finance.data.TransaccionesRepositoryImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TransaccionesModel(application: Application) : AndroidViewModel(application) {
    private val TransaccionesDao = AppDatabase.getDatabase(application).TransaccionDao()
    private val repository = TransaccionesRepositoryImpl(TransaccionesDao)

    private val _transacciones = MutableStateFlow<List<Transacciones>>(emptyList())
    val transacciones = _transacciones.asStateFlow()

    private val _totalIngresos = MutableStateFlow(0.0)
    val totalIngresos = _totalIngresos.asStateFlow()

    private val _totalGastos = MutableStateFlow(0.0)
    val totalGastos = _totalGastos.asStateFlow()

    fun registrarTransaccion(
        monto: Double,
        movimiento: String,
        descripcion: String,
        fecha: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch{
            repository.registrarTransaccion(Transacciones(monto = monto, movimiento= movimiento, descripcion= descripcion, fecha=fecha))
            cargarDatos()
            onSuccess()
        }
    }

    val balanceTotal = combine(totalIngresos, totalGastos) { ingresos, gastos ->
        ingresos - gastos
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _transacciones.value = repository.getAll()!!
            _totalIngresos.value = repository.buscarIngresos() ?: 0.0
            _totalGastos.value = repository.buscarEgresos() ?: 0.0
        }
    }
}

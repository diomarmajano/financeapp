package com.example.finance.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TransaccionesViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransaccionesModel::class.java)) {
            return TransaccionesModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

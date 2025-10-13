package com.example.finance.data

import com.example.finance.model.Transacciones

interface TransaccionesRepository {
    suspend fun buscarIngresos(): Double?
    suspend fun buscarEgresos(): Double?

    suspend fun registrarTransaccion(transacciones: Transacciones)
}
class TransaccionesRepositoryImpl(private val TransaccionDao: TransaccionDao) : TransaccionesRepository{

    override suspend fun buscarIngresos(): Double? {
        return TransaccionDao.buscarIngresos()
    }

    override suspend fun buscarEgresos(): Double?{
        return TransaccionDao.buscarEgresos()
    }
    suspend fun getAll(): List<Transacciones>?{
        return TransaccionDao.getAll()
    }
    override suspend fun registrarTransaccion(transacciones: Transacciones) {
        TransaccionDao.insertar(transacciones)
    }
}
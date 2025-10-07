package com.example.finance.data

import com.example.finance.model.Usuarios

interface UsuariosRepository {
    suspend fun buscarUsuario(name: String, password: String): Usuarios?
    suspend fun registrarUsuario(usuario: Usuarios)
}

class UsuariosRepositoryImpl(private val usuariosDao: UsuariosDao) : UsuariosRepository {
    override suspend fun buscarUsuario(name: String, password: String): Usuarios? {
        return usuariosDao.buscarPorCredenciales(name, password)
    }

    override suspend fun registrarUsuario(usuario: Usuarios) {
        usuariosDao.insertar(usuario)
    }
}
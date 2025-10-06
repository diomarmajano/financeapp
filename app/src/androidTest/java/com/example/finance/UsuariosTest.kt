package com.example.finance

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finance.data.AppDatabase
import com.example.finance.data.UsuariosDao
import com.example.finance.model.Usuarios
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsuariosDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var usuariosDao: UsuariosDao

    //este codigo se ejecura antes de cada prueba
    //y lo que hace es crear la bas ed e datos en memoria, y nos ayuda a no tocra la base de
    //datos real
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        usuariosDao = database.usuariosDao()
    }

    //y el after se va a ejecutar seimpre despues de cada pueba
    // y lo que hace este codigo es cerrar la conexion a la base de datos
    @After
    fun teardown() {
        database.close()
    }

    //El primer test vamos a insertar un nuevo usuario y luego
    //intentar buscarlo para probar dos de las funciones de nuestro UsuariosDao
    @Test
    fun insertarYBuscar() = runBlocking {
        val usuario = Usuarios(name = "usuario_prueba", password = "usuario123")
        usuariosDao.insertar(usuario)

        val buscar = usuariosDao.buscarPorCredenciales("usuario_prueba", "usuario123")
        assertNotNull(buscar)
        assertEquals("usuario_prueba", buscar?.name)
        assertEquals("usuario123", buscar?.password)
    }

    //Con este test queremos probar nuestra funcion para actualizar contraseña
    @Test
    fun actualizarPassword() = runBlocking {
        val usuario = Usuarios(name = "usuario_actualizado", password = "usuario123")
        usuariosDao.insertar(usuario)

        val usuarioActualizado = usuariosDao.actualizarPassword("usuario_actualizado", "usuario123456")
        assertEquals(1, usuarioActualizado)

        val actualizado = usuariosDao.buscarPorCredenciales("usuario_actualizado", "usuario123456")
        assertNotNull(actualizado)
        assertEquals("usuario123456", actualizado?.password)
    }

    @Test
    fun buscarUsuario() = runBlocking {
        val usuario = Usuarios(name = "usuario_prueba", password = "pass")
        usuariosDao.insertar(usuario)

        val encontrado = usuariosDao.buscarPorNombre("usuario_prueba")
        assertNotNull(encontrado)
        assertEquals("usuario_prueba", encontrado?.name)
    }

    @Test
    fun verUsuarios() = runBlocking {
        val usuario_1 = Usuarios(name = "usuario_1", password = "pass1")
        val usuario_2 = Usuarios(name = "usuario_2", password = "pass2")
        usuariosDao.insertar(usuario_1)
        usuariosDao.insertar(usuario_2)

        val todos = usuariosDao.getAll()
        assertEquals(2, todos.size)
    }
}

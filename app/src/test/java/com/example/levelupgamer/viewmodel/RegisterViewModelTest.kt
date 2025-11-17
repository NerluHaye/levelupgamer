package com.example.levelupgamer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.levelupgamer.data.remote.model.RegistroUsuarioDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RegisterViewModel
    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = RegisterViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `register success - sets success state`() = runTest {
        // Given
        val nombre = "Test User"
        val email = "test@example.com"
        val password = "password"
        val fechaNacimiento = "2000-01-01"
        val codigoReferido = "1234"
        val registroDTO = RegistroUsuarioDTO(nombre, email, password, fechaNacimiento, codigoReferido)
        val user = UsuarioDTO(id = 1, nombre = nombre, email = email, token = "someToken")
        whenever(repository.register(registroDTO)).thenReturn(user)

        // When
        viewModel.register(nombre, email, password, fechaNacimiento, codigoReferido)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val registerState = viewModel.registerState.value
        assertTrue(registerState is Result.Success)
        assertEquals(user, (registerState as Result.Success).data)
    }

    @Test
    fun `register failure - repository throws exception - sets error state`() = runTest {
        // Given
        val nombre = "Test User"
        val email = "test@example.com"
        val password = "password"
        val fechaNacimiento = "2000-01-01"
        val codigoReferido = null
        val errorMessage = "Error en el registro. Inténtalo de nuevo."
        whenever(repository.register(any())).thenThrow(RuntimeException())

        // When
        viewModel.register(nombre, email, password, fechaNacimiento, codigoReferido)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val registerState = viewModel.registerState.value
        assertTrue(registerState is Result.Error)
        assertEquals(errorMessage, (registerState as Result.Error).message)
    }

    @Test
    fun `register with blank name - sets error state`() = runTest {
        // When
        viewModel.register("", "email@test.com", "password", "2000-01-01", null)
        
        // Then
        val registerState = viewModel.registerState.value
        assertTrue(registerState is Result.Error)
        assertEquals("Todos los campos obligatorios deben ser rellenados.", (registerState as Result.Error).message)
        verify(repository, never()).register(any())
    }
    
    @Test
    fun `register with blank email - sets error state`() = runTest {
        // When
        viewModel.register("name", "", "password", "2000-01-01", null)
        
        // Then
        val registerState = viewModel.registerState.value
        assertTrue(registerState is Result.Error)
        assertEquals("Todos los campos obligatorios deben ser rellenados.", (registerState as Result.Error).message)
        verify(repository, never()).register(any())
    }

    @Test
    fun `resetRegisterState - sets register state to null`() {
        // When
        viewModel.resetRegisterState()

        // Then
        assertNull(viewModel.registerState.value)
    }
}
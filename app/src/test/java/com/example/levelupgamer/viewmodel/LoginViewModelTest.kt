package com.example.levelupgamer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.levelupgamer.data.remote.model.LoginDTO
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success - sets user and success state`() = runTest {

        val email = "test@example.com"
        val password = "password"
        val loginDTO = LoginDTO(email, password)
        val user = UsuarioDTO(id = 1, nombre = "Test User", email = email, tieneDescuentoDuoc = false, puntosLevelUp = 0, rol = "USER")
        whenever(repository.login(loginDTO)).thenReturn(user)

        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(user, viewModel.user.value)
        val loginState = viewModel.loginState.value
        assertTrue(loginState is Result.Success)
        assertEquals(user, (loginState as Result.Success).data)
    }

    @Test
    fun `login failure - sets error state`() = runTest {

        val email = "wrong@example.com"
        val password = "wrongpassword"
        val loginDTO = LoginDTO(email, password)
        val errorMessage = "Correo o contraseña incorrectos"
        whenever(repository.login(loginDTO)).thenThrow(RuntimeException())

        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.user.value)
        val loginState = viewModel.loginState.value
        assertTrue(loginState is Result.Error)
        assertEquals(errorMessage, (loginState as Result.Error).message)
    }

    @Test
    fun `logout - clears user and login state`() = runTest {

        val email = "test@example.com"
        val password = "password"
        val loginDTO = LoginDTO(email, password)
        val user = UsuarioDTO(id = 1, nombre = "Test User", email = email, tieneDescuentoDuoc = false, puntosLevelUp = 0, rol = "USER")
        whenever(repository.login(loginDTO)).thenReturn(user)
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.logout()

        assertNull(viewModel.user.value)
        assertNull(viewModel.loginState.value)
    }
    
    @Test
    fun `resetLoginState - sets login state to null`() = runTest {

        val loginState = Result.Error("An error")

        viewModel.resetLoginState()

        assertNull(viewModel.loginState.value)
    }
}

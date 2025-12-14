package com.example.levelupgamer

import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.viewmodel.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ViewModelAuthTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var mockAuthRepository: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockAuthRepository = mock()
        loginViewModel = LoginViewModel(mockAuthRepository)
        registerViewModel = RegisterViewModel(mockAuthRepository)
    }

    @Test
    fun `login success updates state to success`() = runTest {
        val fakeUser = UsuarioDTO(1, "Test User", "test@example.com", false, 0, "USER")
        // Corregido: Usar any() para el DTO
        whenever(mockAuthRepository.login(any())).thenReturn(fakeUser)

        loginViewModel.login("test@example.com", "password")
        
        testDispatcher.scheduler.advanceUntilIdle()

        val loginState = loginViewModel.loginState.first()
        assertTrue(loginState is Result.Success)
        assertEquals(fakeUser, (loginState as Result.Success).data)
    }

    @Test
    fun `register success updates state to success`() = runTest {
        val fakeUser = UsuarioDTO(1, "New User", "new@example.com", false, 0, "USER")
        // Corregido: Usar any() para el DTO
        whenever(mockAuthRepository.register(any())).thenReturn(fakeUser)

        registerViewModel.register("New User", "new@example.com", "password", "2000-01-01", null)

        testDispatcher.scheduler.advanceUntilIdle()

        val registerState = registerViewModel.registerState.first()
        assertTrue(registerState is Result.Success)
        assertEquals(fakeUser, (registerState as Result.Success).data)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
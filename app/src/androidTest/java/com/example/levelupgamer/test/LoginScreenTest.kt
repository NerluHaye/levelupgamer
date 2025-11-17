package com.example.levelupgamer.test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.data.repository.AuthRepository
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import com.example.levelupgamer.ui.LoginScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme


class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // Mockear el Repositorio de Autenticación, que es la dependencia del ViewModel
    private val mockAuthRepository = mockk<AuthRepository>(relaxed = true)

    // Crear el ViewModel real con el repositorio mockeado
    private val loginViewModel = LoginViewModel(mockAuthRepository)

    @Test
    fun loginScreen_elementsAreDisplayed_andLoginAttemptWorks() {
        val testEmail = "a@duoc.cl"
        val testPassword = "Pass1234"

        val dummyUser = UsuarioDTO(
            id = 1,
            nombre = "Test User",
            email = testEmail,
            tieneDescuentoDuoc = true,
            puntosLevelUp = 5,
            rol = "admin"
        )

        coEvery {
            mockAuthRepository.login(
                LoginDTO(testEmail, testPassword)
            )
        } returns dummyUser

        // 1. Configurar la pantalla
        composeTestRule.setContent {
            LevelUpGamerTheme {
                LoginScreen(
                    onLoginSuccess = {},
                    onRegisterClick = {},
                    loginViewModel = loginViewModel // Pasamos el VM real con el Repositorio mockeado
                )
            }
        }

        // --- 2. Verificar que los elementos están visibles ---

        // Verificar los campos de texto
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Verificar el botón y el enlace
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed().assertIsEnabled()
        composeTestRule.onNodeWithText("Regístrate aquí").assertIsDisplayed()

        // --- 3. Simular la interacción ---

        // Simular la entrada de datos
        composeTestRule.onNodeWithText("Correo Electrónico").performTextInput(testEmail)
        composeTestRule.onNodeWithText("Contraseña").performTextInput(testPassword)

        // Simular el clic en el botón de login
        composeTestRule.onNodeWithText("Iniciar Sesión").performClick()


        coVerify(exactly = 1) {
            mockAuthRepository.login(
                LoginDTO(testEmail, testPassword)
            )
        }

    }
}


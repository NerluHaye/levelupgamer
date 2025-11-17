package com.example.levelupgamer.test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.levelupgamer.ui.RegisterScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.RegisterViewModel // El ViewModel real
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest


class RegisterScreenTest {
    // 1. Regla principal para pruebas de Compose
    @get:Rule
    val composeTestRule = createComposeRule()

    // 2. Mockeamos el ViewModel para aislar la prueba.
    // 'relaxed = true' permite que las funciones sin definir no lancen errores.
    private val mockViewModel = mockk<RegisterViewModel>(relaxed = true)

    @Test
    fun registerScreen_elementsAreDisplayed_andInteractionWorks() {
        // DATOS DE PRUEBA
        val testNombre = "Ale Bello"
        val testEmail = "al@duoc.cl"
        val testPassword = "Pass1232"
        val testFechaNacimiento = "1999-01-01"

        // 3. Configurar la pantalla: Renderiza el composable
        composeTestRule.setContent {
            // Reemplaza con el nombre de tu tema
            LevelUpGamerTheme {
                RegisterScreen(
                    onRegisterSuccess = {},
                    onLoginClick = {},
                    registerViewModel = mockViewModel
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Logo LevelUpGamer")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Nombre completo")
            .assertIsDisplayed()
            .performTextInput(testNombre)

        composeTestRule
            .onNodeWithText("Email")
            .assertIsDisplayed()
            .performTextInput(testEmail)

        composeTestRule
            .onNodeWithText("Contraseña")
            .assertIsDisplayed()
            .performTextInput(testPassword)

        composeTestRule
            .onNodeWithText("Fecha de Nacimiento (YYYY-MM-DD)")
            .assertIsDisplayed()
            .performTextInput(testFechaNacimiento)


        composeTestRule
            .onNodeWithText("Registrarse")
            .assertIsDisplayed()
            .assertIsEnabled() // Debe estar habilitado para hacer clic

        composeTestRule
            .onNodeWithText("Inicia sesión")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Registrarse")
            .performClick()

        composeTestRule.mainClock.advanceUntilIdle()

        verify(exactly = 1) {
            mockViewModel.register(
                nombre = testNombre,
                email = testEmail,
                password = testPassword,
                fechaNacimiento = testFechaNacimiento,
                codigoReferido = null
            )
        }
    }
}

private fun MainTestClock.advanceUntilIdle() {
    TODO("Not yet implemented")
}




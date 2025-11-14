package com.example.levelupgamer.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.ui.RegisterScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun registerScreen_elementsAreDisplayed_andInputWorks() {
        composeTestRule.setContent {
            LevelUpGamerTheme {
                RegisterScreen(
                    onBack = {},
                    onRegisterSuccess = {},
                    onLoginClick = {}
                )
            }
        }

        // --- ASERCIONES DE VISIBILIDAD ---
        composeTestRule.onNodeWithText("Nombre de usuario").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()

        composeTestRule.onNodeWithText("Inicia sesión").assertIsDisplayed()


        // --- ASERCIONES DE INTERACCIÓN ---
        composeTestRule.onNodeWithText("Nombre de usuario")
            .performTextInput("testUser")

        composeTestRule.onNodeWithText("Email")
            .performTextInput("test@example.com")

        composeTestRule.onNodeWithText("Contraseña")
            .performTextInput("testPassword123")
    }
}
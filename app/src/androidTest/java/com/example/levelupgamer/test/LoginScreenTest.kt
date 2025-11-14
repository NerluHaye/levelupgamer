package com.example.levelupgamer.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.ui.LoginScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loginScreen_elementsAreDisplayed() {
        composeTestRule.setContent {
            LevelUpGamerTheme {
                LoginScreen(
                    onLoginSuccess = {},
                    onRegisterClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsDisplayed()
    }
}
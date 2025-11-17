package com.example.levelupgamer

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigateToCart() {
        // 1. Verifica que estamos en la pantalla principal
        composeTestRule.onNodeWithText("Level-Up Gamer").assertExists()

        // 2. Simula un clic en el botón del carrito (usando su contentDescription)
        composeTestRule.onNodeWithContentDescription("Carrito").performClick()

        // 3. Verifica que hemos navegado a la pantalla del carrito
        composeTestRule.onNodeWithText("El carrito está vacío").assertExists()
    }
}
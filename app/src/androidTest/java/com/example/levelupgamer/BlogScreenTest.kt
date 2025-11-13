package com.example.levelupgamer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.ui.BlogScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BlogScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun blogScreen_allElementsAreDisplayed() {
        composeTestRule.setContent {
            LevelUpGamerTheme {
                BlogScreen(
                    onBack = {}
                )
            }
        }

        // --- ASERCIONES ---

        // Verifica el botón
        composeTestRule.onNodeWithText("Volver", ignoreCase = true).assertIsDisplayed()

        // Verifica el título
        composeTestRule.onNodeWithText("Blog", ignoreCase = true).assertIsDisplayed()

        // Verifica el texto
        composeTestRule.onNodeWithText("Aquí irá el contenido del blog...",
            ignoreCase = true,
            substring = true)
            .assertIsDisplayed()
    }
}
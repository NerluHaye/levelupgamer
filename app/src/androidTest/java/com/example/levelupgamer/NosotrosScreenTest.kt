package com.example.levelupgamer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.ui.NosotrosScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NosotrosScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun nosotrosScreen_allContentHeadingsAndValuesAreDisplayed() {
        composeTestRule.setContent {
            LevelUpGamerTheme {
                NosotrosScreen(onBack = {})
            }
        }

        // --- ASERCIONES DE TÍTULOS PRINCIPALES ---

        composeTestRule.onNodeWithText("Quiénes Somos", ignoreCase = true).assertIsDisplayed()

        composeTestRule.onNodeWithText("Nuestra misión", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Nuestra visión", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Nuestros valores", ignoreCase = true).assertIsDisplayed()


        // Descripción de la tienda
        composeTestRule.onNodeWithText("una tienda online dedicada a satisfacer", substring = true).assertIsDisplayed()

        // Valores
        composeTestRule.onNodeWithText("tecnolog", ignoreCase = true, substring = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Experiencia del", ignoreCase = true, substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun nosotrosScreen_missionAndVisionContentArePresent() {
        composeTestRule.setContent {
            LevelUpGamerTheme {
                NosotrosScreen(onBack = {})
            }
        }

        composeTestRule.onNodeWithText("productos de alta calidad para gamers", substring = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("ser la tienda online líder", substring = true)
            .assertIsDisplayed()
    }
}


package com.example.levelupgamer.test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupgamer.ui.CartScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class DummyRepository : com.example.levelupgamer.data.repository.ProductRepository() {
    override fun getProducts(): List<com.example.levelupgamer.model.Product> = emptyList()
    override fun getCart(): List<com.example.levelupgamer.model.CartItem> = emptyList()
}

class MockProductViewModel(
    initialItems: List<com.example.levelupgamer.model.CartItem>
) : com.example.levelupgamer.viewmodel.ProductViewModel(repository = DummyRepository()) {

    override val cartItems: StateFlow<List<com.example.levelupgamer.model.CartItem>> =
        MutableStateFlow(value = initialItems)
}


@RunWith(AndroidJUnit4::class)
class CartScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cartScreen_emptyStateIsDisplayed() {
        val mockViewModel = MockProductViewModel(initialItems = emptyList())

        composeTestRule.setContent {
            LevelUpGamerTheme {
                CartScreen(
                    productViewModel = mockViewModel as com.example.levelupgamer.viewmodel.ProductViewModel,
                    onBack = {},
                    onProceedToPayment = {}
                )
            }
        }

        // Aserciones:
        // 1. Verifica el total
        composeTestRule.onNodeWithText("Total: $0", ignoreCase = true, substring = true)
            .assertIsDisplayed()

    }

    @Test
    fun cartScreen_withItems_elementsAndTotalAreDisplayed() {

        val item1 = com.example.levelupgamer.model.Product(id = 1, nombre = "Teclado Mecánico", precio = 150)
        val item2 = com.example.levelupgamer.model.Product(id = 2, nombre = "Mouse Gamer", precio = 50)

        val mockItems = listOf(
            com.example.levelupgamer.model.CartItem(product = item1, cantidad = 2), // 150 * 2 = 300
            com.example.levelupgamer.model.CartItem(product = item2, cantidad = 1)  // 50 * 1 = 50
        )
        val expectedTotal = 350 // (300 + 50)

        val mockViewModel = MockProductViewModel(initialItems = mockItems)

        composeTestRule.setContent {
            LevelUpGamerTheme {
                CartScreen(
                    productViewModel = mockViewModel as com.example.levelupgamer.viewmodel.ProductViewModel,
                    onBack = {},
                    onProceedToPayment = {}
                )
            }
        }

        // Aserciones:

        // los productos (Nombres)
        composeTestRule.onNodeWithText("Teclado Mecánico", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Mouse Gamer", ignoreCase = true).assertIsDisplayed()

        // las cantidades
        composeTestRule.onNodeWithText("Cantidad: 2", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Cantidad: 1", ignoreCase = true).assertIsDisplayed()

        // el total
        composeTestRule.onNodeWithText("Total: $${expectedTotal}", ignoreCase = true, substring = true).assertIsDisplayed()

        // botones
        composeTestRule.onNodeWithText("Proceder al pago", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Vaciar carrito", ignoreCase = true, substring = true)
            .assertIsDisplayed()

    }
}
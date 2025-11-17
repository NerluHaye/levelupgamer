package com.example.levelupgamer.test

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.levelupgamer.model.Product
import com.example.levelupgamer.ui.ProductDetailScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.ProductViewModel
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // 1. Mockeamos el ViewModel, que es la dependencia principal
    private val mockViewModel = mockk<ProductViewModel>(relaxed = true)

    // Datos de prueba para el producto
    private val testProductId = 5L
    private val testProduct = Product(
        id = testProductId,
        nombre = "Logitech G413",
        precio = 199.99, // Importante: usar Double
        nombreCategoria = " Mouse y teclados Gamer",
        descripcion = "Teclado Gamer Logitech G413 Mechanical",
        imagenUrl = null
    )

    // Configuración inicial de MockK antes de cada prueba
    @Before
    fun setup() {
        // Configuramos el mock para que, cuando se llame a getProductById(5L),
        // devuelva nuestro producto de prueba. Usamos coEvery porque es una función suspend.
        coEvery { mockViewModel.getProductById(testProductId) } returns testProduct
    }

    @Test
    fun productDetailScreen_loadsAndDisplaysData() = runTest {
        // Mocks para las funciones de navegación
        val onBackMock = mockk<() -> Unit>(relaxed = true)
        val onOpenCartMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            LevelUpGamerTheme {
                ProductDetailScreen(
                    productId = testProductId,
                    productViewModel = mockViewModel,
                    onBack = onBackMock,
                    onOpenCart = onOpenCartMock
                )
            }
        }

        // Esperamos a que la corrutina LaunchedEffect (que llama a getProductById) finalice
        composeTestRule.waitForIdle()

        // --- Verificación de Despliegue de Datos ---

        // 1. Verificar que el nombre y precio del producto se muestran
        composeTestRule.onNodeWithText(testProduct.nombre, substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Precio: $${testProduct.precio}", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(testProduct.descripcion, substring = true).assertIsDisplayed()

        // 2. Verificar que el botón "Añadir al carrito" está presente
        composeTestRule.onNodeWithText("Añadir al carrito").assertIsDisplayed().assertIsEnabled()
    }

    @Test
    fun productDetailScreen_addToCartButton_callsViewModel() = runTest {
        val onBackMock = mockk<() -> Unit>(relaxed = true)
        val onOpenCartMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            LevelUpGamerTheme {
                ProductDetailScreen(
                    productId = testProductId,
                    productViewModel = mockViewModel,
                    onBack = onBackMock,
                    onOpenCart = onOpenCartMock
                )
            }
        }

        composeTestRule.waitForIdle()

        // --- Simulación de Interacción ---

        // Simular el clic en el botón Añadir al carrito
        composeTestRule.onNodeWithText("Añadir al carrito").performClick()

        // --- Verificación de Llamada al ViewModel ---

        // Verificar que addToCart fue llamado exactamente una vez con el producto de prueba y cantidad 1
        verify(exactly = 1) {
            mockViewModel.addToCart(product = testProduct, cantidad = 1)
        }
    }

    @Test
    fun productDetailScreen_backButton_callsOnBack() = runTest {
        val onBackMock = mockk<() -> Unit>(relaxed = true)
        val onOpenCartMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            LevelUpGamerTheme {
                ProductDetailScreen(
                    productId = testProductId,
                    productViewModel = mockViewModel,
                    onBack = onBackMock,
                    onOpenCart = onOpenCartMock
                )
            }
        }

        composeTestRule.waitForIdle()

        // --- Simulación de Interacción ---

        // El botón 'Atrás' usa el contentDescription "Atrás"
        composeTestRule.onNodeWithContentDescription("Atrás").performClick()

        // --- Verificación de Llamada a la Navegación ---

        // Verificar que la función de callback onBack fue llamada
        verify(exactly = 1) {
            onBackMock.invoke()
        }
    }

}
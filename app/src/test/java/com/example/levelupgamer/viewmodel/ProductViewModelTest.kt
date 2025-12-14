package com.example.levelupgamer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProductViewModel
    private lateinit var repository: ProductRepository


    private val product1 = Product(
        1,
        "Logitech G733 Wireless",
        159.990,
        "Accesorios y Periféricos Gaming Esports",
        "Auriculares inalámbricos para juegos LIGHTSPEED con LIGHTSYNC RGB",
        null)
    private val product2 = Product(
        2,
        "DualSense PS5",
        52.990,
        "Accesorios y Periféricos Gaming Esports",
        "Control inalámbrico DualSense para PS5",
        null)

    @Before
    fun setUp(): Unit {
        Dispatchers.setMain(testDispatcher)
        repository = mock()

        whenever(repository.getCart()).thenReturn(emptyList<CartItem>())

        runBlocking {
            whenever(repository.getProducts()).thenReturn(emptyList<Product>())
        }
    }

    private fun createViewModel() {
        viewModel = ProductViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init - fetches products successfully`() = runTest {

        val productList = listOf(product1, product2)
        runBlocking {
            whenever(repository.getProducts()).thenReturn(productList)
        }

        createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()
    }


    @Test
    fun `init - handles exception when fetching products`() = runTest {
        runBlocking {
            whenever(repository.getProducts()).thenThrow(RuntimeException())
        }

        createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList<Product>(), viewModel.products.value)
    }

    @Test
    fun `getProductById - returns correct product`() = runTest {
        val productList = listOf(product1, product2)

        runBlocking {
            whenever(repository.getProductById(1L)).thenReturn(product1)
        }

        runBlocking {
            whenever(repository.getProducts()).thenReturn(listOf(product1, product2))
        }

        createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.getProductById(1L)

        assertNotNull(result)
        assertEquals(product1, result)
    }

    @Test
    fun `getProductById - returns null for non-existent product`() = runTest {
        whenever(repository.getProductById(99L)).thenReturn(null)
        createViewModel()

        val result = viewModel.getProductById(99L)

        assertNull(result)
    }

    @Test
    fun `addToCart - adds product and updates cart`() = runTest {
        val updatedCart = listOf(CartItem(product1, 1))
        whenever(repository.getCart()).thenReturn(emptyList<CartItem>(), updatedCart)
        createViewModel()

        viewModel.addToCart(product1, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).addToCart(product1, 1)
        assertEquals(updatedCart, viewModel.cartItems.value)
    }

    @Test
    fun `removeFromCart - removes product and updates cart`() = runTest {
        val initialCart = listOf(CartItem(product1, 1))
        whenever(repository.getCart()).thenReturn(initialCart, emptyList<CartItem>())
        createViewModel()

        viewModel.removeFromCart(product1)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).removeFromCart(product1)
        assertEquals(emptyList<CartItem>(), viewModel.cartItems.value)
    }

    @Test
    fun `clearCart - clears all products from cart`() = runTest {

        val initialCart = listOf(CartItem(product1, 1), CartItem(product2, 2))

        whenever(repository.getCart())
            .thenReturn(initialCart)
            .thenReturn(emptyList<CartItem>())

        createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(initialCart, viewModel.cartItems.value)

        viewModel.clearCart()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).clearCart()

        assertEquals(emptyList<CartItem>(), viewModel.cartItems.value)
    }
}
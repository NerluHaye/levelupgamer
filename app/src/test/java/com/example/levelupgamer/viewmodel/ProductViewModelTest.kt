package com.example.levelupgamer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    // Test data
    private val product1 = Product(1, "Product 1", "Description 1", 10.0, "", 10)
    private val product2 = Product(2, "Product 2", "Description 2", 20.0, "", 20)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        // Mock the initial cart state
        whenever(repository.getCart()).thenReturn(emptyList())
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
        // Given
        val productList = listOf(product1, product2)
        whenever(repository.getProducts()).thenReturn(productList)

        // When
        createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(productList, viewModel.products.value)
    }

    @Test
    fun `init - handles exception when fetching products`() = runTest {
        // Given
        whenever(repository.getProducts()).thenThrow(RuntimeException())

        // When
        createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(emptyList<Product>(), viewModel.products.value)
    }

    @Test
    fun `getProductById - returns correct product`() = runTest {
        // Given
        whenever(repository.getProductById(1L)).thenReturn(product1)
        createViewModel()

        // When
        val result = viewModel.getProductById(1L)

        // Then
        assertNotNull(result)
        assertEquals(product1, result)
    }

    @Test
    fun `getProductById - returns null for non-existent product`() = runTest {
        // Given
        whenever(repository.getProductById(99L)).thenReturn(null)
        createViewModel()

        // When
        val result = viewModel.getProductById(99L)

        // Then
        assertNull(result)
    }

    @Test
    fun `addToCart - adds product and updates cart`() = runTest {
        // Given
        val updatedCart = listOf(CartItem(product1, 1))
        whenever(repository.getCart()).thenReturn(emptyList(), updatedCart)
        createViewModel()

        // When
        viewModel.addToCart(product1, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(repository).addToCart(product1, 1)
        assertEquals(updatedCart, viewModel.cartItems.value)
    }

    @Test
    fun `removeFromCart - removes product and updates cart`() = runTest {
        // Given
        val initialCart = listOf(CartItem(product1, 1))
        whenever(repository.getCart()).thenReturn(initialCart, emptyList())
        createViewModel()

        // When
        viewModel.removeFromCart(product1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(repository).removeFromCart(product1)
        assertEquals(emptyList<CartItem>(), viewModel.cartItems.value)
    }

    @Test
    fun `clearCart - clears all products from cart`() = runTest {
        // Given
        val initialCart = listOf(CartItem(product1, 1), CartItem(product2, 2))
        whenever(repository.getCart()).thenReturn(initialCart, emptyList())
        createViewModel()

        // When
        viewModel.clearCart()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(repository).clearCart()
        assertEquals(emptyList<CartItem>(), viewModel.cartItems.value)
    }
}
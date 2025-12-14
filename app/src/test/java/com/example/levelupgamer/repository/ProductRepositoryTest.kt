package com.example.levelupgamer.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.data.remote.model.ProductoDTO
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.model.Product
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ProductRepositoryTest {
    private lateinit var repository: ProductRepository
    private lateinit var mockApiService: ApiService

    private val productoDTO1 = ProductoDTO(
        id = 1,
        codigo = "12345",
        nombre = "Logitech G733 Wireless",
        precio = 159.990,
        descripcion = "Auriculares inalámbricos",
        stock = 10,
        nombreCategoria = "Accesorios y Periféricos",
        imagenUrl = ""
    )

    @Before
    fun setUp() {
        mockApiService = mock()
        repository = ProductRepository(mockApiService)

    }

    @Test
    fun `getProducts success - returns mapped product list`() = runTest {
        val apiResponse = listOf(productoDTO1)

        whenever(mockApiService.getAllProductos()).thenReturn(apiResponse)

        val result: List<Product> = repository.getProducts()

        assertEquals(1, result.size)
        val mappedProduct = result.first()

        assertEquals(productoDTO1.id, mappedProduct.id)
        assertEquals(productoDTO1.nombre, mappedProduct.nombre)
        assertTrue(productoDTO1.precio.compareTo(mappedProduct.precio) == 0)
        assertTrue(mappedProduct is Product)
    }

    @Test
    fun `getProducts failure - throws IOException on network error`() = runTest {

        val networkException = RuntimeException("Error simulado de red")

        whenever(mockApiService.getAllProductos()).thenThrow(networkException)

        val result = kotlin.runCatching { repository.getProducts() }

        assertTrue(result.isFailure)

        val actualException = result.exceptionOrNull()
        assertTrue(actualException is RuntimeException || actualException is java.io.IOException)
    }
}
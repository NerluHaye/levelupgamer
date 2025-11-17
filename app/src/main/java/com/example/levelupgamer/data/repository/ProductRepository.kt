package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product

class ProductRepository(private val apiService: ApiService) {

    private val cart = mutableListOf<CartItem>()

    suspend fun getProducts(): List<Product> {
        return try {
            val dtoList = apiService.getAllProductos()
            Log.d("ProductRepository", "Productu DTO recivido: ${dtoList.size} items")
            // Imprime los detalles del primer producto para depuración
            if (dtoList.isNotEmpty()) {
                Log.d("ProductRepositoryDetail", "First product DTO: ${dtoList.first()}")
            }
            dtoList.map { dto ->
                Product(
                    id = dto.id,
                    nombre = dto.nombre,
                    precio = dto.precio.toDouble(),
                    nombreCategoria = dto.nombreCategoria,
                    descripcion = dto.descripcion,
                    imagenUrl = dto.imagenUrl
                )
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "ERROR FETCHING PRODUCTS: ", e)
            emptyList()
        }
    }

    suspend fun getProductById(id: Long): Product? {
        return try {
            val dto = apiService.getProductoById(id)
            Product(
                id = dto.id,
                nombre = dto.nombre,
                precio = dto.precio.toDouble(),
                nombreCategoria = dto.nombreCategoria,
                descripcion = dto.descripcion,
                imagenUrl = dto.imagenUrl
            )
        } catch (e: Exception) {
            Log.e("ProductRepository", "ERROR FETCHING PRODUCT ID: $id", e)
            null
        }
    }

    fun getCart(): List<CartItem> = cart.map { it.copy() }

    fun addToCart(product: Product, cantidad: Int = 1) {
        val existing = cart.find { it.product.id == product.id }
        if (existing != null) {
            existing.cantidad += cantidad
        } else {
            cart.add(CartItem(product, cantidad))
        }
    }

    fun clearCart() {
        cart.clear()
    }

    fun removeFromCart(product: Product) {
        val existing = cart.find { it.product.id == product.id }
        if (existing != null) {
            cart.remove(existing)
        }
    }

    fun payCart() {
        cart.clear()
    }
}
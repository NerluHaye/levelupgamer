package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product

open class ProductRepository(private val apiService: ApiService) {

    private val cart = mutableListOf<CartItem>()

    open suspend fun getProducts(): List<Product> {
        return try {
            val dtoList = apiService.getAllProductos()
            dtoList.map { dto ->
                Product(
                    id = dto.id,
                    nombre = dto.nombre,
                    precio = dto.precio,
                    nombreCategoria = dto.nombreCategoria,
                    descripcion = dto.descripcion,
                    imagenUrl = dto.imagenUrl
                )
            }
        } catch (e: Exception) {
            throw e
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

    open fun getCart(): List<CartItem> = cart.map { it.copy() }

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
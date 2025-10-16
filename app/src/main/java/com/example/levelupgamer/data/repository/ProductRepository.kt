package com.example.levelupgamer.data.repository

import com.example.levelupgamer.model.Product
import com.example.levelupgamer.data.local.ProductLocalDataSource
import com.example.levelupgamer.model.CartItem

class ProductRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun getProducts(): List<Product> = ProductLocalDataSource.productos

    fun getCart(): List<CartItem> = cartItems

    fun addToCart(product: Product, cantidad: Int = 1) {
        val existing = cartItems.find { it.product.id == product.id }
        if (existing != null) {
            existing.cantidad += cantidad
        } else {
            cartItems.add(CartItem(product, cantidad))
        }
    }

    fun removeFromCart(product: Product) {
        cartItems.removeAll { it.product.id == product.id }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
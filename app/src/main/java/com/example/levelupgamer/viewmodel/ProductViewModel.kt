package com.example.levelupgamer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products: List<Product> = repository.getProducts()
    private val _cartItems = MutableLiveData<List<CartItem>>(repository.getCart())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    fun addToCart(product: Product, cantidad: Int = 1) {
        repository.addToCart(product, cantidad)
        _cartItems.value = repository.getCart()
    }

    fun removeFromCart(product: Product) {
        repository.removeFromCart(product)
        _cartItems.value = repository.getCart()
    }

    fun clearCart() {
        repository.clearCart()
        _cartItems.value = repository.getCart()
    }
}
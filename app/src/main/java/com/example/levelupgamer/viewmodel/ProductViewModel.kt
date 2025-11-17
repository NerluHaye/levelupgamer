package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        fetchProductsFromApi()
    }

    private fun fetchProductsFromApi() {
        viewModelScope.launch {
            try {
                val productList = repository.getProducts()
                _products.value = productList
            } catch (e: Exception) {
                _products.value = emptyList()
            }
        }
    }
    suspend fun getProductById(id: Long): Product? {
        return repository.getProductById(id)
    }
    private val _cartItems = MutableStateFlow<List<CartItem>>(repository.getCart())
    open val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product, cantidad: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(product, cantidad)
            _cartItems.value = repository.getCart()
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            repository.removeFromCart(product)
            _cartItems.value = repository.getCart()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            _cartItems.value = repository.getCart()
        }
    }

    fun payCart() {
        viewModelScope.launch {
            repository.payCart()
            _cartItems.value = repository.getCart()
        }
    }
}
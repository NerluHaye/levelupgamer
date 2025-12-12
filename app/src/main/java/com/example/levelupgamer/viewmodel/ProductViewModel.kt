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

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _products.value = productRepository.getProducts()
        }
    }

    fun fetchProductById(productId: Long) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            _cartItems.value = currentItems.map {
                if (it.product.id == product.id) it.copy(cantidad = it.cantidad + 1) else it
            }
        } else {
            _cartItems.value = currentItems + CartItem(id = product.id, product = product, cantidad = 1)
        }
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filter { it.product.id != product.id }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}

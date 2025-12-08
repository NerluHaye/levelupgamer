package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.CarritoRepository
import com.example.levelupgamer.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repo: CarritoRepository) : ViewModel() {
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _items

    private val _total = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _total

    fun loadCart() {
        viewModelScope.launch {
            val fetchedItems = repo.getCarritoItems()
            _items.value = fetchedItems
            _total.value = fetchedItems.sumOf { it.product.precio * it.cantidad }
        }
    }

    fun addToCart(productId: Long) {
        viewModelScope.launch {
            if (repo.agregarItemAlCarrito(productId, 1)) {
                loadCart()
            }
        }
    }

    fun removeFromCart(cartItemId: Long) {
        viewModelScope.launch {
            if (repo.removerItemDelCarrito(cartItemId)) {
                loadCart()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _items.value.forEach { repo.removerItemDelCarrito(it.id) }
            loadCart()
        }
    }
}

package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.CarritoRepository
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. Quitamos 'open' y añadimos 'CarritoRepository' al constructor
class ProductViewModel(
    private val productRepository: ProductRepository,
    private val carritoRepository: CarritoRepository // <-- ¡AHORA USA EL REPOSITORIO DE CARRITO!
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        loadProducts()
        // 2. Ahora cargamos el carrito desde el backend al iniciar
        loadCart()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _products.value = productRepository.getProducts()
        }
    }

    // 3. NUEVA FUNCIÓN: Carga el carrito desde el backend y actualiza el estado
    fun loadCart() {
        viewModelScope.launch {
            _cartItems.value = carritoRepository.getCarritoItems()
        }
    }

    // 4. FUNCIÓN MEJORADA: Ahora habla con el backend usando el ID del producto
    fun addToCart(productId: Long, cantidad: Int = 1) {
        viewModelScope.launch {
            val success = carritoRepository.agregarItemAlCarrito(productId, cantidad)
            if (success) {
                // Si la operación en el backend tiene éxito, recargamos el carrito
                loadCart()
            }
        }
    }

    // 5. FUNCIÓN MEJORADA: Ahora habla con el backend usando el ID del CartItem
    fun removeFromCart(cartItemId: Long) {
        viewModelScope.launch {
            val success = carritoRepository.removerItemDelCarrito(cartItemId)
            if (success) {
                loadCart()
            }
        }
    }

    // 6. NUEVA FUNCIÓN: Para actualizar cantidades en el backend
    fun updateCartItemQuantity(cartItemId: Long, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity < 1) {
                // Si la nueva cantidad es 0 o menos, simplemente eliminamos el item
                removeFromCart(cartItemId)
            } else {
                val success = carritoRepository.actualizarCantidadItem(cartItemId, newQuantity)
                if (success) {
                    loadCart()
                }
            }
        }
    }

    // 7. Esta función ahora solo limpia el estado local para la pantalla de éxito
    fun clearCart() {
        // En un futuro, aquí podrías llamar a una función del backend para vaciar el carrito.
        // Por ahora, solo limpia la UI.
        _cartItems.value = emptyList()
    }
}

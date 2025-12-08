package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.repository.ProductRepository

class ProductViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Ahora solo le pasamos el productRepository
            return ProductViewModel(productRepository) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida")
    }
}

package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.repository.CarritoRepository // 1. Se añade la importación
import com.example.levelupgamer.data.repository.ProductRepository
class ProductViewModelFactory(
    private val productRepository: ProductRepository,
    private val carritoRepository: CarritoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            // 3. Se pasan AMBOS repositorios al crear el ViewModel
            return ProductViewModel(productRepository, carritoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

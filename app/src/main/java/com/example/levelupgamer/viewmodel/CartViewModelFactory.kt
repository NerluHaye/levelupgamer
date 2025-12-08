package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.repository.CarritoRepository

class CartViewModelFactory(private val repo: CarritoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

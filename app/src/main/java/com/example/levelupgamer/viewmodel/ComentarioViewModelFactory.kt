package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.repository.ComentarioRepository

class ComentarioViewModelFactory(
    private val comentarioRepository: ComentarioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComentarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComentarioViewModel(comentarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.repository.BlogRepository
class BlogCreationViewModelFactory(
    private val repository: BlogRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogCreationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlogCreationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
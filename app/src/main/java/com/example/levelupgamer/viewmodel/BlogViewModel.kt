package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.model.Blog
import com.example.levelupgamer.data.repository.BlogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BlogViewModel(private val repository: BlogRepository) : ViewModel() {

    private val _blogs = MutableStateFlow<List<Blog>>(emptyList())
    val blogs: StateFlow<List<Blog>> get() = _blogs

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        fetchBlogs()
    }

    fun fetchBlogs() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repository.getAllBlogs()
                _blogs.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar blogs"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBlogById(id: Long, onResult: (Blog?) -> Unit) {
        viewModelScope.launch {
            try {
                val blog = repository.getBlogById(id)
                onResult(blog)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }
}
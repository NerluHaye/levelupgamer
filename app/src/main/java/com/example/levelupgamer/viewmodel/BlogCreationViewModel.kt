package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.BlogRepository
import com.example.levelupgamer.model.Blog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BlogCreationViewModel(
    private val repository: BlogRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun createBlog(token: String, titulo: String, contenido: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

            val newBlog = Blog(
                id = null,
                titulo = titulo,
                contenido = contenido,
                autor = "",
                fechaPublicacion = currentDate,
                enabled = true
            )

            try {
                val createdBlog = repository.createBlog(token, newBlog)

                if (createdBlog != null) {
                    onSuccess()
                } else {
                    _error.value = "No se pudo crear el blog."
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
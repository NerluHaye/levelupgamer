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
    private val repository: BlogRepository,
    private val autor: String // El nombre del usuario logueado
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun createBlog(titulo: String, contenido: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

            val newBlog = Blog(
                id = null,
                titulo = titulo,
                contenido = contenido,
                autor = autor,
                fechaPublicacion = currentDate,
                enabled = true
            )

            try {
                val createdBlog = repository.createBlog(newBlog)
                if (createdBlog != null) {
                    onSuccess() // ¡Éxito! Avisamos a la UI para que navegue.
                } else {
                    _error.value = "No se pudo crear el blog. Inténtalo de nuevo."
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.ComentarioRepository
import com.example.levelupgamer.model.Comentario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ComentarioViewModel(
    private val comentarioRepository: ComentarioRepository
) : ViewModel() {

    private val _comentarios = MutableStateFlow<List<Comentario>>(emptyList())
    val comentarios: StateFlow<List<Comentario>> = _comentarios.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchComentarios(productoId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = comentarioRepository.getComentarios(productoId)
                _comentarios.value = result
            } catch (e: Exception) {
                _error.value = "Error al cargar comentarios: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun postComentario(token: String, productoId: Long, texto: String) {
        if (texto.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val nuevoComentario = comentarioRepository.publicarComentario(token, productoId, texto)
                if (nuevoComentario != null) {
                    // Actualizamos la lista localmente agregando el nuevo comentario al inicio
                    val currentList = _comentarios.value.toMutableList()
                    currentList.add(0, nuevoComentario)
                    _comentarios.value = currentList
                } else {
                    _error.value = "No se pudo publicar el comentario"
                }
            } catch (e: Exception) {
                _error.value = "Error al publicar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteComentario(token: String, comentarioId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = comentarioRepository.borrarComentario(token, comentarioId)
                if (success) {
                    // Actualizamos la lista localmente removiendo el comentario borrado
                    _comentarios.value = _comentarios.value.filter { it.id != comentarioId }
                } else {
                    _error.value = "No se pudo borrar el comentario"
                }
            } catch (e: Exception) {
                _error.value = "Error al borrar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

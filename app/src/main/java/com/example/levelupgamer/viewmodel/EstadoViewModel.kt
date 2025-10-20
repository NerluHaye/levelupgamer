package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstadoViewModel : ViewModel() {

    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    init {
        viewModelScope.launch {
            // Simular carga desde almacenamiento (DataStore/SharedPref) si se desea
            delay(100)
            _activo.value = false
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {
            val current = _activo.value ?: false
            _activo.value = !current
            // Mostrar mensaje de confirmación durante 1.5s
            _mostrarMensaje.value = true
            delay(1500)
            _mostrarMensaje.value = false
        }
    }
}

package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.remote.model.RegistroUsuarioDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<UsuarioDTO>?>(null)
    val registerState: StateFlow<Result<UsuarioDTO>?> = _registerState.asStateFlow()

    fun register(nombre: String, email: String, password: String, fechaNacimiento: String) {
        if (nombre.isBlank() || email.isBlank() || password.isBlank() || fechaNacimiento.isBlank()) {
            _registerState.value = Result.Error("Todos los campos obligatorios deben ser rellenados.")
            return
        }

        viewModelScope.launch {
            _registerState.value = Result.Loading
            try {
                val registroDTO = RegistroUsuarioDTO(nombre, email, password, fechaNacimiento)
                val registeredUser = repository.register(registroDTO)
                _registerState.value = Result.Success(registeredUser)
            } catch (e: Exception) {
                _registerState.value = Result.Error("Error en el registro. Inténtalo de nuevo.")
            }
        }
    }

    fun resetRegisterState() {
        _registerState.value = null
    }
}

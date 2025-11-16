package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _user = MutableStateFlow<UsuarioDTO?>(null)
    val user: StateFlow<UsuarioDTO?> = _user.asStateFlow()

    private val _loginState = MutableStateFlow<Result<UsuarioDTO>?>(null)
    val loginState: StateFlow<Result<UsuarioDTO>?> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            try {
                val loginDTO = LoginDTO(email, password)
                val loggedInUser = repository.login(loginDTO)
                _user.value = loggedInUser
                _loginState.value = Result.Success(loggedInUser)
            } catch (e: Exception) {
                _loginState.value = Result.Error("Correo o contraseña incorrectos")
            }
        }
    }

    fun logout() {
        _user.value = null
        _loginState.value = null
    }

    fun resetLoginState() {
        _loginState.value = null
    }
}

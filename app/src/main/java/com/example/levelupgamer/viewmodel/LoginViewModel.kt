package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.data.util.Result.*


class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<Result<Boolean>?>(null)
    val loginState: StateFlow<Result<Boolean>?> = _loginState

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        // Validar campos
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _loginState.value = Result.Error("Email y contraseña son obligatorios")
            return
        }

        viewModelScope.launch {
            _loginState.value = Result.Loading
            val result = repository.login(_email.value, _password.value)
            _loginState.value = when(result) {
                is Result.Success -> Success(true)
                is Result.Error -> Error(result.message)
                else -> null
            }
        }
    }

    fun resetState() {
        _loginState.value = null
    }
}
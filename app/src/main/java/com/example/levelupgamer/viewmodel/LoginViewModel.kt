package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<Result<Boolean>?>(null)
    val loginState: StateFlow<Result<Boolean>?> = _loginState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _loginState.value = Result.Error("Email y contraseña son obligatorios")
            return
        }

        viewModelScope.launch {
            _loginState.value = Result.Loading
            val result = repository.login(_email.value, _password.value)
            _loginState.value = when(result) {
                is Result.Success -> {
                    _isLoggedIn.value = true
                    _username.value = result.data.username // <-- guardamos el username
                    Result.Success(true)
                }
                is Result.Error -> Result.Error(result.message)
                else -> null
            }
        }
    }

    fun resetState() {
        _loginState.value = null
    }

    fun logout() {
        _isLoggedIn.value = false
        _email.value = ""
        _password.value = ""
        _username.value = ""
        _loginState.value = null
    }
}

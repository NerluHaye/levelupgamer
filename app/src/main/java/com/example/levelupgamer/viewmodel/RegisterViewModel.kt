package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _registerState = MutableStateFlow<Result<Boolean>?>(null)
    val registerState: StateFlow<Result<Boolean>?> = _registerState

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun register() {
        val user = User(
            username = _username.value,
            email = _email.value,
            password = _password.value
        )

        viewModelScope.launch {
            _registerState.value = Result.Loading
            val result = repository.register(user)
            _registerState.value = result
        }
    }

    fun resetFields() {
        _email.value = ""
        _password.value = ""
        _username.value = ""
    }

    fun resetState() {
        _registerState.value = null
    }
}

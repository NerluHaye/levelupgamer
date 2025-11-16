package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.remote.model.RegistroUsuarioDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
class AuthRepository(private val apiService: ApiService) {

    suspend fun login(loginDTO: LoginDTO): UsuarioDTO {
        return apiService.loginUsuario(loginDTO)
    }

    suspend fun register(registroUsuarioDTO: RegistroUsuarioDTO): UsuarioDTO {
        return apiService.registrarUsuario(registroUsuarioDTO)
    }

}
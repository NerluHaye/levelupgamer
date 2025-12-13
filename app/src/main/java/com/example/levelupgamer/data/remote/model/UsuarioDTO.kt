package com.example.levelupgamer.data.remote.model

data class UsuarioDTO (
    val id: Long,
    val nombre: String,
    val email: String,
    val tieneDescuentoDuoc: Boolean,
    val puntosLevelUp: Int,
    val rol: String,
    val token: String?
)
package com.example.levelupgamer.data.remote.model

data class RegistroUsuarioDTO(
    val nombre: String,
    val email: String,
    val password: String,
    val fechaNacimiento: String,
    val codigoReferido: String?
)
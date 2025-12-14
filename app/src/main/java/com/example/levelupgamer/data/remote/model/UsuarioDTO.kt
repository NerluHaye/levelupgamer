package com.example.levelupgamer.data.remote.model

import com.google.gson.annotations.SerializedName

data class UsuarioDTO (
    val id: Long? = null,
    @SerializedName("nombre", alternate = ["nombreUsuario"])
    val nombre: String? = null,
    @SerializedName("email", alternate = ["username"])
    val email: String? = null,
    val tieneDescuentoDuoc: Boolean? = false,
    val puntosLevelUp: Int? = 0,
    val rol: String? = null,
    val token: String? = null
)

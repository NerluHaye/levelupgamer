package com.example.levelupgamer.data.remote.model

import java.time.LocalDateTime

data class ComentarioDTO (
    val id: Long? = null,
    val comentario: String,
    val nombreUsuario: String,
    val fechaCreacion: LocalDateTime,
    val enabled: Boolean
)
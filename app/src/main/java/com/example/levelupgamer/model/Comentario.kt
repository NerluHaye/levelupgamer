package com.example.levelupgamer.model

import java.time.LocalDateTime

data class Comentario (
    val id: Long,
    val comentario: String,
    val nombreUsuario: String,
    val fechaCreacion: LocalDateTime,
)
package com.example.levelupgamer.data.remote.model

data class BlogDTO(
    val id: Long? = null,
    val titulo: String,
    val contenido: String,
    val autor: String,
    val fechaPublicacion: String? = null,
    val enabled: Boolean
)

package com.example.levelupgamer.model

data class Blog(
    val id: Long? = null,
    val titulo: String,
    val contenido: String,
    val autor: String,
    val fechaPublicacion: String,
    val enabled: Boolean
)

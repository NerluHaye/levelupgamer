package com.example.levelupgamer.model

data class Product(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val categoria: String = "",
    val descripcion: String = "",
    val imageRes: Int? = null
)
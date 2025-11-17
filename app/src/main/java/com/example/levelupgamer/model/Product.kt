package com.example.levelupgamer.model

data class Product(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val nombreCategoria: String,
    val descripcion: String,
    val imagenUrl: String?
)
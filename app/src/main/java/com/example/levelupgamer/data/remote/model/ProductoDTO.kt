package com.example.levelupgamer.data.remote.model

import java.math.BigDecimal

data class ProductoDTO(
    val id: Long,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val nombreCategoria: String,
    val imagenUrl: String
)


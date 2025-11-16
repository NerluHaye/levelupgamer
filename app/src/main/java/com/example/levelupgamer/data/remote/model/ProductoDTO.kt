package com.example.levelupgamer.data.remote.model

import java.math.BigDecimal

data class ProductoDTO(
    val id: Long,
    val codigo: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int,
    val categoryName: String,
    val imageUrl: String
)


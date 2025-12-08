package com.example.levelupgamer.model

data class CartItem(
    val id: Long,
    val product: Product,
    var cantidad: Int = 1
)
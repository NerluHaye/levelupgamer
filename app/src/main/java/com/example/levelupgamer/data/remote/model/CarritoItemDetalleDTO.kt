package com.example.levelupgamer.data.remote.model


data class CarritoItemDetalleDTO(
    val id: Long? = null, // Hacemos el ID nulo porque no lo enviamos al agregar
    val productoId: Long,
    val nombreProducto: String? = null,
    val precioUnitario: Double? = null,
    val cantidad: Int,
    val subtotal: Double? = null
)


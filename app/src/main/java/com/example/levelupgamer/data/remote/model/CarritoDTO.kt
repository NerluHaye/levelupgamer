package com.example.levelupgamer.data.remote.model


data class CarritoDTO(
    val id: Long,
    val items: List<CarritoItemDetalleDTO>,
    val totalGeneral: Double,
    val enabled: Boolean
)


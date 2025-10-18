package com.example.levelupgamer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val precio: Int,
    val categoria: String,
    val descripcion: String,
    val imageUrl: String
)
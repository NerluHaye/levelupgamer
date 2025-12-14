package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.model.Product

// Quitamos 'open' porque ya no es necesario
class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(): List<Product> {
        return try {
            val dtoList = apiService.getAllProductos()
            dtoList.map { dto ->
                Product(
                    id = dto.id,
                    nombre = dto.nombre,
                    precio = dto.precio.toDouble(),
                    nombreCategoria = dto.nombreCategoria,
                    descripcion = dto.descripcion,
                    imagenUrl = dto.imagenUrl
                )
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "ERROR FETCHING PRODUCTS: ", e)
            emptyList()
        }
    }

    suspend fun getProductById(id: Long): Product? {
        return try {
            val dto = apiService.getProductoById(id)
            Product(
                id = dto.id,
                nombre = dto.nombre,
                precio = dto.precio.toDouble(),
                nombreCategoria = dto.nombreCategoria,
                descripcion = dto.descripcion,
                imagenUrl = dto.imagenUrl
            )
        } catch (e: Exception) {
            Log.e("ProductRepository", "ERROR FETCHING PRODUCT ID: $id", e)
            null
        }
    }
}

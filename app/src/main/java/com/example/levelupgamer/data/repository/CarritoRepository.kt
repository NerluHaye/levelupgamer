package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.data.remote.model.CarritoItemDetalleDTO
import com.example.levelupgamer.model.CartItem

class CarritoRepository(
    private val apiService: ApiService,
    // Necesitamos el ProductRepository para obtener los detalles completos del producto
    private val productRepository: ProductRepository
) {

    /**
     * Obtiene el carrito del backend y lo convierte a una lista de CartItems.
     */
    suspend fun getCarritoItems(): List<CartItem> {
        return try {
            val carritoDto = apiService.getCarrito()
            // Mapeamos cada item del DTO a un CartItem de nuestro modelo
            carritoDto.items.mapNotNull { itemDto ->
                val product = productRepository.getProductById(itemDto.productoId)
                if (product != null) {
                    CartItem(
                        id = itemDto.id ?: 0L, // El ID que viene del DTO del carrito
                        product = product,
                        cantidad = itemDto.cantidad
                    )
                } else {
                    null // Si el producto no se encuentra, lo ignoramos
                }
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al obtener el carrito: ", e)
            emptyList() // Si hay un error, devolvemos una lista vacía
        }
    }

    /**
     * Agrega un producto al carrito en el backend.
     */
    suspend fun agregarItemAlCarrito(productoId: Long, cantidad: Int): Boolean {
        return try {
            val itemDto = CarritoItemDetalleDTO(productoId = productoId, cantidad = cantidad)
            apiService.agregarItemAlCarrito(itemDto)
            true // Éxito
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al agregar item al carrito: ", e)
            false // Fallo
        }
    }

    /**
     * Remueve un item del carrito en el backend.
     */
    suspend fun removerItemDelCarrito(itemId: Long): Boolean {
        return try {
            apiService.removerItemDelCarrito(itemId)
            true
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al remover item del carrito: ", e)
            false
        }
    }

    /**
     * Actualiza la cantidad de un item en el carrito en el backend.
     */
    suspend fun actualizarCantidadItem(itemId: Long, nuevaCantidad: Int): Boolean {
        return try {
            apiService.actualizarCantidadItem(itemId, nuevaCantidad)
            true
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al actualizar cantidad: ", e)
            false
        }
    }
}

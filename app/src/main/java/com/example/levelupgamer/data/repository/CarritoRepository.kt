package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.data.remote.model.CarritoItemDetalleDTO
import com.example.levelupgamer.model.CartItem

class CarritoRepository(
    private val apiService: ApiService,
    private val productRepository: ProductRepository
) {

    private fun getAuthToken(): String? {
        val token = TokenRepository.token ?: run {
            // Log de error si no encontramos el token
            Log.e("AUTH_TOKEN", "Se intentó usar el CarritoRepository ¡pero el token es NULO!")
            return null
        }
        val authToken = "Bearer $token"
        // Log para ver el token que estamos a punto de enviar
        Log.d("AUTH_TOKEN", "Enviando a la API el token: $authToken")
        return authToken
    }

    suspend fun getCarritoItems(): List<CartItem> {
        val authToken = getAuthToken() ?: return emptyList()
        return try {
            // Llama a la API pasándole el token
            val carritoDto = apiService.getCarrito(authToken)
            carritoDto.items.mapNotNull { itemDto ->
                val product = productRepository.getProductById(itemDto.productoId)
                if (product != null) {
                    CartItem(
                        id = itemDto.id ?: 0L,
                        product = product,
                        cantidad = itemDto.cantidad
                    )
                } else { null }
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al obtener el carrito: ", e)
            emptyList()
        }
    }

    suspend fun agregarItemAlCarrito(productoId: Long, cantidad: Int): Boolean {
        val authToken = getAuthToken() ?: return false
        Log.d("CARRITO_REPO", "Intentando agregar producto ID: $productoId") // <-- AÑADE ESTO
        return try {
            val itemDto = CarritoItemDetalleDTO(productoId = productoId, cantidad = cantidad)
            apiService.agregarItemAlCarrito(authToken, itemDto)
            Log.d("CARRITO_REPO", "Producto ID: $productoId agregado (sin excepción).") // <-- Y ESTO
            true
        } catch (e: Exception) {
            Log.e("CARRITO_REPO", "Error al agregar item: ", e) // <-- CAMBIA EL TAG PARA COHERENCIA
            false
        }
    }

    suspend fun removerItemDelCarrito(itemId: Long): Boolean {
        val authToken = getAuthToken() ?: return false
        return try {
            apiService.removerItemDelCarrito(authToken, itemId)
            true
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al remover item: ", e)
            false
        }
    }

    suspend fun actualizarCantidadItem(itemId: Long, nuevaCantidad: Int): Boolean {
        val authToken = getAuthToken() ?: return false
        return try {
            apiService.actualizarCantidadItem(authToken, itemId, nuevaCantidad)
            true
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al actualizar cantidad: ", e)
            false
        }
    }
}

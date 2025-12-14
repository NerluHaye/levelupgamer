package com.example.levelupgamer.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.data.remote.model.ComentarioDTO
import com.example.levelupgamer.model.Comentario 
import java.time.LocalDateTime

class ComentarioRepository(private val apiService: ApiService) {

    suspend fun getComentarios(productoId: Long): List<Comentario> {
        return try {
            val dtoList = apiService.getComentariosByProducto(productoId)

            dtoList.map { dto ->
                Comentario(
                    id = dto.id ?: 0,
                    comentario = dto.comentario,
                    nombreUsuario = dto.nombreUsuario,
                    fechaCreacion = dto.fechaCreacion
                )
            }
        } catch (e: Exception) {
            Log.e("ComentarioRepo", "ERROR FETCHING COMENTARIOS: ", e)
            emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun publicarComentario(token: String, productoId: Long, textoComentario: String): Comentario? {
        return try {

            val requestDto = ComentarioDTO(
                id = null,
                comentario = textoComentario,
                nombreUsuario = "",
                fechaCreacion = LocalDateTime.now(),
                enabled = true
            )

            val tokenHeader = "Bearer $token"

            val responseDto = apiService.crearComentario(tokenHeader, productoId, requestDto)

            Comentario(
                id = responseDto.id ?: 0,
                comentario = responseDto.comentario,
                nombreUsuario = responseDto.nombreUsuario,
                fechaCreacion = responseDto.fechaCreacion
            )

        } catch (e: Exception) {
            Log.e("ComentarioRepo", "ERROR POSTING COMENTARIO: ", e)
            null
        }
    }

    suspend fun borrarComentario(token: String, comentarioId: Long): Boolean {
        return try {
            val tokenHeader = "Bearer $token"
            apiService.borrarComentario(tokenHeader, comentarioId)
            true
        } catch (e: Exception) {
            Log.e("ComentarioRepo", "ERROR DELETING COMENTARIO: ", e)
            false
        }
    }
}

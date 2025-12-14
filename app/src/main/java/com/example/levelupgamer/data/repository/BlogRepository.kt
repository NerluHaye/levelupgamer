package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.model.Blog
import com.example.levelupgamer.data.remote.model.BlogDTO

class BlogRepository(private val apiService: ApiService) {

    suspend fun getAllBlogs(): List<Blog> {
        return try {
            val dtoList = apiService.getAllBlogs()
            dtoList.map { dto ->
                Blog(
                    id = dto.id ?: 0L,
                    titulo = dto.titulo,
                    contenido = dto.contenido,
                    autor = dto.autor,
                    fechaPublicacion = dto.fechaPublicacion ?: "",
                    enabled = dto.enabled
                )
            }
        } catch (e: Exception) {
            Log.e("BlogRepository", "Error getAllBlogs", e)
            emptyList()
        }
    }

    suspend fun getBlogById(id: Long): Blog? {
        return try {
            val dto = apiService.getBlogById(id)
            Blog(
                id = dto.id ?: 0L,
                titulo = dto.titulo,
                contenido = dto.contenido,
                autor = dto.autor,
                fechaPublicacion = dto.fechaPublicacion ?: "",
                enabled = dto.enabled
            )
        } catch (e: Exception) {
            Log.e("BlogRepository", "Error getBlogById", e)
            null
        }
    }

    suspend fun createBlog(token: String, blog: Blog): Blog? {
        return try {
            val blogDto = BlogDTO(
                id = null,
                titulo = blog.titulo,
                contenido = blog.contenido,
                autor = "",
                fechaPublicacion = null,
                enabled = true
            )

            val tokenHeader = "Bearer $token"

            val responseDto = apiService.createBlog(tokenHeader, blogDto)

            Blog(
                id = responseDto.id ?: 0L,
                titulo = responseDto.titulo,
                contenido = responseDto.contenido,
                autor = responseDto.autor,
                fechaPublicacion = responseDto.fechaPublicacion ?: "",
                enabled = responseDto.enabled
            )
        } catch (e: Exception) {
            Log.e("BlogRepository", "ERROR CREATING BLOG: ${e.message}")
            null
        }
    }
}
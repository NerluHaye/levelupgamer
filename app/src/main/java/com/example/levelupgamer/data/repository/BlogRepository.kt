package com.example.levelupgamer.data.repository

import android.util.Log
import com.example.levelupgamer.data.remote.ApiService
import com.example.levelupgamer.model.Blog
import com.example.levelupgamer.data.remote.model.BlogDTO

class BlogRepository(private val apiService: ApiService) {

    // Obtener todos los blogs
    suspend fun getAllBlogs(): List<Blog> {
        return try {
            val dtoList = apiService.getAllBlogs()
            Log.d("BlogRepository", "Blogs recibidos: ${dtoList.size} items")
            dtoList.map { dto ->
                Blog(
                    id = dto.id,
                    titulo = dto.titulo,
                    contenido = dto.contenido,
                    autor = dto.autor,
                    fechaPublicacion = dto.fechaPublicacion,
                    enabled = dto.enabled
                )
            }
        } catch (e: Exception) {
            Log.e("BlogRepository", "ERROR FETCHING BLOGS: ", e)
            emptyList()
        }
    }

    // Obtener blog por ID
    suspend fun getBlogById(id: Long): Blog? {
        return try {
            val dto = apiService.getBlogById(id)
            Blog(
                id = dto.id,
                titulo = dto.titulo,
                contenido = dto.contenido,
                autor = dto.autor,
                fechaPublicacion = dto.fechaPublicacion,
                enabled = dto.enabled
            )
        } catch (e: Exception) {
            Log.e("BlogRepository", "ERROR FETCHING BLOG ID: $id", e)
            null
        }
    }

    // Crear un nuevo blog
    suspend fun createBlog(blog: Blog): Blog? {
        return try {
            val dto = apiService.createBlog(
                BlogDTO(
                    id = blog.id,
                    titulo = blog.titulo,
                    contenido = blog.contenido,
                    autor = blog.autor,
                    fechaPublicacion = blog.fechaPublicacion,
                    enabled = blog.enabled
                )
            )
            Blog(
                id = dto.id,
                titulo = dto.titulo,
                contenido = dto.contenido,
                autor = dto.autor,
                fechaPublicacion = dto.fechaPublicacion,
                enabled = dto.enabled
            )
        } catch (e: Exception) {
            Log.e("BlogRepository", "ERROR CREATING BLOG: ", e)
            null
        }
    }
}

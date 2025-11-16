package com.example.levelupgamer.data.remote

import com.example.levelupgamer.data.remote.model.CategoriaDTO
import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.remote.model.ProductoDTO
import com.example.levelupgamer.data.remote.model.RegistroUsuarioDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // --- AUTENTICACIÓN ---

    @POST("api/auth/register")
    suspend fun registrarUsuario(@Body registroDTO: RegistroUsuarioDTO): UsuarioDTO

    @POST("api/auth/login")
    suspend fun loginUsuario(@Body loginDTO: LoginDTO): UsuarioDTO


    // --- PRODUCTOS ---

    @GET("/api/productos") // (Añadí la / que faltaba, buena práctica)
    suspend fun getAllProductos(): List<ProductoDTO>

    @GET("/api/productos/{id}")
    suspend fun getProductoById(@Path("id") id: Long): ProductoDTO

    @GET("/api/productos/categoria/{nombreCategoria}")
    suspend fun getProductosByCategoria(@Path("nombreCategoria") nombreCategoria: String): List<ProductoDTO>


    // --- CATEGORÍAS ---

    @GET("/api/categorias")
    suspend fun getAllCategorias(): List<CategoriaDTO>

    @GET("/api/categorias/{id}")
    suspend fun getCategoriaById(@Path("id") id: Long): CategoriaDTO
}
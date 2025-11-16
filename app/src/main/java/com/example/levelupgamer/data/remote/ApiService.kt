package com.example.levelupgamer.data.remote

import com.example.levelupgamer.data.remote.model.CategoriaDTO
import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.remote.model.ProductoDTO
import com.example.levelupgamer.data.remote.model.RegistroUsuarioDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // --- AUTENTICACIÓN ---

    @POST("api/auth/register")
    fun registrarUsuario(@Body registroDTO: RegistroUsuarioDTO): Call<UsuarioDTO>

    @POST("api/auth/login")
    fun loginUsuario(@Body loginDTO: LoginDTO): Call<UsuarioDTO>


    // --- PRODUCTOS ---

    @GET("/api/productos") // (Añadí la / que faltaba, buena práctica)
    fun getAllProductos(): Call<List<ProductoDTO>>

    @GET("/api/productos/{id}")
    fun getProductoById(@Path("id") id: Long): Call<ProductoDTO>

    @GET("/api/productos/categoria/{nombreCategoria}")
    fun getProductosByCategoria(@Path("nombreCategoria") nombreCategoria: String): Call<List<ProductoDTO>>


    // --- CATEGORÍAS ---

    @GET("/api/categorias")
    fun getAllCategorias(): Call<List<CategoriaDTO>>

    @GET("/api/categorias/{id}")
    fun getCategoriaById(@Path("id") id: Long): Call<CategoriaDTO>
}
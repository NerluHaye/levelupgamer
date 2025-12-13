package com.example.levelupgamer.data.remote

import retrofit2.http.Query
import com.example.levelupgamer.data.remote.model.BlogDTO
import com.example.levelupgamer.data.remote.model.CategoriaDTO
import com.example.levelupgamer.data.remote.model.LoginDTO
import com.example.levelupgamer.data.remote.model.ProductoDTO
import com.example.levelupgamer.data.remote.model.RegistroUsuarioDTO
import com.example.levelupgamer.data.remote.model.UsuarioDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.levelupgamer.data.remote.model.CarritoDTO
import com.example.levelupgamer.data.remote.model.CarritoItemDetalleDTO
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PUT

interface ApiService {

    // --- AUTENTICACIÓN ---
    @POST("api/v1/auth/register")
    suspend fun registrarUsuario(@Body registroDTO: RegistroUsuarioDTO): UsuarioDTO

    @POST("api/v1/auth/login")
    suspend fun loginUsuario(@Body loginDTO: LoginDTO): UsuarioDTO


    // --- PRODUCTOS ---

    @GET("api/v1/productos") // (Añadí la / que faltaba, buena práctica)
    suspend fun getAllProductos(): List<ProductoDTO>

    @GET("api/v1/productos/{id}")
    suspend fun getProductoById(@Path("id") id: Long): ProductoDTO


    // --- PRODUCTOS POR CATEGORÍA ---
    // --- No implementado aun ---
    @GET("api/v1/productos/categoria/{nombreCategoria}")
    suspend fun getProductosByCategoria(@Path("nombreCategoria") nombreCategoria: String): List<ProductoDTO>


    // --- CATEGORÍAS ---

    @GET("api/v1/categorias")
    suspend fun getAllCategorias(): List<CategoriaDTO>

    @GET("/api/v1/categorias/{id}")
    suspend fun getCategoriaById(@Path("id") id: Long): CategoriaDTO

    // --- BLOGS ---
    @GET("api/v1/blog")
    suspend fun getAllBlogs(): List<BlogDTO>

    @GET("api/v1/blog/{blogId}")
    suspend fun getBlogById(@Path("blogId") id: Long): BlogDTO

    @POST("api/v1/blog")
    suspend fun createBlog(@Body blogDto: BlogDTO): BlogDTO

    // --- CARRITO ---

    @GET("api/v1/carrito")
    suspend fun getCarrito(        @Header("Authorization") token: String
    ): CarritoDTO

    @POST("api/v1/carrito/agregar")
    suspend fun agregarItemAlCarrito(
        @Header("Authorization") token: String,
        @Body itemDto: CarritoItemDetalleDTO
    ): CarritoDTO

    @DELETE("api/v1/carrito/remover/{itemId}")
    suspend fun removerItemDelCarrito(
        @Header("Authorization") token: String,
        @Path("itemId") itemId: Long
    ): CarritoDTO

    @PUT("api/v1/carrito/actualizar/{itemId}")
    suspend fun actualizarCantidadItem(
        @Header("Authorization") token: String,
        @Path("itemId") itemId: Long,
        @Query("nuevaCantidad") nuevaCantidad: Int
    ): CarritoDTO

}
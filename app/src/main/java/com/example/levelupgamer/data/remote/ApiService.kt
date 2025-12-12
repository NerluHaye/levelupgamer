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
import retrofit2.http.PUT

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


    // --- PRODUCTOS POR CATEGORÍA ---
    // --- No implementado aun ---
    @GET("/api/productos/categoria/{nombreCategoria}")
    suspend fun getProductosByCategoria(@Path("nombreCategoria") nombreCategoria: String): List<ProductoDTO>


    // --- CATEGORÍAS ---

    @GET("/api/categorias")
    suspend fun getAllCategorias(): List<CategoriaDTO>

    @GET("/api/categorias/{id}")
    suspend fun getCategoriaById(@Path("id") id: Long): CategoriaDTO
    // --- BLOGS ---
    @GET("/api/v1/blog")
    suspend fun getAllBlogs(): List<BlogDTO>

    @GET("/api/v1/blog/{id}")
    suspend fun getBlogById(@Path("id") id: Long): BlogDTO

    @POST("/api/v1/blog")
    suspend fun createBlog(@Body blogDto: BlogDTO): BlogDTO
    // --- CARRITO v2 ---


    @GET("/api/v2/carrito")
    suspend fun getCarrito(): CarritoDTO

    @POST("/api/v2/carrito/agregar")
    suspend fun agregarItemAlCarrito(@Body itemDto: CarritoItemDetalleDTO): CarritoDTO

    @DELETE("/api/v2/carrito/remover/{itemId}")
    suspend fun removerItemDelCarrito(@Path("itemId") itemId: Long): CarritoDTO

    @PUT("/api/v2/carrito/actualizar/{itemId}")
    suspend fun actualizarCantidadItem(
        @Path("itemId") itemId: Long,
        @Query("nuevaCantidad") nuevaCantidad: Int
    ): CarritoDTO
}
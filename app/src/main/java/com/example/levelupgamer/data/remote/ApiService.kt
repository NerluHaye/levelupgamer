package com.example.levelupgamer.data.remote

import com.example.levelupgamer.data.remote.model.* // Asegúrate de importar tus modelos
import retrofit2.http.*

interface ApiService {

    // --- AUTENTICACIÓN ---
    @POST("api/v1/auth/register")
    suspend fun registrarUsuario(@Body registroDTO: RegistroUsuarioDTO): UsuarioDTO

    @POST("api/v1/auth/login")
    suspend fun loginUsuario(@Body loginDTO: LoginDTO): UsuarioDTO
    // Ojo: Si el backend devuelve un objeto con token (ej: AuthResponse), cambia UsuarioDTO por eso.


    // --- PRODUCTOS ---
    // Quitamos la barra inicial para evitar conflictos con la BaseURL
    @GET("api/v1/productos")
    suspend fun getAllProductos(): List<ProductoDTO>

    @GET("api/v1/productos/{id}")
    suspend fun getProductoById(@Path("id") id: Long): ProductoDTO

    @GET("api/v1/productos/categoria/{nombreCategoria}")
    suspend fun getProductosByCategoria(@Path("nombreCategoria") nombreCategoria: String): List<ProductoDTO>


    // --- CATEGORÍAS ---
    @GET("api/v1/categorias")
    suspend fun getAllCategorias(): List<CategoriaDTO>

    @GET("api/v1/categorias/{id}") // Corregido: quitada la barra inicial /
    suspend fun getCategoriaById(@Path("id") id: Long): CategoriaDTO


    // --- BLOGS ---
    @GET("api/v1/blog")
    suspend fun getAllBlogs(): List<BlogDTO>

    @GET("api/v1/blog/{blogId}")
    suspend fun getBlogById(@Path("blogId") id: Long): BlogDTO

    @POST("api/v1/blog")
    suspend fun createBlog(
        @Header("Authorization") token: String,
        @Body blogDto: BlogDTO
    ): BlogDTO


    // --- CARRITO ---
    @GET("api/v1/carrito")
    suspend fun getCarrito(
        @Header("Authorization") token: String
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

    @PUT("api/v1/carrito/item/{itemId}")
    suspend fun actualizarCantidadItem(
        @Header("Authorization") token: String,
        @Path("itemId") itemId: Long,
        // CORRECCIÓN IMPORTANTE: El backend espera "cantidad", no "nuevaCantidad"
        @Query("cantidad") nuevaCantidad: Int
    ): CarritoDTO


    // --- COMENTARIOS (NUEVO) ---
    // Agregamos esto para conectar con lo que acabamos de arreglar en el backend

    @GET("api/v1/comentarios/producto/{productoId}")
    suspend fun getComentariosByProducto(@Path("productoId") productoId: Long): List<ComentarioDTO>

    @POST("api/v1/comentarios/producto/{productoId}")
    suspend fun crearComentario(
        @Header("Authorization") token: String,
        @Path("productoId") productoId: Long,
        @Body comentarioDTO: ComentarioDTO
    ): ComentarioDTO

    @DELETE("api/v1/comentarios/{comentarioId}")
    suspend fun borrarComentario(
        @Header("Authorization") token: String,
        @Path("comentarioId") comentarioId: Long
    ): Void // O Unit, ya que devuelve 204 No Content
}
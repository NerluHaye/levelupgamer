package com.example.levelupgamer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.levelupgamer.R
import com.example.levelupgamer.viewmodel.CartViewModel
import com.example.levelupgamer.viewmodel.ProductViewModel
import com.example.levelupgamer.viewmodel.ComentarioViewModel
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.data.repository.TokenRepository
import com.example.levelupgamer.model.Comentario
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Long,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    comentarioViewModel: ComentarioViewModel,
    loginViewModel: LoginViewModel,
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {

    val product by productViewModel.product.collectAsStateWithLifecycle()
    val comentarios by comentarioViewModel.comentarios.collectAsStateWithLifecycle()
    val comentariosLoading by comentarioViewModel.isLoading.collectAsStateWithLifecycle()
    val user by loginViewModel.user.collectAsStateWithLifecycle()

    var nuevoComentario by remember { mutableStateOf("") }

    LaunchedEffect(key1 = productId) {
        productViewModel.fetchProductById(productId)
        comentarioViewModel.fetchComentarios(productId)
    }

    Scaffold(
        containerColor = Color(0xFF121212),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0x80000000),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF00BFA5))
            }
        } else {
            val currentProduct = product!!
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                ) {
                    if (currentProduct.imagenUrl != null) {
                        AsyncImage(
                            model = currentProduct.imagenUrl,
                            contentDescription = currentProduct.nombre,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF2C2C2C)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.logolevelupgamer),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp),
                                tint = Color.Gray
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xFF121212)),
                                    startY = 500f
                                )
                            )
                    )
                }

                // Contenido del detalle
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .offset(y = (-40).dp)
                ) {
                    Text(
                        text = currentProduct.nombre,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "$${currentProduct.precio}",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color(0xFF00BFA5) // Cyan/Teal
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF00BFA5)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = currentProduct.descripcion,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp),
                        color = Color(0xFFE0E0E0)
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = { cartViewModel.addToCart(currentProduct.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2962FF),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "AÑADIR AL CARRITO",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // SECCIÓN DE COMENTARIOS
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Comentarios (${comentarios.size})",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF00BFA5)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (user != null) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = nuevoComentario,
                                    onValueChange = { nuevoComentario = it },
                                    placeholder = { Text("Escribe una opinión...", color = Color.Gray) },
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF00BFA5),
                                        unfocusedBorderColor = Color.Gray,
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = {
                                        TokenRepository.token?.let { token ->
                                            if (nuevoComentario.isNotBlank()) {
                                                comentarioViewModel.postComentario(token, productId, nuevoComentario)
                                                nuevoComentario = ""
                                            }
                                        }
                                    },
                                    enabled = nuevoComentario.isNotBlank() && !comentariosLoading
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Send,
                                        contentDescription = "Enviar",
                                        tint = if (nuevoComentario.isNotBlank()) Color(0xFF00BFA5) else Color.Gray
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "Inicia sesión para dejar un comentario",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (comentariosLoading && comentarios.isEmpty()) {
                        CircularProgressIndicator(color = Color(0xFF00BFA5), modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else if (comentarios.isEmpty()) {
                        Text(
                            text = "No hay comentarios aún. ¡Sé el primero!",
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        comentarios.forEach { comentario ->
                            ComentarioItem(
                                comentario = comentario,
                                canDelete = user?.nombre == comentario.nombreUsuario,
                                onDelete = {
                                    TokenRepository.token?.let { token ->
                                        comentarioViewModel.deleteComentario(token, comentario.id)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComentarioItem(
    comentario: Comentario,
    canDelete: Boolean,
    onDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF00BFA5),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = comentario.nombreUsuario,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                
                // Formateo simple de fecha
                val fecha = try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        comentario.fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    } else {
                        comentario.fechaCreacion.toString().take(10)
                    }
                } catch (e: Exception) { "" }
                
                Text(
                    text = fecha,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                if (canDelete) {
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar",
                            tint = Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comentario.comentario,
                color = Color(0xFFE0E0E0),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

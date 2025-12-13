package com.example.levelupgamer.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.model.Blog
import com.example.levelupgamer.viewmodel.BlogViewModel

@Composable
fun BlogScreen(
    viewModel: BlogViewModel,
    onBlogClick: (Long?) -> Unit,
    onAddBlogClick: () -> Unit
) {
    val blogs by viewModel.blogs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    Scaffold(
        containerColor = Color(0xFF121212), // Fondo oscuro
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBlogClick,
                containerColor = Color(0xFF2962FF), // Azul Eléctrico
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir nuevo blog")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(color = Color(0xFF00BFA5)) // Cyan/Teal
                }
                error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¡Ups! Algo salió mal.",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error ?: "Error desconocido",
                            color = Color.Gray
                        )
                    }
                }
                blogs.isEmpty() -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No hay blogs publicados aún.",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 80.dp) // Espacio para el FAB
                    ) {
                        items(blogs, key = { it.id ?: it.hashCode() }) { blog ->
                            BlogItem(
                                blog = blog,
                                onClick = { if (blog.id != null) onBlogClick(blog.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogItem(blog: Blog, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(if (isPressed) 2.dp else 6.dp, label = "elevation")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                /*
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
                */
            )
            .padding(vertical = 4.dp), // Separación visual extra si se necesita
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E) // Tarjeta oscura
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Header con degradado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF2962FF), // Azul Eléctrico
                                Color(0xFF00BFA5)  // Cyan/Teal
                            )
                        )
                    )
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = blog.titulo,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = blog.contenido,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Divider(color = Color.Gray.copy(alpha = 0.2f))
                
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Autor",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF00BFA5) // Cyan/Teal
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = blog.autor,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF00BFA5) // Cyan/Teal
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Fecha",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = blog.fechaPublicacion,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

package com.example.levelupgamer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val blogs = viewModel.blogs.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.errorMessage.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBlogClick) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir nuevo blog")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading.value -> {
                    CircularProgressIndicator()
                }
                error.value != null -> {
                    Text(text = error.value ?: "Error desconocido", fontSize = 18.sp)
                }
                blogs.value.isEmpty() -> {
                    Text(text = "No hay blogs disponibles", fontSize = 18.sp)
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // CORRECCIÓN: Se usa una key segura para Ids nulos y se pasa el id (Long?) al hacer click
                        items(blogs.value, key = { it.id ?: it.hashCode() }) { blog ->
                            Box(modifier = Modifier.clickable {
                                // Solo se ejecuta el click si el ID no es nulo, para seguridad
                                if (blog.id != null) {
                                    onBlogClick(blog.id)
                                }
                            }) {
                                BlogItem(blog = blog)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogItem(blog: Blog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = blog.titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = blog.contenido,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Autor: ${blog.autor}", style = MaterialTheme.typography.bodySmall)
                Text(text = blog.fechaPublicacion, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

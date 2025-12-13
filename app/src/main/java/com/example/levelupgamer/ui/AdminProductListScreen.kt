package com.example.levelupgamer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.levelupgamer.model.Product
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun AdminProductListScreen(
    productViewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {

    val products by productViewModel.products.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {

        Text(
            text = "Administración - Inventario de Productos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        if (products.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Producto",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00BFA5),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Cantidad",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00BFA5),
                            modifier = Modifier.wrapContentWidth(Alignment.End)
                        )
                    }
                }


                items(products, key = { it.id }) { product ->
                    AdminProductItem(product = product)
                }
            }
        }
    }
}

@Composable
private fun AdminProductItem(product: Product) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Columna para la información del producto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = "ID: ${product.id} - Precio: $${product.precio}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Cantidad del producto
            Text(
                // Aquí deberías usar la cantidad real del producto si la tienes en tu modelo
                // Por ahora, como no está, pondremos un número de ejemplo.
                // text = product.quantity.toString(),
                text = "10", // <- VALOR DE EJEMPLO
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


package com.example.levelupgamer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.viewmodel.ProductViewModel
import com.example.levelupgamer.model.Product      // O la ruta correcta de tu modelo Product
import com.example.levelupgamer.model.CartItem    // O la ruta correcta de tu modelo CartItem



@Composable
fun CartScreen(
    productViewModel: com.example.levelupgamer.viewmodel.ProductViewModel,
    onBack: () -> Unit,
    onProceedToPayment: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cartItems by productViewModel.cartItems.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("CartScreen", "CartScreen composed, items=${cartItems.size}")
    }

    Column(modifier = modifier.fillMaxSize().padding(12.dp)) {
        // Header
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onBack) { Text("Volver") }

            val totalPrice = cartItems.sumOf { it.product.precio * it.cantidad }
            Text(
                text = "Total: $${totalPrice}",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "El carrito está vacío", color = Color.White)
            }
            return
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937)) // mismo gris que ProductList
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (item.product.imageRes != null) {
                            Image(
                                painter = painterResource(id = item.product.imageRes),
                                contentDescription = item.product.nombre,
                                modifier = Modifier.size(64.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        ) {
                            Text(
                                text = item.product.nombre,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
                            Text(
                                text = "Cantidad: ${item.cantidad}",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
                            Text(
                                text = "Precio: $${item.product.precio}",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
                        }

                        IconButton(onClick = { productViewModel.removeFromCart(item.product) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.Red
                            )
                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            // Botón para vaciar el carrito
            Button(onClick = { productViewModel.clearCart() }, modifier = Modifier.weight(1f).padding(8.dp)) {
                Text(text = "Vaciar carrito", color = Color.Black)
            }
            // Botón para proceder al pago (simulado)
            Button(
                onClick = { onProceedToPayment() },
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                Text(text = "Proceder al pago", color = Color.Black)
            }

        }

    }
}

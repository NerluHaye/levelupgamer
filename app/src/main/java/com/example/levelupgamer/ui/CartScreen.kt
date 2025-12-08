package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.levelupgamer.viewmodel.CartViewModel // ¡Importa el ViewModel correcto!

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onProceedToPayment: () -> Unit,
    modifier: Modifier = Modifier
) {

    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val totalPrice by cartViewModel.totalPrice.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize().padding(12.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onBack) { Text("Volver") }


            Text(
                text = "Total: $${String.format("%.2f", totalPrice)}",
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
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems, key = { it.id }) { item -> // Usar el ID como key mejora el rendimiento
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp), // Ajuste de padding
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            if (item.product.imagenUrl != null) {
                                AsyncImage(
                                    model = item.product.imagenUrl,
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
                                    color = Color.LightGray
                                )
                                Text(
                                    text = "Precio: $${String.format("%.2f", item.product.precio)}",
                                    color = Color.LightGray
                                )
                            }


                            IconButton(onClick = { cartViewModel.removeFromCart(item.id) }) {
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
                modifier = Modifier.fillMaxWidth(), // El padding ya está en el Column padre
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Mejor que padding en cada botón
            ){

                Button(onClick = { cartViewModel.clearCart() }, modifier = Modifier.weight(1f)) {
                    Text(text = "Vaciar carrito", color = Color.Black)
                }
                Button(
                    onClick = { onProceedToPayment() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Proceder al pago", color = Color.Black)
                }
            }
        }
    }
}

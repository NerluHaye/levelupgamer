package com.example.levelupgamer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun CartScreen(
    productViewModel: ProductViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cartItems by productViewModel.cartItems.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("CartScreen", "CartScreen composed, items=${cartItems.size}")
    }

    Column(modifier = modifier.fillMaxSize().padding(12.dp)) {
        // Header
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Carrito", modifier = Modifier.weight(1f))
            Button(onClick = onBack) { Text("Volver") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "El carrito está vacío")
            }
            return
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(cartItems) { item ->
                Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        if (item.product.imageRes != null) {
                            Image(painter = painterResource(id = item.product.imageRes), contentDescription = item.product.nombre, modifier = Modifier.size(64.dp))
                        }

                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text(text = item.product.nombre, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Cantidad: ${item.cantidad}", maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Precio: $${item.product.precio}", maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        }

                        Button(onClick = { productViewModel.removeFromCart(item.product) }) {
                            Text(text = "Eliminar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { productViewModel.clearCart() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Vaciar carrito")
        }
    }
}

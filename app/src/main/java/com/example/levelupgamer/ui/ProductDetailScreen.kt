package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    productViewModel: ProductViewModel,
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val product = productViewModel.getProductById(productId) ?: return

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(painter = painterResource(id = android.R.drawable.ic_menu_revert), contentDescription = "Atrás")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onOpenCart) {
                Icon(painter = painterResource(id = android.R.drawable.ic_menu_view), contentDescription = "Ver carrito")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (product.imageRes != null) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.nombre,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = product.nombre, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Precio: $${product.precio}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = product.descripcion, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { productViewModel.addToCart(product, 1) }, modifier = Modifier.fillMaxWidth().height(48.dp)) {
            Text(text = "Añadir al carrito")
        }
    }
}

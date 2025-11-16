package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.levelupgamer.R
import com.example.levelupgamer.model.Product
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: Long,
    productViewModel: ProductViewModel,
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(key1 = Unit) {
        product = productViewModel.getProductById(productId)
    }

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

        if (product == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            product?.let { currentProduct ->
                Column(modifier = Modifier.fillMaxSize()) {
                    if (currentProduct.imageUrl != null) {
                        AsyncImage(
                            model = currentProduct.imageUrl,
                            contentDescription = currentProduct.nombre,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                    } else {
                        Box(modifier = Modifier.height(200.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Icon(painter = painterResource(R.drawable.logolevelupgamer), contentDescription = null, modifier = Modifier.size(100.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = currentProduct.nombre, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Precio: $${currentProduct.precio}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = currentProduct.descripcion, style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { productViewModel.addToCart(currentProduct, 1) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(text = "Añadir al carrito")
                    }
                }
            }
        }
    }
}
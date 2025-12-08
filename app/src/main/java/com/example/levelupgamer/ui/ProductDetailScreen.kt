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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.levelupgamer.R
import com.example.levelupgamer.viewmodel.CartViewModel
import com.example.levelupgamer.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: Long,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {

    val product by productViewModel.product.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = productId) {
        // Llama a la función con el nombre correcto
        productViewModel.fetchProductById(productId)
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(painter = painterResource(id = android.R.drawable.ic_menu_revert), contentDescription = "Atrás")
            }
            Spacer(modifier = Modifier.weight(1f))
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

            val currentProduct = product!!
            Column(modifier = Modifier.fillMaxSize()) {
                if (currentProduct.imagenUrl != null) {
                    AsyncImage(
                        model = currentProduct.imagenUrl,
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
                    onClick = { cartViewModel.addToCart(currentProduct.id) },
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

package com.example.levelupgamer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.levelupgamer.model.Product
import com.example.levelupgamer.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    onOpenDetail: (Int) -> Unit,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val products by productViewModel.products.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("ProductList", "ProductListScreen composed, products=${products.size}")
    }

    Column(modifier = modifier.fillMaxSize()) {
        Box{
            SnackbarHost(hostState = snackbarHostState)
        }

        // Simple header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Productos", modifier = Modifier.weight(1f))
        }

        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(12.dp)) {
            items(products) { product ->
                ProductItem(product = product, onOpenDetail = onOpenDetail,
                    onAddToCart = {
                        productViewModel.addToCart(product)
                        //Mostrar snackbar
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("${product.nombre} añadido al carrito")
                        }
                    })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ProductItem(product: Product, onOpenDetail: (Int) -> Unit, onAddToCart: () -> Unit) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            if (product.imageRes != null) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.nombre,
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onOpenDetail(product.id) }
                )
            }

            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)) {
                Text(text = product.nombre, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "$${product.precio}")
            }

            Button(onClick = onAddToCart) {
                Text(text = "Añadir")
            }
        }
    }
}

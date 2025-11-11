package com.example.levelupgamer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val backgroundColor = Color(0xFF1F2937) // gris azulado medio
    val textColor = Color(0xFFE5E7EB) // texto claro
    val buttonGreen = Color(0xFF22C55E)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(12.dp)
    ) {
        // Snackbar
        Box {
            SnackbarHost(hostState = snackbarHostState)
        }

        // Header
        Text(
            text = "Productos",
            fontSize = 28.sp,
            color = textColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Lista de productos
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // espaciado uniforme entre items
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onOpenDetail = onOpenDetail,
                    onAddToCart = {
                        productViewModel.addToCart(product)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("${product.nombre} añadido al carrito")
                        }
                    },
                    backgroundColor = backgroundColor,
                    textColor = textColor,
                    buttonColor = buttonGreen
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    onOpenDetail: (Int) -> Unit,
    onAddToCart: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    buttonColor: Color
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (product.imageRes != null) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.nombre,
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onOpenDetail(product.id) }
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = product.nombre,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    fontSize = 18.sp
                )
                Text(
                    text = "$${product.precio}",
                    color = buttonColor,
                    fontSize = 16.sp
                )
            }

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Añadir")
            }
        }
    }
}

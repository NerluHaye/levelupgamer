package com.example.levelupgamer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.ui.CartScreen
import com.example.levelupgamer.ui.ProductDetailScreen
import com.example.levelupgamer.ui.ProductListScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.ProductViewModel
import com.example.levelupgamer.viewmodel.ProductViewModelFactory

sealed class Screen {
    object List : Screen()
    data class Detail(val productId: Int) : Screen()
    object Cart : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelUpGamerTheme(darkTheme = true) {
                val repository = remember { ProductRepository() }
                val productViewModel: ProductViewModel =
                    viewModel(factory = ProductViewModelFactory(repository))

                var screen by remember { mutableStateOf<Screen?>(Screen.List) }

                Scaffold (
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (val currentScreen = screen) {
                            is Screen.List, null -> {
                                ProductListScreen(
                                    productViewModel = productViewModel,
                                    onOpenDetail = { productId ->
                                        screen = Screen.Detail(productId)
                                    },
                                    onOpenCart = {
                                        screen = Screen.Cart
                                    }
                                )
                            }

                            is Screen.Detail -> {
                                ProductDetailScreen(
                                    productId = currentScreen.productId,
                                    productViewModel = productViewModel,
                                    onBack = {
                                        screen = Screen.List
                                    },
                                    onOpenCart = {
                                        screen = Screen.Cart
                                    }
                                )
                            }

                            is Screen.Cart -> {
                                CartScreen(
                                    productViewModel = productViewModel,
                                    onBack = {
                                        screen = Screen.List
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    LevelUpGamerTheme{
        val repository = remember { ProductRepository() }
        val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(repository))

        ProductListScreen(
            productViewModel = productViewModel,
            onOpenDetail = {},
            onOpenCart = {}
        )
    }
}
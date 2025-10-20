package com.example.levelupgamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.Screen.*
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.ui.CartScreen
import com.example.levelupgamer.ui.LoginScreen
import com.example.levelupgamer.ui.ProductDetailScreen
import com.example.levelupgamer.ui.ProductListScreen
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.ProductViewModel
import com.example.levelupgamer.viewmodel.ProductViewModelFactory

sealed class Screen {
    object List : Screen()
    data class Detail(val productId: Int) : Screen()
    object Cart : Screen()
    object Login : Screen()
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelUpGamerTheme(darkTheme = true) {
                val repository = remember { ProductRepository() }
                val productViewModel: ProductViewModel =
                    viewModel(factory = ProductViewModelFactory(repository))

                var screen by remember { mutableStateOf<Screen?>(Screen.List) }

                val onBackClick = {
                    screen = when (screen) {
                        is Detail -> Screen.List
                        is Cart -> Screen.List
                        is Login -> Screen.List
                        else -> null
                    }
                }

                Scaffold (
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Level Up Gamer")
                            },
                            actions = {
                                //Icono de carrito
                                IconButton(onClick = { screen = Screen.Cart }) {
                                    Icon(imageVector = androidx.compose.material.icons.Icons.Default.ShoppingCart, contentDescription = "Carrito")
                                }
                                //Icono de user
                                IconButton(onClick = { screen = Screen.Login }) {
                                    Icon(imageVector = androidx.compose.material.icons.Icons.Default.AccountCircle, contentDescription = "Login")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (val currentScreen = screen) {
                            is Screen.List, null -> {
                                ProductListScreen(
                                    productViewModel = productViewModel,
                                    onOpenDetail = { productId ->
                                        screen = Detail(productId)
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

                            is Screen.Login -> {
                                // Placeholder for Login Screen
                                LoginScreen(
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
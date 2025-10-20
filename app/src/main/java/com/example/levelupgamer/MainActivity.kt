package com.example.levelupgamer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
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
            LevelUpGamerTheme {
                val repository = remember { ProductRepository() }
                val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(repository))

                // screen es nullable para evitar que se muestre temporalmente la pantalla incorrecta
                var screen by remember { mutableStateOf<Screen?>(null) }

                // Observar carrito para aplicar una regla: si el carrito está vacío no mostrar la pantalla Cart al inicio
                val cartItems by productViewModel.cartItems.collectAsState()

                // Forzar y loggear el estado inicial al arrancar
                LaunchedEffect(Unit) {
                    Log.d("MainActivity", "Initial screen at composition: $screen")
                    // Forzar inicio en List para evitar restauración accidental
                    screen = Screen.List
                }

                // Si por alguna razón 'screen' queda en Cart y el carrito está vacío, volver a List
                LaunchedEffect(cartItems) {
                    if (screen is Screen.Cart && cartItems.isEmpty()) {
                        Log.d("MainActivity", "Detected Cart with empty items — switching to List")
                        screen = Screen.List
                    }
                }

                // Determinar la pantalla efectiva que se mostrará (defensivo)
                val effectiveScreen: Screen = when {
                    screen == null -> Screen.List
                    screen is Screen.Cart && cartItems.isEmpty() -> Screen.List
                    else -> screen as Screen
                }

                // Loggear cuando cambie la pantalla y, si es Cart, registrar stacktrace
                LaunchedEffect(effectiveScreen) {
                    Log.d("MainActivity", "effective screen changed -> $effectiveScreen")
                    if (effectiveScreen is Screen.Cart) {
                        val ex = Exception("effectiveScreen changed to Cart - stacktrace")
                        Log.e("MainActivity", "effectiveScreen changed -> Cart; stacktrace:", ex)
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (val s = effectiveScreen) {

                        is Screen.List -> ProductListScreen(
                            productViewModel = productViewModel,
                            onOpenDetail = { id -> screen = Screen.Detail(id) },
                            onOpenCart = { screen = Screen.Cart },
                            modifier = Modifier.padding(innerPadding)
                        )

                        is Screen.Detail -> ProductDetailScreen(
                            productId = s.productId,
                            productViewModel = productViewModel,
                            onBack = { screen = Screen.List },
                            onOpenCart = { screen = Screen.Cart },
                            modifier = Modifier.padding(innerPadding)
                        )

                        is Screen.Cart -> CartScreen(
                            productViewModel = productViewModel,
                            onBack = { screen = Screen.List },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    LevelUpGamerTheme {
        val repository = remember { ProductRepository() }
        val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(repository))

        ProductListScreen(
            productViewModel = productViewModel,
            onOpenDetail = {},
            onOpenCart = {}
        )
    }
}
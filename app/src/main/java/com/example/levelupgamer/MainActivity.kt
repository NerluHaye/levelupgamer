package com.example.levelupgamer

import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.local.AppDatabase
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.ui.*
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.viewmodel.LoginViewModelFactory
import com.example.levelupgamer.viewmodel.ProductViewModel
import com.example.levelupgamer.viewmodel.ProductViewModelFactory
import androidx.compose.animation.*
import androidx.compose.animation.core.tween


sealed class Screen {
    object List : Screen()
    data class Detail(val productId: Int) : Screen()
    object Cart : Screen()
    object Login : Screen()
    object Register : Screen()
    object Payment : Screen()
    object Blog : Screen()
    object Nosotros : Screen()
    object Profile : Screen()
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
                val authRepository =
                    remember { //recordar que se cambiara esto una vez se conecte al backend
                        AuthRepository(
                            AppDatabase.getDatabase(this).userDao()
                        )
                    }
                val loginViewModel: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(
                        authRepository
                    )
                )

                var screen by remember { mutableStateOf<Screen>(Screen.List) }
                var isLoggedIn by remember { mutableStateOf(0) }

                // Gris azulado claro como fondo unificado
                val backgroundColor = Color(0xFF1F2937) // gris azulado medio
                val barColor = Color(0xFF18202D)

                Scaffold(
                    containerColor = backgroundColor,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Level Up Gamer",
                                    color = Color.White,
                                    modifier = Modifier.clickable { screen = Screen.List }
                                )
                            },
                            actions = {
                                IconButton(onClick = { screen = Screen.Cart }) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Carrito",
                                        tint = Color.White
                                    )
                                }
                                IconButton(onClick = {
                                    if (isLoggedIn == 1) {
                                        screen = Screen.Profile // nueva pantalla
                                    } else {
                                        screen = Screen.Login
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Usuario"
                                    )
                                }
                                IconButton(onClick = { screen = Screen.Blog }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Blog",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = barColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    bottomBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(barColor)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Call Center : +56 9 1234 5678", color = Color.White)
                            Text(
                                text = "Sobre Nosotros",
                                color = Color.White,
                                modifier = Modifier.clickable { screen = Screen.Nosotros }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(backgroundColor)
                    ) {
                        AnimatedContent(
                            targetState = screen,
                            transitionSpec = {
                                // Animación combinada que devuelve ContentTransform correctamente
                                (slideInHorizontally(
                                    initialOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(300)
                                ) + fadeIn(animationSpec = tween(300)))
                                    .togetherWith(
                                        slideOutHorizontally(
                                            targetOffsetX = { fullWidth -> -fullWidth },
                                            animationSpec = tween(300)
                                        ) + fadeOut(animationSpec = tween(300))
                                    )
                            }
                        ) { currentScreen ->
                            when (currentScreen) {
                                is Screen.List -> ProductListScreen(
                                    productViewModel = productViewModel,
                                    onOpenDetail = { productId ->
                                        screen = Screen.Detail(productId)
                                    },
                                    onOpenCart = { screen = Screen.Cart }
                                )

                                is Screen.Detail -> ProductDetailScreen(
                                    productId = currentScreen.productId,
                                    productViewModel = productViewModel,
                                    onBack = { screen = Screen.List },
                                    onOpenCart = { screen = Screen.Cart }
                                )

                                is Screen.Cart -> CartScreen(
                                    productViewModel = productViewModel,
                                    onBack = { screen = Screen.List },
                                    onProceedToPayment = { screen = Screen.Payment }
                                )

                                is Screen.Login -> LoginScreen(
                                    onLoginSuccess = {
                                        isLoggedIn = 1
                                        screen = Screen.List
                                    },
                                    onRegisterClick = { screen = Screen.Register }
                                )

                                is Screen.Register -> RegisterScreen(
                                    onBack = { screen = Screen.Login },
                                    onRegisterSuccess = { screen = Screen.Login },
                                    onLoginClick = { screen = Screen.Login }
                                )

                                is Screen.Payment -> {
                                    val cartItems =
                                        productViewModel.cartItems.collectAsState().value
                                    PaymentScreen(
                                        cartItems = cartItems,
                                        totalAmount = cartItems.sumOf { it.product.precio * it.cantidad }
                                            .toDouble(),
                                        onBack = { screen = Screen.Cart },
                                        onPaymentSuccess = {
                                            productViewModel.clearCart()
                                            screen = Screen.List
                                        }
                                    )
                                }

                                is Screen.Blog -> BlogScreen(onBack = { screen = Screen.List })
                                is Screen.Nosotros -> NosotrosScreen(onBack = {
                                    screen = Screen.List
                                })

                                is Screen.Profile -> ProfileScreen(
                                    loginViewModel = loginViewModel,
                                    onBack = { screen = Screen.List },
                                    onLogout = {
                                        loginViewModel.logout()
                                        isLoggedIn = 0
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


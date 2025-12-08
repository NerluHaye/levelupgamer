package com.example.levelupgamer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.remote.RetrofitClient
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.repository.ProductRepository
import com.example.levelupgamer.ui.*
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.viewmodel.LoginViewModelFactory
import com.example.levelupgamer.viewmodel.ProductViewModel
import com.example.levelupgamer.viewmodel.ProductViewModelFactory
import com.example.levelupgamer.viewmodel.RegisterViewModel
import com.example.levelupgamer.viewmodel.RegisterViewModelFactory
import kotlinx.coroutines.launch

sealed class Screen {
    object List : Screen()
    data class Detail(val productId: Long) : Screen()
    object Cart : Screen()
    object Login : Screen()
    object Register : Screen()
    object Payment : Screen()

    object Nosotros : Screen()
    object Profile : Screen()
    object PaymentSuccess : Screen()
    object Blog: Screen()
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelUpGamerTheme {
                val apiService = RetrofitClient.apiService
                val productRepository = remember { ProductRepository(apiService) }
                val authRepository = remember { AuthRepository(apiService) }

                val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(productRepository))
                val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(authRepository))
                val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(authRepository))

                var screen by remember { mutableStateOf<Screen>(Screen.List) }
                val user by loginViewModel.user.collectAsState()
                val isLoggedIn = user != null

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                //Menu lateral
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.background) {
                            Spacer(Modifier.height(12.dp))
                            //Iniciar sesion
                            NavigationDrawerItem(
                                label = {
                                    Text(if (isLoggedIn) "User: ${user?.nombre}\nEmail: ${user?.email}" else "Iniciar Sesión")
                                },
                                icon = {
                                    Icon(Icons.Default.AccountCircle, contentDescription = "Usuario")
                                },
                                selected = false,
                                onClick = {
                                    screen = if (isLoggedIn) Screen.Profile else Screen.Login
                                    scope.launch { drawerState.close() }
                                }
                            )
                            //Nosotros
                            NavigationDrawerItem(
                                label = { Text("Sobre Nosotros") },
                                selected = false,
                                onClick = { 
                                    screen = Screen.Nosotros
                                    scope.launch { drawerState.close() }
                                }
                            )
                            //Blog
                            NavigationDrawerItem(
                                label = { Text("Blog") },
                                selected = false,
                                onClick = {
                                    screen = Screen.Blog
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Level Up Gamer", modifier = Modifier.clickable { screen = Screen.List }) },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { screen = Screen.Cart }) {
                                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            when (val currentScreen = screen) {
                                is Screen.List -> ProductListScreen(productViewModel = productViewModel, onOpenDetail = { productId -> screen = Screen.Detail(productId) }, onOpenCart = { screen = Screen.Cart })
                                is Screen.Detail -> ProductDetailScreen(productId = currentScreen.productId, productViewModel = productViewModel, onBack = { screen = Screen.List }, onOpenCart = { screen = Screen.Cart })
                                is Screen.Cart -> CartScreen(productViewModel = productViewModel, onBack = { screen = Screen.List }, onProceedToPayment = { run { if (isLoggedIn) screen = Screen.Payment else Toast.makeText(this@MainActivity, "Debes iniciar sesión para pagar", Toast.LENGTH_SHORT).show() } })
                                is Screen.Login -> LoginScreen(loginViewModel = loginViewModel, onLoginSuccess = { screen = Screen.List }, onRegisterClick = { screen = Screen.Register })
                                is Screen.Register -> RegisterScreen(registerViewModel = registerViewModel, onRegisterSuccess = { screen = Screen.Login }, onLoginClick = { screen = Screen.Login })
                                is Screen.Payment -> { val cartItems = productViewModel.cartItems.collectAsState().value
                                    val subtotal = cartItems.sumOf { it.product.precio * it.cantidad }.toDouble()
                                    val tieneDescuento = user?.tieneDescuentoDuoc ?: false
                                    val totalFinal = if (tieneDescuento) {
                                        subtotal * 0.80 // ¡Aplica el 20% de descuento!
                                    } else {
                                        subtotal
                                    }
                                    PaymentScreen(cartItems = cartItems, totalAmount = totalFinal, onPaymentSuccess1 = if (user?.tieneDescuentoDuoc == true) {"20% de descuento a usuarios DUOC"} else {"sin descuentos aplicables"}, onBack = { screen = Screen.Cart }, onPaymentSuccess = { productViewModel.clearCart(); screen = Screen.PaymentSuccess })
                                }
                                is Screen.Nosotros -> NosotrosScreen(onBack = { screen = Screen.List })
                                is Screen.Profile -> ProfileScreen(loginViewModel = loginViewModel, onBack = { screen = Screen.List }, onLogout = { loginViewModel.logout(); screen = Screen.List })
                                is Screen.PaymentSuccess -> PaymentSuccessScreen (onReturnHome = { screen = Screen.List }
                                )
                                is Screen.Blog -> BlogScreen()

                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.example.levelupgamer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.R
import com.example.levelupgamer.data.local.AppDatabase
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val userDao = AppDatabase.getDatabase(context).userDao()
    val repository = remember { AuthRepository(userDao) }
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2937)) // fondo gris azulado
            .padding(16.dp)
    ) {
        Login(
            modifier = Modifier.align(Alignment.Center),
            loginViewModel = loginViewModel,
            onRegisterClick = onRegisterClick,
            onLoginSuccess = onLoginSuccess
        )
    }
}

@Composable
fun Login(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by loginViewModel.loginState.collectAsState()

    Column(modifier = modifier) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                loginViewModel.onEmailChange(it)
            },
            placeholder = { Text(text = "Email", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth(), // gris más oscuro dentro del campo

            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginViewModel.onPasswordChange(it)
            },
            placeholder = { Text(text = "Contraseña", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth(),

            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón Login
        Button(
            onClick = { loginViewModel.login() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Iniciar Sesión", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Registro
        Text(
            text = "¿No tienes cuenta? Registrarse",
            modifier = Modifier
                .clickable { onRegisterClick() }
                .padding(4.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF22C55E)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Estado login
        loginState?.let { state ->
            when(state) {
                is Result.Loading -> Text("Iniciando sesión...", color = Color.White)
                is Result.Success -> {
                    onLoginSuccess()
                    loginViewModel.resetState()
                }
                is Result.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logolevelupgamer),
        contentDescription = "Login Header",
        modifier = modifier
    )
}

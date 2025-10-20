package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.levelupgamer.viewmodel.LoginViewModel
import com.example.levelupgamer.data.util.Result
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
        Modifier
            .fillMaxSize()
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
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()

    Column(modifier = modifier) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(email) { loginViewModel.onEmailChange(it) }
        Spacer(modifier = Modifier.height(8.dp))
        PasswordField(password) { loginViewModel.onPasswordChange(it) }
        Spacer(modifier = Modifier.height(8.dp))
        Registrarse(onRegisterClick)
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton() {
            loginViewModel.login()
        }

        Spacer(modifier = Modifier.height(16.dp))

        loginState?.let { state ->
            when(state) {
                is Result.Loading -> Text("Iniciando sesión...", fontSize = 14.sp)
                is Result.Success -> {
                    onLoginSuccess()
                    loginViewModel.resetState()
                }
                is Result.Error -> Text("${state.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun Registrarse(onRegisterClick: () -> Unit) {
    Text(
        text = "¿No tienes una cuenta? Registrarse",
        modifier = Modifier.clickable { onRegisterClick() },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoginButton(onLoginClick: () -> Unit) {
    Button(
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = "Iniciar Sesión")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidé mi contraseña",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logolevelupgamer),
        contentDescription = "LoginHeader",
        modifier = modifier
    )
}

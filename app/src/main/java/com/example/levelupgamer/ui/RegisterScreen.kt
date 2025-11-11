package com.example.levelupgamer.ui

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
import com.example.levelupgamer.viewmodel.RegisterViewModel
import com.example.levelupgamer.viewmodel.RegisterViewModelFactory

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val context = LocalContext.current
    val userDao = AppDatabase.getDatabase(context).userDao()
    val repository = remember { AuthRepository(userDao) }
    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(repository))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F2937)) // Fondo gris oscuro
            .padding(16.dp)
    ) {
        RegisterContent(
            modifier = Modifier.align(Alignment.Center),
            viewModel = viewModel,
            onRegisterSuccess = onRegisterSuccess,
            onLoginClick = onLoginClick
        )
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val registerState by viewModel.registerState.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logolevelupgamer),
            contentDescription = "Logo LevelUpGamer",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Username simple
        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            placeholder = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email simple
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password simple
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            placeholder = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón registrar
        Button(
            onClick = { viewModel.register() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF22C55E),
                contentColor = Color.White
            )
        ) {
            Text("Registrarse", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Estado de registro
        when (val state = registerState) {
            is Result.Loading -> Text("Registrando...", fontSize = 14.sp, color = Color.White)
            is Result.Success -> {
                onRegisterSuccess()
                viewModel.resetState()
            }
            is Result.Error -> Text(state.message ?: "Error", color = MaterialTheme.colorScheme.error)
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto para ir a Login
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                "¿Ya tienes una cuenta? ",
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                "Inicia sesión",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF22C55E),
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

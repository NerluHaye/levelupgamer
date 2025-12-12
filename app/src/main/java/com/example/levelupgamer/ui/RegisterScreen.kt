package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.R
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.viewmodel.RegisterViewModel
import androidx.compose.foundation.clickable

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    registerViewModel: RegisterViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RegisterContent(
            modifier = Modifier.align(Alignment.Center),
            viewModel = registerViewModel,
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
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    val registerState by viewModel.registerState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.logolevelupgamer),
            contentDescription = "Logo LevelUpGamer",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Fecha de Nacimiento
        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = { fechaNacimiento = it },
            placeholder = { Text("Fecha de Nacimiento (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón registrar
        Button(
            onClick = { viewModel.register(nombre, email, password, fechaNacimiento) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Estado de registro
        registerState?.let { state ->
            when (state) {
                is Result.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is Result.Success -> {
                    LaunchedEffect(state) {
                        onRegisterSuccess()
                        viewModel.resetRegisterState()
                    }
                }
                is Result.Error -> Text(state.message, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto para ir a Login
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                "¿Ya tienes una cuenta? ",
                fontSize = 12.sp,
            )
            Text(
                "Inicia sesión",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

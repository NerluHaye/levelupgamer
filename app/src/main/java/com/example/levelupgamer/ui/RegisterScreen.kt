package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.R
import com.example.levelupgamer.data.repository.AuthRepository
import com.example.levelupgamer.data.local.AppDatabase
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.viewmodel.RegisterViewModel
import com.example.levelupgamer.viewmodel.RegisterViewModelFactory

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current
    val userDao = AppDatabase.getDatabase(context).userDao()
    val repository = remember { AuthRepository(userDao) }
    val viewModel: RegisterViewModel= viewModel(factory = RegisterViewModelFactory(repository))

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Register(
            modifier = Modifier.align(Alignment.Center),
            viewModel = viewModel,
            onRegisterSuccess = onRegisterSuccess
        )
    }
}

@Composable
fun Register(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    val registerState by viewModel.registerState.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        HeaderImageR(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        EmailFieldR(email) { viewModel.onEmailChange(it) }
        Spacer(modifier = Modifier.height(8.dp))
        PasswordFieldR(password) { viewModel.onPasswordChange(it) }
        Spacer(modifier = Modifier.height(16.dp))
        RegisterButton {
            viewModel.register()
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = registerState) {
            is Result.Loading -> Text("Registrando...", fontSize = 14.sp)
            is Result.Success -> {
                Text("Usuario registrado correctamente")
                onRegisterSuccess()
                viewModel.resetState()
            }
            is Result.Error -> Text("ERROR: ${state.message}")
            else -> {}
        }
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = "Registrarse", fontSize = 16.sp)
    }
}

@Composable
fun PasswordFieldR(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun EmailFieldR(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun HeaderImageR(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logolevelupgamer),
        contentDescription = "LoginHeader",
        modifier = modifier
    )
}

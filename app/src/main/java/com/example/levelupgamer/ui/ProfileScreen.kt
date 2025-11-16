package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.R
import com.example.levelupgamer.viewmodel.LoginViewModel

@Composable
fun ProfileScreen(
    loginViewModel: LoginViewModel,
    onLogout: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user by loginViewModel.user.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        user?.let { currentUser ->
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo o avatar
                Image(
                    painter = painterResource(id = R.drawable.logolevelupgamer),
                    contentDescription = "Profile Logo",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Nombre de usuario
                Text(
                    text = "Usuario: ${currentUser.nombre}",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Email
                Text(
                    text = "Email: ${currentUser.email}",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Botón cerrar sesión
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.onTertiary)
                }
            }
        }
    }
}
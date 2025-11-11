package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val email by loginViewModel.email.collectAsState()
    val username by loginViewModel.username.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F2937)) // mismo fondo que login/register
            .padding(16.dp)
    ) {
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
                text = "Usuario: $username",
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Text(
                text = "Email: $email",
                color = Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botón cerrar sesión
            Button(
                onClick = {
                    loginViewModel.logout()
                    onLogout()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
        }
    }
}

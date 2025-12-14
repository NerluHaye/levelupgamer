package com.example.levelupgamer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        containerColor = Color(0xFF121212) // Fondo oscuro
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E1E1E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text("Volver")
            }

            user?.let { currentUser ->
                Card(
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Logo o avatar
                        Image(
                            painter = painterResource(id = R.drawable.logolevelupgamer),
                            contentDescription = "Profile Logo",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2C2C2C))
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Perfil de Usuario",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF00BFA5) // Cyan/Teal
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        // Información del usuario
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Nombre",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Gray
                            )
                            Text(
                                text = currentUser.nombre ?: "Sin nombre",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Gray
                            )
                            Text(
                                text = currentUser.email ?: "Sin email",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))

                        // Botón cerrar sesión
                        Button(
                            onClick = onLogout,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEF5350), // Rojo suave para logout
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Cerrar sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

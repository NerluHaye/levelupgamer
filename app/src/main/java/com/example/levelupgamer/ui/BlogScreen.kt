package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BlogScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón de regresar
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Título de la pantalla
        Text(
            text = "Blog",
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Aquí podrás agregar luego artículos, imágenes, etc.
        Text(
            text = "Aquí irá el contenido del blog...",
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
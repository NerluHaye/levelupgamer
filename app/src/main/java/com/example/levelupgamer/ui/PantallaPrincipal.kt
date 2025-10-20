package com.example.levelupgamer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.levelupgamer.viewmodel.EstadoViewModel

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier, viewModel: EstadoViewModel) {

    // Obtener estados desde el ViewModel
    val estado = viewModel.activo.collectAsState(initial = null)
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState(initial = false)

    val estadoValue = estado.value

    if (estadoValue == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val estadoActivo = estadoValue

    val colorAnimado by animateColorAsState(
        targetValue = if (estadoActivo) Color(0xFF4CAF50) else Color(0xFFB0BEC5),
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    val textoBoton by remember(estadoActivo) { derivedStateOf { if (estadoActivo) "Desactivar" else "Activar" } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.alternarEstado() },
            colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
            modifier = Modifier.height(50.dp)
        ) {
            Text(text = textoBoton, style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(visible = mostrarMensaje) {
            Text(
                text = "¡Estado Guardado Exitosamente!",
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
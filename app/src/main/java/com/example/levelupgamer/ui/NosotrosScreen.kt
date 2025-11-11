package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NosotrosScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nosotros", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Aquí irá la información de la compañía.")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBack) {
            Text(text = "Volver")
        }
    }
}
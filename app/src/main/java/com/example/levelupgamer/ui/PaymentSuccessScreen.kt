package com.example.levelupgamer.ui

import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PaymentSuccessScreen(onReturnHome: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Gracias por tu compra!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tu pedido está en camino 🚚",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,

            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onReturnHome) {
                Text("Volver al inicio")
            }
        }
    }
}

package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaymentScreen(
    totalAmount: Double,
    onBack: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("Volver al carrito") }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total a pagar: $${totalAmount}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPaymentSuccess, modifier = Modifier.fillMaxWidth()) {
            Text("Confirmar pago")
        }
    }
}

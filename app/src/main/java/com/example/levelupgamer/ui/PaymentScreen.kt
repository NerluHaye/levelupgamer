package com.example.levelupgamer.ui

import androidx.compose.foundation.layout.* 
import androidx.compose.material3.* 
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupgamer.model.CartItem

@Composable
fun PaymentScreen(
    cartItems: List<CartItem>,
    totalAmount: Double,
    onPaymentSuccess: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Resumen de la Compra",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de productos
        cartItems.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${it.product.nombre} x${it.cantidad}", color = Color.White)
                Text(text = "$${it.product.precio * it.cantidad}", color = Color.White)
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Total
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total a Pagar:", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("$${totalAmount}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón de pago
        Button(
            onClick = onPaymentSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E))
        ) {
            Text("Pagar Ahora", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Volver al Carrito")
        }
    }
}

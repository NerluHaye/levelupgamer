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
import com.example.levelupgamer.data.util.generarComprobantePDF
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider

import com.example.levelupgamer.model.CartItem

@Composable
fun PaymentScreen(
    cartItems: List<CartItem>,
    totalAmount: Double,
    onBack: () -> Unit,
    onPaymentSuccess: () -> Unit
)

{
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("Volver al carrito") }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total a pagar: $${totalAmount}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // 1️⃣ Simula que el pago fue exitoso
                onPaymentSuccess()

                // 2️⃣ Genera el PDF con los productos del carrito
                val pdfFile = generarComprobantePDF(context, cartItems)

                // 3️⃣ Abre el PDF usando FileProvider
                val uri: Uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    pdfFile
                )
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar pago")
        }
    }
}

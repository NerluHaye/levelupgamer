package com.example.levelupgamer.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.levelupgamer.data.util.generarComprobantePDF
import com.example.levelupgamer.model.CartItem

@Composable
fun PaymentScreen(
    cartItems: List<CartItem>,
    totalAmount: Double,
    onPaymentSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    var ubicacion by remember { mutableStateOf("Ubicación no disponible") }

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
            Text(
                "Total a Pagar:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                "$${totalAmount}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar ubicación actual
        Text(text = ubicacion, fontSize = 14.sp, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        // Botón de pago
        Button(
            onClick = {
                // Verificar permisos de ubicación
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val listener = object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            ubicacion = "Lat: ${location.latitude}, Lng: ${location.longitude}"

                            // Generar PDF con ubicación
                            val pdfFile = generarComprobantePDF(context, cartItems, ubicacion)

                            // Abrir PDF
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

                            // Confirmar pago
                            onPaymentSuccess()

                            // Detener updates de GPS
                            locationManager.removeUpdates(this)
                        }
                    }

                    // Solicitar ubicación GPS
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L,
                        0f,
                        listener
                    )

                } else {
                    // Pedir permisos
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1001
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E))
        ) {
            Text("Pagar Ahora", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón volver al carrito
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

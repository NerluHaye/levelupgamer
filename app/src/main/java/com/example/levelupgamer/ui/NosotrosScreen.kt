package com.example.levelupgamer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NosotrosScreen(
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título principal
        Text(
            text = "Quiénes Somos",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0EA5E9), // color sky-500 aproximado
            modifier = Modifier.padding(bottom = 12.dp),
        )

        // Tarjeta con descripción
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F2937), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Level-Up Gamer es una tienda online dedicada a satisfacer las " +
                        "necesidades de los entusiastas de los videojuegos en Chile. " +
                        "Desde hace dos años ofrecemos consolas, accesorios, computadores " +
                        "gamers y mucho más, con despachos a todo el país.",
                fontSize = 16.sp,
                color = Color(0xFFD1D5DB), // text-gray-300
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Misión
        Text(
            text = "Nuestra misión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF22C55E), // green-600
            modifier = Modifier.padding(bottom = 12.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F2937), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Nuestra misión es proporcionar productos de alta calidad para gamers " +
                        "en todo Chile, ofreciendo una experiencia de compra única y personalizada, " +
                        "con un enfoque en la satisfacción del cliente y el crecimiento de la comunidad gamer.",
                fontSize = 16.sp,
                color = Color(0xFFD1D5DB),
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Visión
        Text(
            text = "Nuestra visión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF22C55E),
            modifier = Modifier.padding(bottom = 12.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F2937), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Nuestra visión es ser la tienda online líder en productos para gamers " +
                        "en Chile, conocida por brindar un servicio al cliente cercano y un " +
                        "programa de fidelización que recompensa la lealtad de nuestra comunidad.",
                fontSize = 16.sp,
                color = Color(0xFFD1D5DB),
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Valores
        Text(
            text = "Nuestros valores",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0EA5E9), // sky-500
            modifier = Modifier.padding(bottom = 12.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F2937), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "En Level-Up Gamer, nos guiamos por los siguientes valores:",
                    fontSize = 16.sp,
                    color = Color(0xFFD1D5DB),
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                val valores = listOf(
                    "Innovación y tecnología",
                    "Experiencia del cliente",
                    "Compromiso a la comunidad gamer",
                    "Calidad y autenticidad",
                    "Gamificación y fidelización"
                )
                valores.forEach { valor ->
                    Text(
                        text = "• $valor",
                        fontSize = 16.sp,
                        color = Color(0xFFD1D5DB),
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
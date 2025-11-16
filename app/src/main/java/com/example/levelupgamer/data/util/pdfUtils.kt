package com.example.levelupgamer.data.util

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.levelupgamer.model.CartItem
import java.io.File
import java.io.FileOutputStream

fun generarComprobantePDF(
    context: Context,
    cartItems: List<CartItem>,
    ubicacion: String = "Ubicación no disponible",
    nombreArchivo: String = "comprobante.pdf"
): File {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(500, 800, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    val paint = android.graphics.Paint()
    paint.textSize = 16f

    var yPosition = 50f
    canvas.drawText("COMPROBANTE DE COMPRA", 150f, yPosition, paint)
    yPosition += 30f

    cartItems.forEach { item ->
        val subtotal = item.product.precio * item.cantidad
        val line = "${item.product.nombre} x${item.cantidad} - $${subtotal}"
        canvas.drawText(line, 20f, yPosition, paint)
        yPosition += 25f
    }

    yPosition += 20f
    val total = cartItems.sumOf { it.product.precio * it.cantidad }
    canvas.drawText("TOTAL: $${total}", 20f, yPosition, paint)

    yPosition += 30f
    canvas.drawText("Ubicación: $ubicacion", 20f, yPosition, paint)

    pdfDocument.finishPage(page)

    val directorio = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val archivo = File(directorio, nombreArchivo)
    pdfDocument.writeTo(FileOutputStream(archivo))
    pdfDocument.close()

    return archivo
}

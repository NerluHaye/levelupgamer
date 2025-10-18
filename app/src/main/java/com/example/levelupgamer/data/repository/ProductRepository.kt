package com.example.levelupgamer.data.repository

import com.example.levelupgamer.model.Product

class ProductRepository {

    private val products = listOf(
        Product(1, "Logitech G733 Wireless", 159990, categoria = "", descripcion = "", imageUrl = ""),
        Product(2, "Control DualSense PS5", 52990, categoria = "", descripcion = "", imageUrl = ""),
        Product(3, "Estuche Nintendo Switch OLED", 19990, categoria = "", descripcion = "", imageUrl = ""),
        Product(4, "Asus ROG Strix G18", 2229990, categoria = "", descripcion = "", imageUrl = ""),
        Product(5, "Acer Nitro AN515", 1099990, categoria = "", descripcion = "", imageUrl = ""),
        Product(6, "PC Gamer R5 RTX 4060", 1199990, categoria = "", descripcion = "", imageUrl = ""),
        Product(7, "PlayStation 5 Slim", 536990, categoria = "", descripcion = "", imageUrl = ""),
        Product(8, "Xbox Series S", 419990, categoria = "", descripcion = "", imageUrl = ""),
        Product(9, "Nintendo Switch 2", 589990, categoria = "", descripcion = "", imageUrl = ""),
        Product(10, "Trust GXT 707R Resto", 139990, categoria = "", descripcion = "", imageUrl = ""),
        Product(11, "Kronos Hunter Pro", 119990, categoria = "", descripcion = "", imageUrl = ""),
        Product(12, "Cougar Defensor", 189990, categoria = "", descripcion = "", imageUrl = ""),
        Product(13, "Logitech G502", 79990, categoria = "", descripcion = "", imageUrl = ""),
        Product(14, "Razer DeathAdder V2", 89990, categoria = "", descripcion = "", imageUrl = ""),
        Product(15, "Logitech G413", 99990, categoria = "", descripcion = "", imageUrl = ""),
        Product(16, "HyperX Fury S Pro XL", 29990, categoria = "", descripcion = "", imageUrl = ""),
        Product(17, "Razer Gigantus V2", 29990, categoria = "", descripcion = "", imageUrl = ""),
        Product(18, "Logitech G640", 29990, categoria = "", descripcion = "", imageUrl = ""),
        Product(19, "Polera HALO", 19990, categoria = "", descripcion = "", imageUrl = ""),
        Product(20, "Polera God Of War", 19990, categoria = "", descripcion = "", imageUrl = ""),
        Product(21, "Poleron Legend of Zelda", 39990, categoria = "", descripcion = "", imageUrl = ""),
        Product(22, "Warhammer 40M", 89990, categoria = "", descripcion = "", imageUrl = ""),
        Product(23, "Ajedrez", 24990, categoria = "", descripcion = "", imageUrl = ""),
        Product(24, "Shogi", 59990, categoria = "", descripcion = "", imageUrl = "")
    )

    fun getAllProducts(): List<Product> = products

    fun getProductById(id: Int): Product? = products.find { it.id == id }
}
package com.example.levelupgamer.data.repository

import com.example.levelupgamer.model.CartItem
import com.example.levelupgamer.model.Product

import com.example.levelupgamer.R


class ProductRepository {

    private val products = listOf(
        Product(1, "Logitech G733 Wireless", 159990, "Auriculares", "Auriculares inalámbricos para juegos LIGHTSPEED con LIGHTSYNC RGB", R.drawable.logitech_g733),
        Product(2, "Control DualSense PS5", 52990, "Consolas", "Control inalámbrico DualSense para PS5", R.drawable.dualsense_ps5),
        Product(3, "Estuche Nintendo Switch OLED", 19990, "Accesorios", "Estuche protector para Nintendo Switch OLED", R.drawable.nintendo_switch_case),
        Product(4, "Asus ROG Strix G18", 2229990, "Notebooks", "Intel Core i9-13650HX / NVIDIA GeForce RTX 4070 12GB / 32GB RAM / 1TB SSD", R.drawable.asus_rog_g18),
        Product(5, "Acer Nitro AN515", 1099990, "Notebooks", "Ryzen 7 8845HS/ NVIDIA® GeForce® RTX 3060 6GB / 16GB RAM / 512GB SSD / Pantalla 15,6\" Full HD @144Hz", R.drawable.acer_nitro),
        Product(6, "PC Gamer R5 RTX 4060", 1199990, "PCs", "Ryzen 5 8400f / NVIDIA GeForce RTX 4060 / 16GB RAM / 512GB SSD / refrigeración líquida / gabinete ATX", R.drawable.pc_gamer_r5),
        Product(7, "PlayStation 5 Slim", 536990, "Consolas", "Consola PlayStation 5 Slim 825GB", R.drawable.ps5_slim),
        Product(8, "Xbox Series S", 419990, "Consolas", "Consola Xbox Series S 512GB", R.drawable.xbox_series_s),
        Product(9, "Nintendo Switch 2", 589990, "Consolas", "Consola Nintendo Switch 2 / 256GB / 12 RAM / pantalla tactil", R.drawable.nintendo_switch2),
        Product(10, "Trust GXT 707R Resto", 139990, "Sillas", "Silla Gamer Trust GXT 707R Resto Negra y Roja", R.drawable.trust_gxt707),
        Product(11, "Kronos Hunter Pro", 119990, "Sillas", "Silla Gamer Kronos Hunter Pro Negra y Azul", R.drawable.kronos_hunter_pro),
        Product(12, "Cougar Defensor", 189990, "Sillas", "Silla Gamer Cougar Defensor Negra y Naranja", R.drawable.cougar_defensor),
        Product(13, "Logitech G502", 79990, "Mouse", "Mouse Gamer Logitech G502 Hero", R.drawable.logitech_g502),
        Product(14, "Razer DeathAdder V2", 89990, "Mouse", "Mouse Gamer Razer DeathAdder V2 Hyperspeed", R.drawable.razer_deathadder_v2),
        Product(15, "Logitech G413", 99990, "Teclados", "Teclado Gamer Logitech G413 Mechanical", R.drawable.logitech_g413),
        Product(16, "HyperX Fury S Pro XL", 29990, "Alfombrillas", "Mousepad Gamer HyperX Fury S Pro XL", R.drawable.hyperx_fury_s),
        Product(17, "Razer Gigantus V2", 29990, "Alfombrillas", "Mousepad Gamer Razer Gigantus V2", R.drawable.razer_gigantus_v2),
        Product(18, "Logitech G640", 29990, "Alfombrillas", "Mousepad Gamer Logitech G640", R.drawable.logitech_g640),
        Product(19, "Polera HALO", 19990, "Ropa", "Polera de HALO Talla M/L/XL", R.drawable.polera_halo),
        Product(20, "Polera God Of War", 19990, "Ropa", "Polera de God Of War Talla M/L/XL", R.drawable.polera_gow),
        Product(21, "Poleron Legend of Zelda", 39990, "Ropa", "Poleron de Legend of Zelda Talla M/L/XL", R.drawable.poleron_zelda),
        Product(22, "Warhammer 40M", 89990, "Juegos", "Juego de mesa Warhammer 40,000", R.drawable.warhammer_40m),
        Product(23, "Ajedrez", 24990, "Juegos", "Juego de mesa Ajedrez", R.drawable.ajedrez),
        Product(24, "Shogi", 59990, "Juegos", "Juego de mesa Japones Shogi", R.drawable.shogi)
    )

    // Carrito en memoria simple
    private val cart = mutableListOf<CartItem>()

    fun getProducts(): List<Product> = products

    fun getProductById(id: Int): Product? = products.find { it.id == id }

    fun getCart(): List<CartItem> = cart.map { it.copy() }

    fun addToCart(product: Product, cantidad: Int = 1) {
        val existing = cart.find { it.product.id == product.id }
        if (existing != null) {
            existing.cantidad += cantidad
        } else {
            cart.add(CartItem(product, cantidad))
        }
    }

    fun removeFromCart(product: Product) {
        val existing = cart.find { it.product.id == product.id }
        if (existing != null) {
            cart.remove(existing)
        }
    }

    fun clearCart() {
        cart.clear()
    }
}
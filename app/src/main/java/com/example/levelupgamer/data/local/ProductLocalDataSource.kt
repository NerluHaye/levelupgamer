package com.example.levelupgamer.data.local

import com.example.levelupgamer.data.local.dao.ProductDao
import com.example.levelupgamer.data.local.entity.ProductEntity

class ProductLocalDataSource(private val productDao: ProductDao) {

    // Obtener todos los productos desde la DB
    suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts()
    }

    // Insertar varios productos
    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertAll(products)
    }

    // Insertar un solo producto
    suspend fun insertProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    // Borrar un producto
    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    // Borrar todos los productos
    suspend fun deleteAll() {
        productDao.deleteAll()
    }
}
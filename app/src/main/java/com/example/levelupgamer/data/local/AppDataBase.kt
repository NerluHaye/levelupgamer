package com.example.levelupgamer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.levelupgamer.data.local.dao.ProductDao
import com.example.levelupgamer.data.local.dao.UserDao
import com.example.levelupgamer.data.local.entity.ProductEntity
import com.example.levelupgamer.data.local.entity.UserEntity

@Database(
    entities = [ProductEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
}
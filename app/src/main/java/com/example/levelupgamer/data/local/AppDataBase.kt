package com.example.levelupgamer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna la instancia existente o la crea si no existe
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelupgamer_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
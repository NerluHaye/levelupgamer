package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.local.dao.UserDao
import com.example.levelupgamer.data.local.entity.UserEntity
import com.example.levelupgamer.data.util.Result
import com.example.levelupgamer.model.User

class AuthRepository(private val userDao: UserDao) {

    suspend fun register(user: User): Result<Boolean> {
        val existingUser = userDao.getUserByEmail(user.email)
        return if (existingUser != null) {
            Result.Error("El correo ya está registrado.")
        } else {
            val entity = UserEntity(
                username = user.username,
                email = user.email,
                password = user.password
            )
            userDao.registerUser(entity)
            Result.Success(true)
        }
    }

    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.login(email, password)
        return if (user != null) {
            Result.Success(user)
        } else {
            Result.Error("Correo o contraseña incorrectos.")
        }
    }
}
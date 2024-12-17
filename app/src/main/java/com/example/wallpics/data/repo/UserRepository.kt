package com.example.wallpics.data.repo

import com.example.wallpics.data.User
import com.example.wallpics.data.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun authenticateUser(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }

    suspend fun registerUser(user: User) {
        userDao.insert(user)
    }
}

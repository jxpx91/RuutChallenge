package com.jp.ruutchallenge.data.room

import com.jp.ruutchallenge.data.room.dao.UserDao
import com.jp.ruutchallenge.data.room.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val dao: UserDao
) {
    fun getAllUsers(): MutableList<UserEntity> {
        return dao.getAllUsers()
    }

    fun getUserById(id: String): UserEntity {
        return dao.getUserById(id)
    }

    suspend fun insertNewUser(id: String, name: String, email: String, pass: String) {
        dao.insertTask(UserEntity(id, name, email, pass))
    }
}
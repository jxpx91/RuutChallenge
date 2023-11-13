package com.jp.ruutchallenge.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jp.ruutchallenge.data.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_entity")
    fun getAllUsers(): MutableList<UserEntity>

    @Query("SELECT * FROM user_entity WHERE id = :id")
    fun getUserById(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(user : UserEntity)
}
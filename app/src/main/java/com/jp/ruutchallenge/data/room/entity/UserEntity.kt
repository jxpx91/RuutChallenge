package com.jp.ruutchallenge.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var name: String,
    var email: String,
    var pass: String,
) {
    companion object {
        val EMPTY = UserEntity("", "", "", "")
    }
}

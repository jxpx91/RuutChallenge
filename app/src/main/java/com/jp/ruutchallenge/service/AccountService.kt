package com.jp.ruutchallenge.service

import com.jp.ruutchallenge.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<User?>
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Pair<Boolean, String>
    suspend fun signOut()
}
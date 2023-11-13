package com.jp.ruutchallenge.service.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jp.ruutchallenge.model.User
import com.jp.ruutchallenge.service.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(): AccountService {
    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) })
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override fun getId(): String {
        return Firebase.auth.currentUser?.uid ?: "Not id"
    }

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e("Firebase", e.message.toString())
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Pair<Boolean, String> {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Pair(true, "")
        } catch (e: Exception) {
            Log.e("Firebase", e.message.toString())
            Pair(false, e.message.toString())
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }
}
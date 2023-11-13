package com.jp.ruutchallenge.ui.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.ruutchallenge.data.room.UserRepository
import com.jp.ruutchallenge.data.room.entity.UserEntity
import com.jp.ruutchallenge.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userRepository: UserRepository,
): ViewModel() {

    val users = MutableStateFlow(mutableListOf<UserEntity>())
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    fun onSignInClick(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.emit(true)
            val signed = accountService.signIn(email, pass)
            if (signed) {
                onSuccess()
            } else {
                errorMessage.emit("Incorrect credentials")
            }
            isLoading.emit(false)
        }
    }

    fun getAllLinkedUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            users.emit(userRepository.getAllUsers())
        }
    }
}
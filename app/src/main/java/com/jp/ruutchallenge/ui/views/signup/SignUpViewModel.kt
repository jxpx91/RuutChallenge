package com.jp.ruutchallenge.ui.views.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.ruutchallenge.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
): ViewModel() {

    val errorMessage = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)

    fun signUp(name: String, email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.emit(true)
            val result = accountService.signUp(email = email, password = pass)
            if (result.first) {
                accountService.currentUser.collect { user ->
                    Log.d("Sign up", "${user?.id}")
                    if (user != null) {
                        onSuccess()
                    }
                }
            } else {
                errorMessage.emit("An error occurred: ${result.second}")
                // clean up the message
                delay(400)
                errorMessage.emit("")
            }
            isLoading.emit(false)
        }
    }

}
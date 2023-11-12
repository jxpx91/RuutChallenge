package com.jp.ruutchallenge.ui.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.ruutchallenge.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
): ViewModel() {

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
}
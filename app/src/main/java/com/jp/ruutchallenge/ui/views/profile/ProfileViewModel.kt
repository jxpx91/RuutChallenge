package com.jp.ruutchallenge.ui.views.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.ruutchallenge.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
): ViewModel() {

    fun signOut(onSuccess: () -> Unit) {
        viewModelScope.launch {
            accountService.signOut()
            onSuccess()
        }
    }

}
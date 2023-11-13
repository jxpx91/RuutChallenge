package com.jp.ruutchallenge.ui.views.profile

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
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userRepository: UserRepository,
): ViewModel() {

    val userInfo = MutableStateFlow(UserEntity.EMPTY)

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (accountService.hasUser()) {
                userInfo.emit(userRepository.getUserById(
                    accountService.getId()
                ))
            }
        }
    }

    fun signOut(onSuccess: () -> Unit) {
        viewModelScope.launch {
            accountService.signOut()
            onSuccess()
        }
    }

}
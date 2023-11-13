package com.jp.ruutchallenge.ui.views

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel() {

    lateinit var navController: NavController

    fun navigateToSignUp() {
        navController.navigate(Screens.SIGN_UP)
    }

    fun navigateToMain() {
        navController.navigate(Screens.MAIN)
    }

    fun navigateToProfile() {
        navController.navigate(Screens.PROFILE)
    }

    fun navigateToLogin() {
        navController.navigate(Screens.LOGIN)
    }
}
package com.jp.ruutchallenge.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jp.ruutchallenge.ui.views.login.LoginView
import com.jp.ruutchallenge.ui.views.main.MainView
import com.jp.ruutchallenge.ui.views.profile.ProfileView
import com.jp.ruutchallenge.ui.views.signup.SignUpView

object Screens {
    val LOGIN = "login"
    val SIGN_UP = "sign_up"
    val MAIN = "main"
    val PROFILE = "profile"
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN,
        modifier = Modifier,
    ) {
        composable(route = Screens.LOGIN) {
            LoginView()
        }
        composable(route = Screens.SIGN_UP) {
            SignUpView()
        }
        composable(route = Screens.MAIN) {
            MainView()
        }
        composable(route = Screens.PROFILE) {
            ProfileView()
        }
    }
}
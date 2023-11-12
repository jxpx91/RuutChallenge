package com.jp.ruutchallenge.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
    val viewModel: MainScreenViewModel = hiltViewModel()
    val navController = rememberNavController()
    viewModel.navController = navController

    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN,
        modifier = Modifier,
    ) {
        composable(route = Screens.LOGIN) {
            LoginView(
                goToMain = { viewModel.navigateToMain() },
                goToSignUp = { viewModel.navigateToSignUp() }
            )
        }
        composable(route = Screens.SIGN_UP) {
            SignUpView(
                goToMain = { viewModel.navigateToMain() },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = Screens.MAIN) {
            MainView(
                goToProfile = { viewModel.navigateToProfile() }
            )
        }
        composable(route = Screens.PROFILE) {
            ProfileView(
                goToLogin = { viewModel.navigateToLogin() },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}
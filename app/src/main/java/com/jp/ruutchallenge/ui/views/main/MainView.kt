package com.jp.ruutchallenge.ui.views.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.ruutchallenge.R
import com.jp.ruutchallenge.ui.views.comun.TopBarAV

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    goToProfile: () -> Unit,
) {
    val viewModel: MainViewModel = hiltViewModel()
    viewModel.getData()

    Scaffold(
        topBar = {
            TopBarAV(
                title = stringResource(id = R.string.alpha_vantage),
                showProfileAction = true,
                onProfileActionClick = {
                    goToProfile()
                }
            )
        },
        content = { padding ->
            Text(text = "Main screen")
        }
    )
}
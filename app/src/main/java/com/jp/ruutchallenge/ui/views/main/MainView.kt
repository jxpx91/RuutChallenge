package com.jp.ruutchallenge.ui.views.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainView() {
    val viewModel: MainViewModel = hiltViewModel()
    viewModel.getData()
}
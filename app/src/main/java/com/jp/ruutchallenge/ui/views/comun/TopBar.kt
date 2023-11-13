package com.jp.ruutchallenge.ui.views.comun

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.jp.ruutchallenge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAV(
    title: String,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = { },
    showProfileAction: Boolean = false,
    onProfileActionClick: () -> Unit = { },
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = { onBackButtonClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (showProfileAction) {
                IconButton(
                    onClick = { onProfileActionClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = null
                    )
                }
            }
        }
    )
}
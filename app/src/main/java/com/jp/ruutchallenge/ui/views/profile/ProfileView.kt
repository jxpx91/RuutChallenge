package com.jp.ruutchallenge.ui.views.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.ruutchallenge.R
import com.jp.ruutchallenge.ui.views.comun.TopBarAV

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    goToLogin: () -> Unit,
    navigateUp: () -> Unit,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopBarAV(
                title = stringResource(id = R.string.profile),
                showBackButton = true,
                onBackButtonClick = {
                    navigateUp()
                }
            )
        },
        content = { padding ->
            ProfileInfo {
                viewModel.signOut(goToLogin)
            }
        }
    )
}

@Composable
private fun ProfileInfo(
    onSignOut: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(0.2f),
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "User name",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.red_400),
                contentColor = colorResource(id = R.color.white)),
            onClick = { onSignOut() },
        ) {
            Text(text = stringResource(id = R.string.signout))
        }
    }
}
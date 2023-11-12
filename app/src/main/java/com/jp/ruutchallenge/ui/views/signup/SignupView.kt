package com.jp.ruutchallenge.ui.views.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.ruutchallenge.R
import com.jp.ruutchallenge.ui.views.comun.PasswordField
import com.jp.ruutchallenge.ui.views.comun.TopBarAV

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView(
    goToMain: () -> Unit,
    navigateUp: () -> Unit,
) {
    val viewModel: SignUpViewModel = hiltViewModel()
    var nameText by rememberSaveable {
        mutableStateOf("")
    }
    var usernameText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopBarAV(
                title = stringResource(id = R.string.signup_title),
                showBackButton = true,
                onBackButtonClick = {
                    navigateUp()
                }
            )
        },
        content = { padding ->
            SignUpForm(
                nameText = nameText,
                usernameText = usernameText,
                passwordText = passwordText,
                onNameChange = { newText ->
                    nameText = newText
                },
                onUsernameChange = { newText ->
                    usernameText = newText
                },
                onPassChange = { newText ->
                    passwordText = newText
                },
                onGo = {
                    goToMain()
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                enabled = nameText.isNotEmpty() && usernameText.isNotEmpty() && passwordText.isNotEmpty(),
                onClick = {
                    goToMain()
            }) {
                Text(text = stringResource(id = R.string.create_account))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpForm(
    nameText: String,
    onNameChange: (String) -> Unit,
    usernameText: String,
    onUsernameChange: (String) -> Unit,
    passwordText: String,
    onPassChange: (String) -> Unit,
    onGo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.signup_subtitle)
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = nameText,
            singleLine = true,
            onValueChange = { newText ->
                onNameChange(newText)
            },
            label = { Text(text = stringResource(id = R.string.name_placeholder)) },
            placeholder = { Text(text = stringResource(id = R.string.name_placeholder)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = usernameText,
            singleLine = true,
            onValueChange = { newText ->
                onUsernameChange(newText)
            },
            label = { Text(text = stringResource(id = R.string.user_placeholder)) },
            placeholder = { Text(text = stringResource(id = R.string.user_placeholder)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            passText = passwordText,
            onValueChange = { newText ->
                onPassChange(newText)
            },
            onGo = {
                onGo()
            },
            passwordVisibleInit = true
        )
    }
}
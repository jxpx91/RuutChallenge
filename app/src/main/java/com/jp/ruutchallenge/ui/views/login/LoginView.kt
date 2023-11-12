package com.jp.ruutchallenge.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.ruutchallenge.R
import com.jp.ruutchallenge.ui.views.comun.PasswordField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    goToMain: () -> Unit,
    goToSignUp: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.height(48.dp))

                LoginForm(
                    onLoginClick = goToMain
                )
            }
        },
        bottomBar = {
            SignUp(
                onSignUpClick = goToSignUp
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginForm(
    onLoginClick: () -> Unit
) {
    var userText by rememberSaveable {
        mutableStateOf("")
    }
    var passText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = userText,
        singleLine = true,
        onValueChange = { newText ->
            userText = newText
        },
        label = { Text(text = stringResource(id = R.string.user_placeholder)) },
        placeholder = { Text(text = stringResource(id = R.string.user_placeholder)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
    Spacer(modifier = Modifier.height(16.dp))
    PasswordField(
        passText = passText,
        onValueChange = { newText ->
            passText = newText
        },
        onGo = {
            onLoginClick()
        },
    )
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        enabled = userText.isNotEmpty() && passText.isNotEmpty(),
        onClick = { onLoginClick() }
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Composable
private fun SignUp(
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.signup_helper)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = colorResource(id = R.color.purple_500),
                disabledContainerColor = Color.Transparent),
            onClick = { onSignUpClick() },
        ) {
            Text(text = stringResource(id = R.string.signup))
        }
    }
}
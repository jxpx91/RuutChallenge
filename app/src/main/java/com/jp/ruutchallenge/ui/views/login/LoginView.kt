package com.jp.ruutchallenge.ui.views.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.ruutchallenge.R
import com.jp.ruutchallenge.ui.views.comun.PasswordField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginView(
    goToMain: () -> Unit,
    goToSignUp: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var userText by rememberSaveable {
        mutableStateOf("")
    }
    var passText by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
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
                    userText = userText,
                    passText = passText,
                    onUserTextChange = {
                       userText = it
                    },
                    onPassTextChange = {
                       passText = it
                    },
                    onLoginClick = { viewModel.onSignInClick(
                        email = userText,
                        pass = passText,
                    ){
                        keyboardController?.hide()
                        goToMain()
                    }}
                )
            }
        },
        bottomBar = {
            SignUp(
                onSignUpClick = goToSignUp
            )
        }
    )

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    AnimatedVisibility(visible = isLoading) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_transparent)),
            contentAlignment = Alignment.Center,
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginForm(
    userText: String,
    onUserTextChange: (String) -> Unit,
    passText: String,
    onPassTextChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = userText,
        singleLine = true,
        onValueChange = { newText ->
            onUserTextChange(newText)
        },
        label = { Text(text = stringResource(id = R.string.user_placeholder)) },
        placeholder = { Text(text = stringResource(id = R.string.user_placeholder)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
    Spacer(modifier = Modifier.height(16.dp))
    PasswordField(
        passText = passText,
        onValueChange = { newText ->
            onPassTextChange(newText)
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
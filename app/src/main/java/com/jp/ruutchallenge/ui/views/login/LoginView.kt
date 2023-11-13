package com.jp.ruutchallenge.ui.views.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.jp.ruutchallenge.data.room.entity.UserEntity
import com.jp.ruutchallenge.ui.views.comun.PasswordField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginView(
    goToMain: () -> Unit,
    goToSignUp: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val users by viewModel.users.collectAsState()
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
    var showQuickList by rememberSaveable {
        mutableStateOf(false)
    }
    viewModel.getAllLinkedUsers()

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
                    usersLinked = users,
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
                    }},
                    onQuickLinkClick = {
                        showQuickList = true
                    }
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
            .background(colorResource(id = R.color.black_transparent)),
            contentAlignment = Alignment.Center,
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }

    AnimatedVisibility(visible = showQuickList) {
        QuickLink(
            users = users,
            onUserClick = {
                viewModel.onSignInClick(
                    email = it.email,
                    pass = it.pass,
                ){
                    keyboardController?.hide()
                    goToMain()
                }
                showQuickList = false
            },
            onCloseClick = {
                showQuickList = false
            },)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginForm(
    usersLinked: List<UserEntity>,
    userText: String,
    onUserTextChange: (String) -> Unit,
    passText: String,
    onPassTextChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onQuickLinkClick: () -> Unit,
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
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        enabled = usersLinked.isNotEmpty(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.purple_500),
            disabledContainerColor = Color.Transparent),
        onClick = { onQuickLinkClick() }
    ) {
        Text(text = stringResource(id = R.string.quick_login))
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

@Composable
private fun QuickLink(
    users: List<UserEntity>,
    onUserClick: (UserEntity) -> Unit,
    onCloseClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.black_transparent))
        .padding(horizontal = 32.dp)
        .clickable(true) {},
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier
            .background(colorResource(id = R.color.white))
            .padding(16.dp)
        ) {
            Column {
                Text(text = stringResource(id = R.string.quick_title))
                Spacer(modifier = Modifier.height(32.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(users) { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .clickable { onUserClick(user) }
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth(0.2f),
                                painter = painterResource(id = R.drawable.ic_user),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                            )
                            Column {
                                Text(text = user.name)
                                Text(text = user.email)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    Button(
                        onClick = onCloseClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = colorResource(id = R.color.purple_500),
                            disabledContainerColor = Color.Transparent),
                    ) {
                        Text(
                            text = stringResource(id = R.string.close)
                        )
                    }
                }
            }
        }
    }
}
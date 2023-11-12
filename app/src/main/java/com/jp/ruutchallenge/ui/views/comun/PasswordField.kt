package com.jp.ruutchallenge.ui.views.comun

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.jp.ruutchallenge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    passText: String,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit,
    passwordVisibleInit: Boolean = false,
) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(passwordVisibleInit)
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = passText,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { newText ->
            onValueChange(newText)
        },
        label = { Text(text = stringResource(id = R.string.password_placeholder)) },
        placeholder = { Text(text = stringResource(id = R.string.password_placeholder)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(onGo = { onGo() }),
        trailingIcon = {
            val image = if (passwordVisible)
                painterResource(id = R.drawable.ic_show_password)
            else painterResource(id = R.drawable.ic_hide_password)

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(painter  = image, null)
            }
        }
    )
}
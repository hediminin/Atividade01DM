package com.example.atividade01dm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividade01dm.R
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    navController: NavController
) {
    val authViewModel = viewModel<AuthViewModel>()
    val loginState by authViewModel.loginResponseBody
    val autenticado by authViewModel.autenticado

    var usuario by remember { mutableStateOf("alx.delira@gmail.com") }
    var senha by remember { mutableStateOf("12345678") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .size(150.dp)
        )

        when(loginState) {
            is ApiState.Created -> {}
            is ApiState.Loading -> {}
            is ApiState.Success -> {}
            is ApiState.Error -> {
                loginState.message?.let { message ->
                    Text(
                        message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    )
                }
            }
        }

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuário") },
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    val id = if (passwordVisibility) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = null
                    )
                }
            },
        )

        Button(
            onClick = {
                authViewModel.login(
                    usuario,
                    senha,
                    onComplete = {
                        navController.navigate("dashboard")
                    }
                )
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Entrar",
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }

    if (loginState is ApiState.Loading) {
        LoadScreen()
    }

    if (autenticado) {
        navController.navigate("dashboard")
    }
}

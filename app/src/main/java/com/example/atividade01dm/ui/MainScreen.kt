package com.example.atividade01dm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atividade01dm.viewmodel.AuthViewModel

@Composable
fun MainScreen() {
    val authViewModel = viewModel<AuthViewModel>()

    Column {
        Text("Início")
        Button(onClick = {
            authViewModel.login()
        }) {
            Text("Login")
        }
    }
}

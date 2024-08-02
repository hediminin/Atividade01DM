package com.example.atividade01dm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuariosScreen(
    navController: NavController
) {
    val usuarioViewModel = viewModel<UsuarioViewModel>()
    val usuariosState by usuarioViewModel.usuariosResponseBody

    Scaffold(
        topBar = {
            AppTopAppBar(
                navController,
                "Usuários"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (usuariosState) {
                is ApiState.Created -> {}
                is ApiState.Loading -> {}
                is ApiState.Success -> {
                    usuariosState.data?.let { responseData ->
                        if (responseData.docs.isEmpty()) {
                            Text("Nenhum usuário")
                        } else {
                            LazyColumn {
                                items(
                                    items = responseData.docs,
                                    key = { usuario -> usuario._id }
                                ) { usuario ->
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                 navController.navigate("usuario")
                                            }
                                            .padding(20.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            usuario.nome,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is ApiState.Error -> {
                    usuariosState.message?.let { message ->
                        FailScreen(message)
                    }
                }
            }
        }

        if (usuariosState is ApiState.Loading) {
            LoadScreen()
        }
    }

    LaunchedEffect(Unit) {
        usuarioViewModel.getUsuarios()
    }
}
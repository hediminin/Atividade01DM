package com.example.atividade01dm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    var searchText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            AppTopAppBar(
                navController,
                "Usuários"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
            ) {
                Icon(Icons.Filled.Add, "Adicionar usuário")
            }
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
                            SearchBar(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                query = searchText,
                                onQueryChange = {
                                    searchText = it
                                },
                                onSearch = {},
                                active = false,
                                onActiveChange = {},
                                placeholder = { Text("Buscar usuário") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },

                            ) {

                            }

                            LazyColumn {
                                items(
                                    items = responseData.docs.filter {
                                        it.nome.contains(searchText, ignoreCase = true)
                                    },
                                    key = { usuario -> usuario._id }
                                ) { usuario ->
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                navController.navigate("usuario/${usuario._id}")
                                            }
                                            .padding(20.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Row {
                                            FotoPerfil(
                                                foto = usuario.foto,
                                                56.dp
                                            )

                                            Column(
                                                modifier = Modifier
                                                    .padding(start = 16.dp)
                                            ) {
                                                Text(
                                                    usuario.nome,
                                                )
                                                Text(
                                                    usuario.email,
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
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
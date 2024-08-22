package com.example.atividade01dm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividade01dm.R
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.ui.theme.surfaceContainerLight
import com.example.atividade01dm.viewmodel.AuthViewModel
import com.example.atividade01dm.viewmodel.DashBoardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController
) {
    val dashBoardViewModel = viewModel<DashBoardViewModel>()
    val dashboardState by dashBoardViewModel.dashboardResponseBody
    val perfilLocal by dashBoardViewModel.perfilLocal

    val authViewModel = viewModel<AuthViewModel>()
    val alertVisible = remember { mutableStateOf(false)  }

    Scaffold(
        topBar = {
            AppTopAppBar(
                navController,
                "Início",
                actions = {
                    IconButton(
                        onClick = {
                            alertVisible.value = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "Sair"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clickable { }
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    FotoPerfil(
                        foto = perfilLocal.foto,
                        50.dp
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                    ) {
                        Text(perfilLocal.nome)
                        Text(
                            perfilLocal.email,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("usuarios")
                        }
                        .background(
                            color = surfaceContainerLight,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    when (dashboardState) {
                        is ApiState.Created -> {}
                        is ApiState.Loading -> {
                            Text("Carregando...")
                        }
                        is ApiState.Success -> {
                            dashboardState.data?.let { responseData ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        if (responseData.totalUsuarios > 0) {
                                            Text(
                                                responseData.totalUsuarios.toString(),
                                                style = MaterialTheme.typography.displaySmall
                                            )
                                            Text(
                                                "usuários",
                                                color = Color.Gray
                                            )
                                        } else {
                                            Text(
                                                "Nenhum usuário",
                                                color = Color.Gray
                                            )
                                        }
                                    }

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_arrow_right),
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }

                        is ApiState.Error -> {
                            Text("Não foi possível carregar.")
                        }
                    }
                }
            }
        }

        if (dashboardState is ApiState.Loading) {
            LoadScreen()
        }
    }

    if (alertVisible.value) {
        AlertDialog(
            onDismissRequest = {
                alertVisible.value = false
            },
            title = {
                Text(text = "Deseja realmente sair?")
            },
            text = {
                Text("Não será possível acessar informações e receber notificações pessoais.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        authViewModel.logout(onComplete = {
                            navController.navigate("auth")
                        })
                        alertVisible.value = false
                    }
                ) {
                    Text("Sair")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        alertVisible.value = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        dashBoardViewModel.getDashboard()
    }
}
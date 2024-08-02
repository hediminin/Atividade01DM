package com.example.atividade01dm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atividade01dm.ui.AuthScreen
import com.example.atividade01dm.ui.UsuarioScreen
import com.example.atividade01dm.ui.UsuariosScreen
import com.example.atividade01dm.ui.theme.Atividade01DMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Atividade01DMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "inicio"
                    ) {
                        composable("inicio") {
                            AuthScreen(navController)
                        }
                        composable("usuarios") {
                            UsuariosScreen(navController)
                        }
                        composable("usuario") {
                            UsuarioScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
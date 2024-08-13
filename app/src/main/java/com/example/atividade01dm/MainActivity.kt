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
import com.example.atividade01dm.ui.DashboardScreen
import com.example.atividade01dm.ui.UsuarioEditaScreen
import com.example.atividade01dm.ui.UsuariosScreen
import com.example.atividade01dm.ui.theme.Atividade01DMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)

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
                        startDestination = "auth"
                    ) {
                        composable("auth") {
                            AuthScreen(navController)
                        }
                        composable("usuarios") {
                            UsuariosScreen(navController)
                        }
                        composable("usuario/{id}") { navBackStackEntry ->
                            val aId = navBackStackEntry.arguments?.getString("id")
                            aId?.let { id ->
                                UsuarioEditaScreen(navController, id)
                            }
                        }
                        composable("dashboard") {
                            DashboardScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
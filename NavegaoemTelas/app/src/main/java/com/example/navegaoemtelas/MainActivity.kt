package com.example.navegaoemtelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navegaoemtelas.ui.theme.NavegaçãoEmTelasTheme
import com.example.navegaoemtelas.view.Tela01
import com.example.navegaoemtelas.view.Tela02

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegaçãoEmTelasTheme {
                val navController = rememberNavController()

                // Configuração do NavHost
                NavHost(
                    navController = navController,
                    startDestination = "Tela01"
                ) {
                    // Composable para a tela 01
                    composable(
                        route = "Tela01"
                    ) {
                        Tela01(navController)
                    }

                    // Composable para a tela 02
                    composable(
                        route = "Tela02"
                    ) {
                        Tela02(navController)
                    }
                }
            }
        }
    }
}

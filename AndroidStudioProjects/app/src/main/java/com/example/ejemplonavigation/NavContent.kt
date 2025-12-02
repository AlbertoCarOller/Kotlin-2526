package com.example.ejemplonavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavContent(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "PrincipalScreen") {
        composable("PrincipalScreen") {
            PrincipalScreen(modifer = modifier) { navController.navigate("Details/123") }
        }
        // Para pasar argumentos se utiliza backStackEntry entre pantallas
        composable("Details/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item") ?: ""
            // popBackStack -> Para volver a la pantalla anterior como el pop en flutter
            DetailsScreen(modifier = modifier, item = item) { navController.popBackStack() }
        }
    }
}
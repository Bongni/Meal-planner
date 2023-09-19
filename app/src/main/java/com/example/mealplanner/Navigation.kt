package com.example.mealplanner

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    var recipes = LinkedHashMap<String, String>()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, recipes = recipes)
        }

        composable(
            route = Screen.RecipeScreen.route + "/{title}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {entry ->
            val title = entry.arguments?.getString("title")!!

            Recipe(
                title = title,
                ingredients = recipes[title]!!,
                navController = navController
            )
        }
        
        composable(route = Screen.AddScreen.route) {
            AddScreen(navController = navController, recipes = recipes)
        }
    }
}

@Composable
fun HomeButton (navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Screen.MainScreen.route)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Home")
    }
}
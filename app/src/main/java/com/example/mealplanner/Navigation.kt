package com.example.mealplanner

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(recipes : ArrayList<Recipe>, ingredients: ArrayList<Ingredient>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ShoppingListScreen.route) {
        composable(route = Screen.ShoppingListScreen.route) {
            Scaffold (
                topBar = {
                    TopNavBar(navController = navController)
                },
                content = {innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        ShoppingListScreen(ingredients = ingredients)
                    }
                }
            )
        }

        composable(route = Screen.RecipeListScreen.route) {
            Scaffold (
                topBar = {
                    TopNavBar(navController = navController)
                },
                content = {innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        RecipeListScreen(navController = navController, recipes = recipes, ingredients = ingredients)
                    }
                }
            )
        }

        composable(
            route = Screen.RecipeScreen.route + "/{title}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { entry ->
            val title = entry.arguments?.getString("title")!!

            Scaffold (
                topBar = {
                    TopNavBarSmall(navController = navController, title = "Recipe")
                },
                content = {innerPadding ->
                    Box (
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        RecipeScreen(
                            title = title,
                            ingredients = "some"
                        )
                    }
                }
            )
        }

        composable(route = Screen.AddScreen.route) {
            Scaffold (
                topBar = {
                    TopNavBarSmall(navController = navController, title = "Add Recipe")
                },
                content = {innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        AddScreen(navController = navController, recipes = recipes)
                    }
                }
            )
        }
    }
}
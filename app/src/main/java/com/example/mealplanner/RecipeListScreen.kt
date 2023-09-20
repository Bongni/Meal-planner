package com.example.mealplanner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RecipeListScreen(navController: NavController, recipes: ArrayList<Recipe>, chosenRecipes: ArrayList<Recipe>) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .padding(vertical = 20.dp)
        ) {
            recipes.forEach { recipe ->
                RecipeBox(recipe = recipe, recipes = recipes, chosenRecipes = chosenRecipes, navController = navController)
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.AddScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text("add")
        }
    }
}

@Composable
fun RecipeBox (recipe: Recipe, recipes: ArrayList<Recipe>, chosenRecipes: ArrayList<Recipe>, navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 10.dp)
            .padding(vertical = 5.dp)
            .clickable {
                chosenRecipes.add(recipe)
            }
    ){
        RecipeField(name = recipe.name, navController = navController)
        DeleteButton(recipe = recipe, recipes = recipes, navController = navController)
    }
}

@Composable
fun RecipeField (name : String, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Screen.RecipeScreen.withArgs(name))
        },
        modifier = Modifier.width(200.dp)
    ) {
        Text(text = name)
    }
}

@Composable
fun DeleteButton (recipe: Recipe, recipes: ArrayList<Recipe>, navController: NavController)  {
    Button(
        onClick = {
            recipes.remove(recipe)
            navController.navigate(Screen.RecipeListScreen.route)
        },
        modifier = Modifier
    ) {
        Text("X")
    }
}

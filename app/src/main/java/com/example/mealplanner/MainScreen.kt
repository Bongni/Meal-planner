package com.example.mealplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun MainScreen(navController: NavController, recipes: LinkedHashMap<String, String>) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        ) {
            recipes.forEach { (key, value) ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    RecipeField(title = key, navController = navController)
                    DeleteButton(key = key, recipes = recipes, navController = navController)
                }
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
fun RecipeField (title : String, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Screen.RecipeScreen.withArgs(title))
        },
        modifier = Modifier.width(200.dp)
    ) {
        Text(text = title)
    }
}

@Composable
fun DeleteButton (key: String, recipes: LinkedHashMap<String, String>, navController: NavController)  {
    Button(
        onClick = {
            recipes.remove(key)
            navController.navigate(Screen.MainScreen.route)
        },
        modifier = Modifier
    ) {
        Text("X")
    }
}

package com.example.mealplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeScreen(title: String, recipe: String, ingredients: ArrayList<Ingredient>) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        Text(text = recipe)

        LazyColumn(
            contentPadding = PaddingValues(vertical = 12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(ingredients.size) {index ->
                val ingredient = ingredients[index]

                Ingredient(ingredient = ingredient)
            }
        }
    }
}

package com.example.mealplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecipeScreen(title: String, ingredients: String) {
    Column {
        Text(text = title)
        Text(text = ingredients)
    }
}

package com.example.mealplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun Recipe(title: String, ingredients: String, navController: NavController) {
    Column {
        HomeButton(navController = navController)
        Text(text = title)
        Text(text = ingredients)
    }
}

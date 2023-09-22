package com.example.mealplanner

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class Ingredient (var name: String, var amount: Double, var unit: Unit) {
    val id: Int get() = hashCode()
}

enum class Unit {
    G, L, EL, KL, Stk
}

@Composable
fun Ingredient (ingredient: Ingredient) {
    Surface (
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        val pad = 5.dp
        Row (
            modifier = Modifier
                .padding(20.dp, 8.dp)
        ) {
            Text(
                text = ingredient.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(pad)
            )
            Text(
                text = ingredient.amount.toString(),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(pad)
            )
            Text(
                text = ingredient.unit.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(pad)
            )
        }
    }
}
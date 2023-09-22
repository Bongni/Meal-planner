package com.example.mealplanner

import org.json.JSONObject
import kotlin.reflect.typeOf

data class Recipe (var name: String, var recipe: String, var ingredients: ArrayList<Ingredient>) {
    val id: Int get() = hashCode()

    override fun equals(other: Any?): Boolean {
        if(other is Recipe)
            return this.name == other.name

        return super.equals(other)
    }
}

fun getRecipeWhereName(name: String, recipes: ArrayList<Recipe>): Recipe {
    for(recipe in recipes) {
        if (name == recipe.name) return recipe
    }

    return Recipe("", "", ArrayList<Ingredient>())
}
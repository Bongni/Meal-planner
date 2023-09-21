package com.example.mealplanner

import org.json.JSONObject

data class Recipe (var name: String, var recipe: String, var ingredients: ArrayList<Ingredient>) {
    val id: Int get() = hashCode()
}

data class Ingredient (var name: String, var amount: Int, var unit: Unit) {
    val id: Int get() = hashCode()
}

enum class Unit {
    G, L, EL, KL
}
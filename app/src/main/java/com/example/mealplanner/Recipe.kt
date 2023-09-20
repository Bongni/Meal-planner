package com.example.mealplanner

import org.json.JSONObject

data class Recipe (var name: String, var recipe: String, var ingredients: ArrayList<Ingredient>) {

}

data class Ingredient (var name: String, var amount: Int, var unit: Unit) {

}

enum class Unit {
    G, L, EL, KL
}
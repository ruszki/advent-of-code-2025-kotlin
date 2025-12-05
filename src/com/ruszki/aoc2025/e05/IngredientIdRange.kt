package com.ruszki.aoc2025.e05

class IngredientIdRange(val start: ULong, val end: ULong) {
    fun includes(ingredient: Ingredient): Boolean {
        return ingredient.id in start..end
    }

    override fun toString(): String {
        return "$start-$end"
    }


}

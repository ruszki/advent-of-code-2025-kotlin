package com.ruszki.aoc2025.e05

class IngredientIdRange(var start: ULong, var end: ULong) {
    fun includes(ingredient: Ingredient): Boolean {
        return ingredient.id in start..end
    }

    override fun toString(): String {
        return "$start-$end"
    }

    fun size(): ULong {
        return end - start + 1uL
    }
}

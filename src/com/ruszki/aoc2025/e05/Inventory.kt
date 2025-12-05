package com.ruszki.aoc2025.e05

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Inventory {
    val ingredientRanges = mutableListOf<IngredientIdRange>()

    var freshCount: ULong = 0u
        private set

    private fun addIngredient(ingredient: Ingredient) {
        if (checkFresh(ingredient)) {
            freshCount++
        }
    }

    private fun checkFresh(ingredient: Ingredient): Boolean {
        for (ingredientRange in ingredientRanges) {
            if (ingredientRange.includes(ingredient)) {
                return true
            }
        }

        return false
    }

    companion object {
        fun load(pathString: String): Inventory {
            val inventory = Inventory()

            var state = LoadState.RANGES

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (state == LoadState.RANGES) {
                        if (line.isBlank()) {
                            state = LoadState.INGREDIENTS
                        } else {
                            val rangeLimits = line.split('-')

                            if (rangeLimits.size == 2) {
                                val start = rangeLimits[0].toULong()
                                val end = rangeLimits[1].toULong()

                                val ingredientRange = IngredientIdRange(start, end)

                                inventory.ingredientRanges.add(ingredientRange)
                            }
                        }
                    } else {
                        if (line.isNotBlank()) {
                            val ingredient = Ingredient(line.toULong())

                            inventory.addIngredient(ingredient)
                        }
                    }
                }
            }

            return inventory
        }
    }

    enum class LoadState {
        RANGES, INGREDIENTS
    }
}
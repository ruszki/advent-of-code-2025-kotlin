package com.ruszki.aoc2025.e05

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Inventory {
    val ingredientRanges = mutableListOf<IngredientIdRange>()

    val freshRangeCount
        get() = ingredientRanges.sumOf { it.size() }

    var freshCount: ULong = 0u
        private set

    private fun addIngredientRange(newIngredientRange: IngredientIdRange) {
        val deletedRanges = mutableListOf<IngredientIdRange>()
        var newStart: ULong = newIngredientRange.start
        var newEnd: ULong = newIngredientRange.end

        for (ingredientRange in ingredientRanges.sortedWith { rangeA, rangeB -> rangeA.start.compareTo(rangeB.start) }) {
            if (newIngredientRange.end < ingredientRange.start) {
                break
            } else if (newIngredientRange.start > ingredientRange.end) {
                continue
            } else {
                newStart = if (newStart > ingredientRange.start) ingredientRange.start else newStart
                newEnd = if (newEnd < ingredientRange.end) ingredientRange.end else newEnd

                deletedRanges.add(ingredientRange)
            }
        }

        ingredientRanges.add(IngredientIdRange(newStart, newEnd))

        for (deletedRange in deletedRanges) {
            ingredientRanges.remove(deletedRange)
        }
    }

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

    override fun toString(): String {
        return ingredientRanges.joinToString("\n") { it.toString() }
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

                                inventory.addIngredientRange(ingredientRange)
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
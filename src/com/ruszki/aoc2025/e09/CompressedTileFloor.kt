package com.ruszki.aoc2025.e09

import kotlin.collections.set

class CompressedTileFloor(originalRedTiles: List<RedTile>) {
    val tileMap = mutableMapOf<ULong, MutableMap<ULong, TileType>>()

    val redTileMap = mutableMapOf<RedTile, RedTile>()

    init {
        compress(originalRedTiles)
    }

    fun getLargestGreenAndRedRectangle(): Pair<RedTile, RedTile>? {
        var maxArea = 0uL
        var maxRedTileA: RedTile? = null
        var maxRedTileB: RedTile? = null

        val compressedRedTiles = redTileMap.entries.toList()

        for (i in compressedRedTiles.indices) {
            val compressedRedTileA = compressedRedTiles[i].key
            val redTileA = compressedRedTiles[i].value

            println(redTileA)

            otherRectangleCornerLoop@ for (j in i + 1..compressedRedTiles.lastIndex) {
                val compressedRedTileB = compressedRedTiles[j].key
                val redTileB = compressedRedTiles[j].value

                val xRange = minOf(compressedRedTileA.x, compressedRedTileB.x)..maxOf(compressedRedTileA.x, compressedRedTileB.x)
                val yRange = minOf(compressedRedTileA.y, compressedRedTileB.y)..maxOf(compressedRedTileA.y, compressedRedTileB.y)

                for (x in xRange) {
                    for (y in yRange) {
                        if (!inside(x, y, redTileMap.keys)) {
                            continue@otherRectangleCornerLoop
                        }
                    }
                }

                val areaOfSpannedRectangle = redTileA.areaOfSpannedRectangle(redTileB)

                if (maxArea < areaOfSpannedRectangle) {
                    maxArea = areaOfSpannedRectangle

                    maxRedTileA = redTileA
                    maxRedTileB = redTileB
                }
            }
        }

        return if (maxRedTileA != null && maxRedTileB != null) Pair(maxRedTileA, maxRedTileB) else null
    }

    private fun compress(originalRedTiles: List<RedTile>) {
        tileMap.clear()
        redTileMap.clear()

        val distinctXValues = originalRedTiles.map { it.x }.distinct().sorted().toList()
        val distinctYValues = originalRedTiles.map { it.y }.distinct().sorted().toList()

        val distinctXValuesMap = distinctXValues.mapIndexed { index, x -> x to index.toULong() }.toMap()
        val distinctYValuesMap = distinctYValues.mapIndexed { index, y -> y to index.toULong() }.toMap()

        for (x in distinctXValuesMap.values) {
            val yTileMap = mutableMapOf<ULong, TileType>()

            for (y in distinctYValuesMap.values) {
                yTileMap[y] = TileType.OUTSIDE
            }

            tileMap[x] = yTileMap
        }

        var firstCompressedRedTile: RedTile? = null
        var previousCompressedRedTile: RedTile? = null

        originalRedTiles.forEach { redTile ->
            val compressedRedTile = RedTile(distinctXValuesMap[redTile.x]!!, distinctYValuesMap[redTile.y]!!)

            redTileMap[compressedRedTile] = redTile

            tileMap[compressedRedTile.x]!![compressedRedTile.y] = TileType.RED

            if (previousCompressedRedTile == null) {
                previousCompressedRedTile = compressedRedTile
            }

            if (firstCompressedRedTile == null) {
                firstCompressedRedTile = compressedRedTile
            }

            compressedRedTile.previous = previousCompressedRedTile
            compressedRedTile.previous.next = compressedRedTile
            compressedRedTile.next = firstCompressedRedTile
            compressedRedTile.next.previous = compressedRedTile

            if (previousCompressedRedTile.x == compressedRedTile.x) {
                for (y in (minOf(compressedRedTile.y, previousCompressedRedTile.y) + 1uL)..<maxOf(
                    compressedRedTile.y,
                    previousCompressedRedTile.y
                )) {
                    tileMap[compressedRedTile.x]!![y] = TileType.BORDER_GREEN_Y
                }
            } else { // Y must be the same according to specification
                for (x in (minOf(compressedRedTile.x, previousCompressedRedTile.x) + 1uL)..<maxOf(
                    compressedRedTile.x,
                    previousCompressedRedTile.x
                )) {
                    tileMap[x]!![redTile.y] = TileType.BORDER_GREEN_X
                }
            }

            previousCompressedRedTile = compressedRedTile
        }

        for (x in 0uL..distinctXValuesMap.values.max()) {
            var inside = false

            for (y in 0uL..distinctYValuesMap.values.max()) {
                val tileType = tileMap[x]!![y]!!

                if (tileType == TileType.BORDER_GREEN_X || tileType == TileType.RED) {
                    inside = !inside
                } else if (inside) {
                    tileMap[x]!![y] = TileType.INSIDE_GREEN
                }
            }
        }
    }

    private fun inside(x: ULong, y: ULong, redTiles: Iterable<RedTile>): Boolean {
        var intersections = 0

        for (currentRedTile in redTiles) {
            val nextRedTile = currentRedTile.next

            if (currentRedTile.x == x && currentRedTile.y == y) {
                return true
            } else if (currentRedTile.x == nextRedTile.x && currentRedTile.x == x && y > minOf(
                    currentRedTile.y,
                    nextRedTile.y
                ) && y < maxOf(currentRedTile.y, nextRedTile.y)
            ) {
                return true
            } else if ((currentRedTile.y > y) != (nextRedTile.y > y)) {
                val intersectionX =
                    currentRedTile.x.toLong() + (y.toLong() - currentRedTile.y.toLong()) * (nextRedTile.x.toLong() - currentRedTile.x.toLong()) / (nextRedTile.y.toLong() - currentRedTile.y.toLong())
                if (x.toLong() < intersectionX) {
                    intersections++
                }
            }
        }

        return intersections % 2 == 1
    }

    enum class TileType {
        RED, INSIDE_GREEN, BORDER_GREEN_Y, BORDER_GREEN_X, OUTSIDE
    }
}
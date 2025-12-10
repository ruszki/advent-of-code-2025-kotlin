package com.ruszki.aoc2025.e09

class TileFloor {
    private val redTiles = mutableListOf<RedTile>()

    fun addRedTile(tile: RedTile) {
        redTiles.add(tile)
    }

    fun getMaximumRedRectangle(): ULong {
        var maxArea = 0uL

        for (i in redTiles.indices) {
            for (j in i + 1..redTiles.lastIndex) {
                val redTileA = redTiles[i]
                val redTileB = redTiles[j]

                val areaOfSpannedRectangle = redTileA.areaOfSpannedRectangle(redTileB)

                if (maxArea < areaOfSpannedRectangle) {
                    maxArea = areaOfSpannedRectangle
                }
            }
        }

        return maxArea
    }
}
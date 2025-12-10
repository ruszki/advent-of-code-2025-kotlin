package com.ruszki.aoc2025.e09

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class TileFloor {
    private val redTiles = mutableListOf<RedTile>()

    fun addRedTile(tile: RedTile) {
        val previous = redTiles.lastOrNull()
        val next = redTiles.firstOrNull()

        if (previous != null && next != null) {
            next.previous = tile
            previous.next = tile

            tile.previous = previous
            tile.next = next
        }

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

    fun getMaximumRedAndGreenRectangle(): ULong {
        val compressedTileFloor = CompressedTileFloor(redTiles)

        val (rectangleCornerA, rectangleCornerB) = compressedTileFloor.getLargestGreenAndRedRectangle() ?: Pair(null, null)

        return if (rectangleCornerA != null && rectangleCornerB != null) rectangleCornerA.areaOfSpannedRectangle(rectangleCornerB) else 0uL
    }


    companion object {
        fun load(pathString: String): TileFloor {
            val tileFloor = TileFloor()

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (line.isNotBlank()) {
                        val redTile = RedTile.from(line)

                        tileFloor.addRedTile(redTile)
                    }
                }
            }

            return tileFloor
        }
    }
}
package com.ruszki.aoc2025.e09

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

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
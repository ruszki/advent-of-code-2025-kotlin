package com.ruszki.aoc2025.e12

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class ChristmasTreeFarm(val presents: List<Present>, val christmasTrees: List<ChristmasTree>) {
    override fun toString(): String {
        return "${presents.joinToString("\n\n")}\n\n${christmasTrees.joinToString("\n")}"
    }

    companion object {
        fun load(pathString: String): ChristmasTreeFarm {
            val presentMap = mutableMapOf<ULong, Present>()
            val christmasTrees = mutableListOf<ChristmasTree>()

            var loaderState = LOADER_STATE.PRESENT_ID_OR_CHRISTMAS_TREE
            var presentId = 0uL
            var currentShapeLine = 0L
            var shapePoints = mutableListOf<Pair<Long, Long>>()

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    when (loaderState) {
                        LOADER_STATE.PRESENT_ID_OR_CHRISTMAS_TREE ->
                            if (line.isNotBlank()) {
                                if (line.contains("x")) {
                                    loaderState = LOADER_STATE.CHRISTMAS_TREE

                                    christmasTrees.add(getChristmasTreeFrom(line, presentMap))
                                } else {
                                    presentId = line.split(":")[0].toULong()
                                    currentShapeLine = 0L
                                    shapePoints = mutableListOf()

                                    loaderState = LOADER_STATE.PRESENT_SHAPE
                                }
                            }

                        LOADER_STATE.PRESENT_SHAPE ->
                            if (line.isBlank()) {
                                presentMap[presentId] = Present(presentId, shapePoints)

                                loaderState = LOADER_STATE.PRESENT_ID_OR_CHRISTMAS_TREE
                            } else {
                                shapePoints.addAll(
                                    line
                                        .mapIndexed { index, c -> if (c == '#') currentShapeLine to index.toLong() else null }
                                        .filterNotNull()
                                )

                                currentShapeLine += 1L
                            }

                        LOADER_STATE.CHRISTMAS_TREE ->
                            if (line.isNotBlank()) {
                                christmasTrees.add(getChristmasTreeFrom(line, presentMap))
                            }
                    }
                }
            }

            if (loaderState == LOADER_STATE.PRESENT_SHAPE) {
                presentMap[presentId] = Present(presentId, shapePoints)
            }

            return ChristmasTreeFarm(presentMap.values.toList(), christmasTrees)
        }

        private fun getChristmasTreeFrom(line: String, presents: Map<ULong, Present>): ChristmasTree {
            val sizePresentsSplit = line.split(": ")

            val sizes = sizePresentsSplit[0].split("x").map { it.toULong() }

            val presents = sizePresentsSplit[1].split(" ")
                .mapIndexed { index, count -> presents[index.toULong()]!! to count.toULong() }.toMap()

            return ChristmasTree(sizes[0], sizes[1], presents)
        }

        private enum class LOADER_STATE {
            PRESENT_ID_OR_CHRISTMAS_TREE, PRESENT_SHAPE, CHRISTMAS_TREE
        }
    }
}
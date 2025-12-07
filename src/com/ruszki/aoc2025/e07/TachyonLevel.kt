package com.ruszki.aoc2025.e07

class TachyonLevel(val size: ULong, val splitters: List<ULong>, start: ULong? = null) {
    private val beams = mutableSetOf<ULong>()

    init {
        if (start != null) {
            beams.add(start)
        }
    }

    fun getBeams(): Iterable<ULong> {
        return beams
    }

    /**
     * Returns the number of used splitters
     */
    fun addIncomingBeams(incomingBeams: Iterable<ULong>): ULong {
        var usedSplitterCount = 0uL

        for (beam in incomingBeams) {
            if (splitters.contains(beam)) {
                usedSplitterCount++

                if (beam - 1uL >= 0uL) {
                    beams.add(beam - 1uL)
                }

                if (beam + 1uL < size) {
                    beams.add(beam + 1uL)
                }
            } else {
                beams.add(beam)
            }
        }

        return usedSplitterCount
    }
}
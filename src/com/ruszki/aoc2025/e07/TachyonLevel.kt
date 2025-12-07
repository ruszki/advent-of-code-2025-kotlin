package com.ruszki.aoc2025.e07

class TachyonLevel(val size: ULong, val splitters: List<ULong>, start: ULong? = null) {
    private val beams = mutableMapOf<ULong, ULong>()

    init {
        if (start != null) {
            beams[start] = 1uL
        }
    }

    fun getBeams(): Map<ULong, ULong> {
        return beams
    }

    private fun addBeam(beamIndex: ULong, beamPossibilities: ULong) {
        beams[beamIndex] = beams.getOrDefault(beamIndex, 0uL) + beamPossibilities
    }

    /**
     * Returns the number of used splitters
     */
    fun addIncomingBeams(incomingBeams: Map<ULong, ULong>): ULong {
        var usedSplitterCount = 0uL

        for (beam in incomingBeams) {
            val beamIndex = beam.key
            val beamPossibilities = beam.value

            if (splitters.contains(beamIndex)) {
                usedSplitterCount++

                if (beamIndex - 1uL >= 0uL) {
                    addBeam(beamIndex - 1uL, beamPossibilities)
                }

                if (beamIndex + 1uL < size) {
                    addBeam(beamIndex + 1uL, beamPossibilities)
                }
            } else {
                addBeam(beamIndex, beamPossibilities)
            }
        }

        return usedSplitterCount
    }
}
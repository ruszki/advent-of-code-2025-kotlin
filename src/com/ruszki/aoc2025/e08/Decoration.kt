package com.ruszki.aoc2025.e08

class Decoration {
    val junctionBoxMap = mutableMapOf<JunctionBox, ULong>()

    fun addJunctionBox(jb: JunctionBox) {
        junctionBoxMap[jb] = junctionBoxMap.values.maxOrNull()?.plus(1uL) ?: 0uL
    }

    fun connectNext() {
        if (getCircuits().size <= 1) {
            return
        }

        var minimumDistance = Double.MAX_VALUE
        var minimumJunctionBoxA: JunctionBox? = null
        var minimumJunctionBoxB: JunctionBox? = null

        val junctionBoxList = junctionBoxMap.keys.toList()

        for (i in 0..junctionBoxList.lastIndex) {
            for (j in i + 1..junctionBoxList.lastIndex) {
                val junctionBoxA = junctionBoxList[i]
                val junctionBoxB = junctionBoxList[j]

                val circuitIdA = junctionBoxMap[junctionBoxA]
                val circuitIdB = junctionBoxMap[junctionBoxB]

                if (circuitIdA != circuitIdB) {
                    val distance = junctionBoxA.position.distanceTo(junctionBoxB.position)

                    if (distance < minimumDistance) {
                        minimumDistance = distance
                        minimumJunctionBoxA = junctionBoxA
                        minimumJunctionBoxB = junctionBoxB
                    }
                }
            }
        }

        val circuitIdA = junctionBoxMap[minimumJunctionBoxA] ?: return
        val circuitIdB = junctionBoxMap[minimumJunctionBoxB] ?: return

        if (circuitIdA < circuitIdB) {
            junctionBoxMap.replaceAll { _, oldCircuitId -> if (oldCircuitId == circuitIdB) circuitIdA else oldCircuitId }
        } else if (circuitIdA > circuitIdB) {
            junctionBoxMap.replaceAll { _, oldCircuitId -> if (oldCircuitId == circuitIdA) circuitIdB else oldCircuitId }
        }
    }

    fun getCircuits(): Map<ULong, List<JunctionBox>> {
        val circuitMap = mutableMapOf<ULong, MutableList<JunctionBox>>()

        junctionBoxMap.forEach { (junctionBox, circuitId) ->
            circuitMap.compute(circuitId) { _, junctionBoxList ->
                junctionBoxList?.also { it.add(junctionBox) } ?: mutableListOf(junctionBox)
            }
        }

        return circuitMap
    }

    override fun toString(): String {
        val circuitMap = getCircuits()

        if (circuitMap.isEmpty()) {
            return "There are no circuits"
        } else {
            return "Circuits:\n" + circuitMap.entries.joinToString("\n") { "  - ${it.key}: ${it.value.joinToString (", ")}" }
        }
    }


}
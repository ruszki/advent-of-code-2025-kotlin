package com.ruszki.aoc2025.e08

class Decoration {
    val junctionBoxMap = mutableMapOf<JunctionBox, ULong>()

    fun addJunctionBox(jb: JunctionBox) {
        junctionBoxMap[jb] = junctionBoxMap.values.maxOrNull()?.plus(1uL) ?: 0uL
    }

    fun connect(jbA: JunctionBox, jbB: JunctionBox) {
        val circuitIdA = junctionBoxMap[jbA] ?: return
        val circuitIdB = junctionBoxMap[jbB] ?: return

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
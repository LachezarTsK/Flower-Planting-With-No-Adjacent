
class Solution {

    private companion object {
        const val NO_FLOWERS = 0
        val FLOWER_TYPES = intArrayOf(1, 2, 3, 4)
        val BITSTAMP_ALL_FLOWER_TYPES = createBitstampAllFlowerTypes()

        fun createBitstampAllFlowerTypes(): Int {
            var bitstampAllFlowerTypes = 0
            for (flowerType in FLOWER_TYPES) {
                bitstampAllFlowerTypes = bitstampAllFlowerTypes xor (1 shl flowerType)
            }
            return bitstampAllFlowerTypes
        }
    }

    fun gardenNoAdj(numberOfGardens: Int, paths: Array<IntArray>): IntArray {
        val undirectedGraph = createUndirectedGraph(numberOfGardens, paths)
        return createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph)
    }

    private fun createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens: Int, undirctedGraph: List<List<Int>>): IntArray {
        val plantedGardens = IntArray(numberOfGardens)
        for (garden in 0..<numberOfGardens) {
            if (plantedGardens[garden] == NO_FLOWERS) {
                plantedGardens[garden] = getOneAvailableFlowerType(undirctedGraph[garden], plantedGardens)
            }
        }
        return plantedGardens
    }

    private fun createUndirectedGraph(numberOfGardens: Int, paths: Array<IntArray>): List<List<Int>> {
        val graph = ArrayList<ArrayList<Int>>(numberOfGardens)
        for (i in 0..<numberOfGardens) {
            graph.add(ArrayList<Int>())
        }

        for (path in paths) {
            val fristGarden = getZeroBasedIndexFromOneBasedIndex(path[0])
            val secondGarden = getZeroBasedIndexFromOneBasedIndex(path[1])
            graph[fristGarden].add(secondGarden)
            graph[secondGarden].add(fristGarden)
        }
        return graph
    }

    private fun getZeroBasedIndexFromOneBasedIndex(oneBasedIndex: Int): Int {
        return oneBasedIndex - 1
    }

    private fun getOneAvailableFlowerType(neighbourGardens: List<Int>, plantedGardens: IntArray): Int {
        var bitstamptOfAvailableFlowerTypes = getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens)
        var flowerType = 0
        while ((bitstamptOfAvailableFlowerTypes and 1) == 0) {
            ++flowerType
            bitstamptOfAvailableFlowerTypes = bitstamptOfAvailableFlowerTypes shr 1
        }
        return flowerType
    }

    private fun getBitstamptOfAvailableFlowerTypes(neighbourGardens: List<Int>, plantedGardens: IntArray): Int {
        var bitstamptOfAvailableFlowerTypes = 0
        for (garden in neighbourGardens) {
            if (plantedGardens[garden] != NO_FLOWERS) {
                bitstamptOfAvailableFlowerTypes = bitstamptOfAvailableFlowerTypes or (1 shl plantedGardens[garden])
            }
        }
        bitstamptOfAvailableFlowerTypes = bitstamptOfAvailableFlowerTypes xor BITSTAMP_ALL_FLOWER_TYPES
        return bitstamptOfAvailableFlowerTypes
    }
}


package main

import "fmt"

const NO_FLOWERS = 0

var FLOWER_TYPES = [4]int{1, 2, 3, 4}
var BITSTAMP_ALL_FLOWER_TYPES = createBitstampAllFlowerTypes()

func gardenNoAdj(numberOfGardens int, paths [][]int) []int {
    undirectedGraph := createUndirectedGraph(numberOfGardens, paths)
    return createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph)
}

func createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens int, undirctedGraph [][]int) []int {
    plantedGardens := make([]int, numberOfGardens)
    for garden := 0; garden < numberOfGardens; garden++ {
        if plantedGardens[garden] == NO_FLOWERS {
            plantedGardens[garden] = getOneAvailableFlowerType(undirctedGraph[garden], plantedGardens)
        }
    }
    return plantedGardens
}

func createUndirectedGraph(numberOfGardens int, paths [][]int) [][]int {
    graph := make([][]int, numberOfGardens)
    for i := 0; i < numberOfGardens; i++ {
        graph[i] = []int{}
    }

    for _, path := range paths {
        fristGarden := getZeroBasedIndexFromOneBasedIndex(path[0])
        secondGarden := getZeroBasedIndexFromOneBasedIndex(path[1])
        graph[fristGarden] = append(graph[fristGarden], secondGarden)
        graph[secondGarden] = append(graph[secondGarden], fristGarden)
    }
    return graph
}

func getZeroBasedIndexFromOneBasedIndex(oneBasedIndex int) int {
    return oneBasedIndex - 1
}

func createBitstampAllFlowerTypes() int {
    bitstampAllFlowerTypes := 0
    for _, flowerType := range FLOWER_TYPES {
        bitstampAllFlowerTypes ^= 1 << flowerType
    }
    return bitstampAllFlowerTypes
}

func getOneAvailableFlowerType(neighbourGardens []int, plantedGardens []int) int {
    var bitstamptOfAvailableFlowerTypes = getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens)
    var flowerType = 0
    for (bitstamptOfAvailableFlowerTypes & 1) == 0 {
        flowerType++
        bitstamptOfAvailableFlowerTypes >>= 1
    }
    return flowerType
}

func getBitstamptOfAvailableFlowerTypes(neighbourGardens []int, plantedGardens []int) int {
    bitstamptOfAvailableFlowerTypes := 0
    for _, garden := range neighbourGardens {
        if plantedGardens[garden] != NO_FLOWERS {
            bitstamptOfAvailableFlowerTypes |= 1 << plantedGardens[garden]
        }
    }
    bitstamptOfAvailableFlowerTypes ^= BITSTAMP_ALL_FLOWER_TYPES
    return bitstamptOfAvailableFlowerTypes
}

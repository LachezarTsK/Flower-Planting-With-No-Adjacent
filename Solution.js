
/**
 * @param {number} numberOfGardens
 * @param {number[][]} paths
 * @return {number[]}
 */
var gardenNoAdj = function (numberOfGardens, paths) {
    this.NO_FLOWERS = 0;
    this.FLOWER_TYPES = [1, 2, 3, 4];
    this.BITSTAMP_ALL_FLOWER_TYPES = createBitstampAllFlowerTypes();

    const undirectedGraph = createUndirectedGraph(numberOfGardens, paths);
    return createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph);
};

/**
 * @param {number} numberOfGardens
 * @param {number[][]} undirectedGraph
 * @return {number[]}
 */
function createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph) {
    const plantedGardens = new Array(numberOfGardens).fill(this.NO_FLOWERS);
    for (let garden = 0; garden < numberOfGardens; ++garden) {
        if (plantedGardens[garden] === this.NO_FLOWERS) {
            plantedGardens[garden] = getOneAvailableFlowerType(undirectedGraph[garden], plantedGardens);
        }
    }
    return plantedGardens;
}

/**
 * @param {number} numberOfGardens
 * @param {number[][]} paths
 * @return {number[][]}
 */
function createUndirectedGraph(numberOfGardens, paths) {
    const graph = Array.from(new Array(numberOfGardens), () => new Array());
    for (let path of paths) {
        const fristGarden = getZeroBasedIndexFromOneBasedIndex(path[0]);
        const secondGarden = getZeroBasedIndexFromOneBasedIndex(path[1]);
        graph[fristGarden].push(secondGarden);
        graph[secondGarden].push(fristGarden);
    }
    return graph;
}

/**
 * @param {number} oneBasedIndex
 * @return {number}
 */
function getZeroBasedIndexFromOneBasedIndex(oneBasedIndex) {
    return oneBasedIndex - 1;
}

/**
 * @return {number}
 */
function createBitstampAllFlowerTypes() {
    let bitstampAllFlowerTypes = 0;
    for (let flowerType of this.FLOWER_TYPES) {
        bitstampAllFlowerTypes ^= (1 << flowerType);
    }
    return bitstampAllFlowerTypes;
}

/**
 * @param {number[]} neighbourGardens
 * @param {number[]} plantedGardens
 * @return {number}
 */
function getOneAvailableFlowerType(neighbourGardens, plantedGardens) {
    let bitstamptOfAvailableFlowerTypes = getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens);
    let flowerType = 0;
    while ((bitstamptOfAvailableFlowerTypes & 1) === 0) {
        ++flowerType;
        bitstamptOfAvailableFlowerTypes >>= 1;
    }
    return flowerType;
}

/**
 * @param {number[]} neighbourGardens
 * @param {number[]} plantedGardens
 * @return {number}
 */
function getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens) {
    let bitstamptOfAvailableFlowerTypes = 0;
    for (let garden of neighbourGardens) {
        if (plantedGardens[garden] !== this.NO_FLOWERS) {
            bitstamptOfAvailableFlowerTypes |= 1 << plantedGardens[garden];
        }
    }
    bitstamptOfAvailableFlowerTypes ^= this.BITSTAMP_ALL_FLOWER_TYPES;
    return bitstamptOfAvailableFlowerTypes;
}

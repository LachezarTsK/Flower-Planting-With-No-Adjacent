
function gardenNoAdj(numberOfGardens: number, paths: number[][]): number[] {
    this.NO_FLOWERS = 0;
    this.FLOWER_TYPES = [1, 2, 3, 4];
    this.BITSTAMP_ALL_FLOWER_TYPES = createBitstampAllFlowerTypes();

    const undirectedGraph = createUndirectedGraph(numberOfGardens, paths);
    return createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph);
};

function createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens: number, undirectedGraph: number[][]): number[] {
    const plantedGardens = new Array(numberOfGardens).fill(this.NO_FLOWERS);
    for (let garden = 0; garden < numberOfGardens; ++garden) {
        if (plantedGardens[garden] === this.NO_FLOWERS) {
            plantedGardens[garden] = getOneAvailableFlowerType(undirectedGraph[garden], plantedGardens);
        }
    }
    return plantedGardens;
}

function createUndirectedGraph(numberOfGardens: number, paths: number[][]): number[][] {
    const graph = Array.from(new Array(numberOfGardens), () => new Array());
    for (let path of paths) {
        const fristGarden = getZeroBasedIndexFromOneBasedIndex(path[0]);
        const secondGarden = getZeroBasedIndexFromOneBasedIndex(path[1]);
        graph[fristGarden].push(secondGarden);
        graph[secondGarden].push(fristGarden);
    }
    return graph;
}

function getZeroBasedIndexFromOneBasedIndex(oneBasedIndex: number): number {
    return oneBasedIndex - 1;
}

function createBitstampAllFlowerTypes(): number {
    let bitstampAllFlowerTypes = 0;
    for (let flowerType of this.FLOWER_TYPES) {
        bitstampAllFlowerTypes ^= (1 << flowerType);
    }
    return bitstampAllFlowerTypes;
}

function getOneAvailableFlowerType(neighbourGardens: number[], plantedGardens: number[]): number {
    let bitstamptOfAvailableFlowerTypes = getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens);
    let flowerType = 0;
    while ((bitstamptOfAvailableFlowerTypes & 1) === 0) {
        ++flowerType;
        bitstamptOfAvailableFlowerTypes >>= 1;
    }
    return flowerType;
}

function getBitstamptOfAvailableFlowerTypes(neighbourGardens: number[], plantedGardens: number[]): number {
    let bitstamptOfAvailableFlowerTypes = 0;
    for (let garden of neighbourGardens) {
        if (plantedGardens[garden] !== this.NO_FLOWERS) {
            bitstamptOfAvailableFlowerTypes |= 1 << plantedGardens[garden];
        }
    }
    bitstamptOfAvailableFlowerTypes ^= this.BITSTAMP_ALL_FLOWER_TYPES;
    return bitstamptOfAvailableFlowerTypes;
}

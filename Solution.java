
import java.util.ArrayList;
import java.util.List;

public class Solution {

    private static final int NO_FLOWERS = 0;
    private static final int[] FLOWER_TYPES = {1, 2, 3, 4};
    private static final int BITSTAMP_ALL_FLOWER_TYPES = createBitstampAllFlowerTypes();

    public int[] gardenNoAdj(int numberOfGardens, int[][] paths) {
        List<Integer>[] undirectedGraph = createUndirectedGraph(numberOfGardens, paths);
        return createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph);
    }

    private int[] createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(int numberOfGardens, List<Integer>[] undirctedGraph) {
        int[] plantedGardens = new int[numberOfGardens];
        for (int garden = 0; garden < numberOfGardens; ++garden) {
            if (plantedGardens[garden] == NO_FLOWERS) {
                plantedGardens[garden] = getOneAvailableFlowerType(undirctedGraph[garden], plantedGardens);
            }
        }
        return plantedGardens;
    }

    private List<Integer>[] createUndirectedGraph(int numberOfGardens, int[][] paths) {
        List<Integer>[] graph = new ArrayList[numberOfGardens];
        for (int i = 0; i < numberOfGardens; ++i) {
            graph[i] = new ArrayList<>();
        }

        for (int[] path : paths) {
            int fristGarden = getZeroBasedIndexFromOneBasedIndex(path[0]);
            int secondGarden = getZeroBasedIndexFromOneBasedIndex(path[1]);
            graph[fristGarden].add(secondGarden);
            graph[secondGarden].add(fristGarden);
        }
        return graph;
    }

    private int getZeroBasedIndexFromOneBasedIndex(int oneBasedIndex) {
        return oneBasedIndex - 1;
    }

    private static int createBitstampAllFlowerTypes() {
        int bitstampAllFlowerTypes = 0;
        for (int flowerType : FLOWER_TYPES) {
            bitstampAllFlowerTypes ^= (1 << flowerType);
        }
        return bitstampAllFlowerTypes;
    }

    private int getOneAvailableFlowerType(List<Integer> neighbourGardens, int[] plantedGardens) {
        int bitstamptOfAvailableFlowerTypes = getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens);
        int flowerType = 0;
        while ((bitstamptOfAvailableFlowerTypes & 1) == 0) {
            ++flowerType;
            bitstamptOfAvailableFlowerTypes >>= 1;
        }
        return flowerType;
    }

    private int getBitstamptOfAvailableFlowerTypes(List<Integer> neighbourGardens, int[] plantedGardens) {
        int bitstamptOfAvailableFlowerTypes = 0;
        for (int garden : neighbourGardens) {
            if (plantedGardens[garden] != NO_FLOWERS) {
                bitstamptOfAvailableFlowerTypes |= 1 << plantedGardens[garden];
            }
        }
        bitstamptOfAvailableFlowerTypes ^= BITSTAMP_ALL_FLOWER_TYPES;
        return bitstamptOfAvailableFlowerTypes;
    }
}

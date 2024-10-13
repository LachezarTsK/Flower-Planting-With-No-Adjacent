
#include <span>
#include <array>
#include <vector>
using namespace std;

/*
 The code will run faster with ios::sync_with_stdio(0).
 However, this should not be used in production code and interactive problems.
 In this particular problem, it is ok to apply ios::sync_with_stdio(0).

 Many of the top-ranked C++ solutions for time on leetcode apply this trick,
 so, for a fairer assessment of the time percentile of my code
 you could outcomment the lambda expression below for a faster run.
*/

/*
 const static auto speedup = [] {
	ios::sync_with_stdio(0);
	return nullptr;
 }();
*/

class Solution {

    static constexpr int createBitstampAllFlowerTypes() {
        int bitstampAllFlowerTypes = 0;
        for (const auto& flowerType : FLOWER_TYPES) {
            bitstampAllFlowerTypes ^= (1 << flowerType);
        }
        return bitstampAllFlowerTypes;
    }

    static const int NO_FLOWERS = 0;
    static constexpr array<int, 4> FLOWER_TYPES = { 1, 2, 3, 4 };
    inline static const int BITSTAMP_ALL_FLOWER_TYPES = createBitstampAllFlowerTypes();

public:
    vector<int> gardenNoAdj(int numberOfGardens, const vector<vector<int>>& paths) const {
        vector<vector<int>> undirectedGraph = createUndirectedGraph(numberOfGardens, paths);
        return createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph);
    }

private:
    vector<int> createPlantedGardensWhereAdjacentGardensHaveDifferentFlowers(int numberOfGardens, span<const vector<int>> undirctedGraph) const {
        vector<int> plantedGardens(numberOfGardens);
        for (int garden = 0; garden < numberOfGardens; ++garden) {
            if (plantedGardens[garden] == NO_FLOWERS) {
                plantedGardens[garden] = getOneAvailableFlowerType(undirctedGraph[garden], plantedGardens);
            }
        }
        return plantedGardens;
    }

    vector<vector<int>> createUndirectedGraph(int numberOfGardens, span<const vector<int>> paths) const {
        vector<vector<int>> graph(numberOfGardens);

        for (const auto& path : paths) {
            int fristGarden = getZeroBasedIndexFromOneBasedIndex(path[0]);
            int secondGarden = getZeroBasedIndexFromOneBasedIndex(path[1]);
            graph[fristGarden].push_back(secondGarden);
            graph[secondGarden].push_back(fristGarden);
        }
        return graph;
    }

    int getZeroBasedIndexFromOneBasedIndex(int oneBasedIndex) const {
        return oneBasedIndex - 1;
    }

    int getOneAvailableFlowerType(span<const int> neighbourGardens, span<const int> plantedGardens) const {
        int bitstamptOfAvailableFlowerTypes = getBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens);
        int flowerType = 0;
        while ((bitstamptOfAvailableFlowerTypes & 1) == 0) {
            ++flowerType;
            bitstamptOfAvailableFlowerTypes >>= 1;
        }
        return flowerType;
    }

    int getBitstamptOfAvailableFlowerTypes(span<const int> neighbourGardens, span<const int> plantedGardens) const {
        int bitstamptOfAvailableFlowerTypes = 0;
        for (const auto& garden : neighbourGardens) {
            if (plantedGardens[garden] != NO_FLOWERS) {
                bitstamptOfAvailableFlowerTypes |= 1 << plantedGardens[garden];
            }
        }
        bitstamptOfAvailableFlowerTypes ^= BITSTAMP_ALL_FLOWER_TYPES;
        return bitstamptOfAvailableFlowerTypes;
    }
};

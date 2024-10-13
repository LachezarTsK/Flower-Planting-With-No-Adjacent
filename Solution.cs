
using System;
using System.Collections.Generic;

public class Solution
{
    private static readonly int NO_FLOWERS = 0;
    private static readonly int[] FLOWER_TYPES = { 1, 2, 3, 4 };
    private static readonly int BITSTAMP_ALL_FLOWER_TYPES = CreateBitstampAllFlowerTypes();
    
    public int[] GardenNoAdj(int numberOfGardens, int[][] paths)
    {
        IList<int>[] undirectedGraph = CreateUndirectedGraph(numberOfGardens, paths);
        return CreatePlantedGardensWhereAdjacentGardensHaveDifferentFlowers(numberOfGardens, undirectedGraph);
    }

    private int[] CreatePlantedGardensWhereAdjacentGardensHaveDifferentFlowers(int numberOfGardens, IList<int>[] undirctedGraph)
    {
        int[] plantedGardens = new int[numberOfGardens];
        for (int garden = 0; garden < numberOfGardens; ++garden)
        {
            if (plantedGardens[garden] == NO_FLOWERS)
            {
                plantedGardens[garden] = GetOneAvailableFlowerType(undirctedGraph[garden], plantedGardens);
            }
        }
        return plantedGardens;
    }

    private IList<int>[] CreateUndirectedGraph(int numberOfGardens, int[][] paths)
    {
        IList<int>[] graph = new List<int>[numberOfGardens];
        for (int i = 0; i < numberOfGardens; ++i)
        {
            graph[i] = new List<int>();
        }

        foreach (int[] path in paths)
        {
            int fristGarden = GetZeroBasedIndexFromOneBasedIndex(path[0]);
            int secondGarden = GetZeroBasedIndexFromOneBasedIndex(path[1]);
            graph[fristGarden].Add(secondGarden);
            graph[secondGarden].Add(fristGarden);
        }
        return graph;
    }

    private int GetZeroBasedIndexFromOneBasedIndex(int oneBasedIndex)
    {
        return oneBasedIndex - 1;
    }

    private static int CreateBitstampAllFlowerTypes()
    {
        int bitstampAllFlowerTypes = 0;
        foreach (int flowerType in FLOWER_TYPES)
        {
            bitstampAllFlowerTypes ^= (1 << flowerType);
        }
        return bitstampAllFlowerTypes;
    }

    private int GetOneAvailableFlowerType(IList<int> neighbourGardens, int[] plantedGardens)
    {
        int bitstamptOfAvailableFlowerTypes = GetBitstamptOfAvailableFlowerTypes(neighbourGardens, plantedGardens);
        int flowerType = 0;
        while ((bitstamptOfAvailableFlowerTypes & 1) == 0)
        {
            ++flowerType;
            bitstamptOfAvailableFlowerTypes >>= 1;
        }
        return flowerType;
    }

    private int GetBitstamptOfAvailableFlowerTypes(IList<int> neighbourGardens, int[] plantedGardens)
    {
        int bitstamptOfAvailableFlowerTypes = 0;
        foreach (int garden in neighbourGardens)
        {
            if (plantedGardens[garden] != NO_FLOWERS)
            {
                bitstamptOfAvailableFlowerTypes |= 1 << plantedGardens[garden];
            }
        }
        bitstamptOfAvailableFlowerTypes ^= BITSTAMP_ALL_FLOWER_TYPES;
        return bitstamptOfAvailableFlowerTypes;
    }
}

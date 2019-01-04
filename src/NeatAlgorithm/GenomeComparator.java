package NeatAlgorithm;

import java.util.Collections;

public class GenomeComparator {

    public static double getGeneticDistance(Genome genome1, Genome genome2) {

        int numberOfGenesInLargerGenome = Math.max(genome1.getConnections().size(), genome2.getConnections().size());
        int highestCommonInnovation = Math.min(Collections.max(genome1.getConnections().keySet()), Collections.max(genome2.getConnections().keySet()));
        int highestGeneInnovation = Math.max(Collections.max(genome1.getConnections().keySet()), Collections.max(genome2.getConnections().keySet()));

        int disjointGenes = 0;
        for(int i=1; i <= highestCommonInnovation; i++) {
            if(genome1.getConnections().containsKey(i) ^ genome2.getConnections().containsKey(i))
            {
                disjointGenes++;
            }
        }

        int excessGenes = 0;
        for(int i=highestCommonInnovation + 1; i <= highestGeneInnovation; i++) {
            if(genome1.getConnections().containsKey(i) || genome2.getConnections().containsKey(i))
            {
                excessGenes++;
            }
        }


        double weightDifferenceSum = 0;
        int matchingGenesCount = 0;


        for (int i = 1; i <= highestGeneInnovation; i++)
        {
            if(genome1.getConnections().containsKey(i) && genome2.getConnections().containsKey(i))
            {
                matchingGenesCount++;
                weightDifferenceSum += Math.abs(genome1.getConnections().get(i).getWeight()-genome2.getConnections().get(i).getWeight());

            }
        }

        double averageWeight = weightDifferenceSum / matchingGenesCount;

        return (AlgorithmSettings.C1 * excessGenes / numberOfGenesInLargerGenome) + (AlgorithmSettings.C2 * disjointGenes / numberOfGenesInLargerGenome) + (AlgorithmSettings.C3 * averageWeight);
    }

    public static boolean isSameSpecies(Genome genome1, Genome genome2) {
        return getGeneticDistance(genome1, genome2) < AlgorithmSettings.MAX_GENETIC_DISTANCE;
    }

}

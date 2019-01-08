package NeatAlgorithm;

import java.util.Collections;

public class GenomeComparator {

    public static double getGeneticDistance(Genome genome1, Genome genome2) {

        int numberOfGenesInLargerGenome = Math.max(genome1.getConnections().size(), genome2.getConnections().size());
        int highestCommonInnovation = 0;
        if(genome1.getConnections().size() > 0 && genome2.getConnections().size() > 0) {
            highestCommonInnovation = Math.min(Collections.max(genome1.getConnections().keySet()), Collections.max(genome2.getConnections().keySet()));
        }

        int highestGeneInnovation = 0;
        if(genome1.getConnections().size() > 0 && genome2.getConnections().size() > 0) {
            highestGeneInnovation = Math.max(Collections.max(genome1.getConnections().keySet()), Collections.max(genome2.getConnections().keySet()));
        }

        int disjointGenes = 0;
        for(int i=0; i <= highestCommonInnovation; i++) {
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

        for (int i = 0; i <= highestGeneInnovation; i++)
        {
            if(genome1.getConnections().containsKey(i) && genome2.getConnections().containsKey(i))
            {
                matchingGenesCount++;
                weightDifferenceSum += Math.abs(genome1.getConnections().get(i).getWeight()-genome2.getConnections().get(i).getWeight());
            }
        }

        double averageWeight = 0;
        if(matchingGenesCount > 0) {
            averageWeight = weightDifferenceSum / matchingGenesCount;
        }

        double distance =  (AlgorithmSettings.C1 * excessGenes / numberOfGenesInLargerGenome) + (AlgorithmSettings.C2 * disjointGenes / numberOfGenesInLargerGenome) + (AlgorithmSettings.C3 * averageWeight);
        return distance;
    }

    public static boolean isSameSpecies(Genome genome1, Genome genome2) {
        return getGeneticDistance(genome1, genome2) < AlgorithmSettings.MAX_GENETIC_DISTANCE;
    }

}

package NeatAlgorithm;

import NeatAlgorithm.operators.SelectionOparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproductor {


    private static int getChosenSpecies(Random rng, ArrayList<Double> probabilities) {

        double chance = rng.nextDouble();

        for(int i = 0; i < probabilities.size(); i++) {
            if(chance <= probabilities.get(i) ) {
                return i;
            }
        }
        return -1;
    }


    public static Population createNextGeneration(Population oldGeneration) {
        Random rng = new Random();
        Population nextGeneration = new Population(oldGeneration.getInputNumber(), oldGeneration.getOutputNumber());
        int oldPopulationSize = oldGeneration.getSize();
        double populationAdjustedFitness = oldGeneration.getPopulationTotalAdjustedFitness();

        ArrayList<Double> probabilities = new ArrayList<>(0);
        double probabilitiesSum = 0;

        for(Species s : oldGeneration.getSpecies()) {
            double probability = s.getTotalAdjustedFitness()/populationAdjustedFitness;
            probabilities.add(probability + probabilitiesSum);
            probabilitiesSum += probability;
        }

        for (Double p : probabilities) {
            p = p / probabilitiesSum;
        }

        SelectionOparator selector = new Selector();

        for(int i = 0; i < oldPopulationSize; i++) {
            int speciesIndex = getChosenSpecies(rng, probabilities);
            List<GenomeWithFitness> survivors = selector.applySelection(oldGeneration.getSpecies().get(i));

            Genome newGenome;
            if(rng.nextDouble() < AlgorithmSettings.MUTATION_WITHOUT_CROSSOVER_CHANCE) {
                newGenome = survivors.get(rng.nextInt(survivors.size())).getGenome().copy();
            } else {
                newGenome = getChildOfSurvivors(rng, survivors);
            }

            NeatMutation.mutateGenome(newGenome);
            nextGeneration.addGenome(newGenome, 0, Double.NEGATIVE_INFINITY);
        }
        return nextGeneration;
    }

    private static Genome getChildOfSurvivors(Random rng, List<GenomeWithFitness> survivors) {
        GenomeWithFitness parent1 = survivors.get(rng.nextInt(survivors.size()));
        GenomeWithFitness parent2 = survivors.get(rng.nextInt(survivors.size()));

        return NeatCrossover.crossGenomes(parent1, parent2);
    }
}

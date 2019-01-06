package NeatAlgorithm;

import NeatAlgorithm.operators.SelectionOparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproductor {



    public static Population createNextGeneration(Population oldGeneration) {

        Population nextGeneration = new Population(oldGeneration.getInputNumber(), oldGeneration.getOutputNumber());

// TODO: kill stagnant species
        double populationAdjustedFitness = oldGeneration.getPopulationTotalAdjustedFitness();
        SelectionOparator selectionOparator = new Selector();

        for(Species s : oldGeneration.getSpecies()) {
//            TODO: pass stagnation to next generation
            nextGeneration.addGenome(s.getTopGenome().getGenome().copy(), s.stagnation);

//            TODO: make sure that numberOfOffspring sums up to oldGeneration.getSize();
            int numberOfOffspring = (int) (s.getTotalAdjustedFitness() / populationAdjustedFitness * oldGeneration.getSize());
            List<GenomeWithFitness> survivors = selectionOparator.applySelection(s);
            Random rng = new Random();

            for (int i=0; i < numberOfOffspring - 1; i++) {

                Genome newGenome = null;
                if(rng.nextDouble() < AlgorithmSettings.MUTATION_WITHOUT_CROSSOVER_CHANCE) {
                    newGenome = survivors.get(rng.nextInt(survivors.size())).getGenome().copy();
                } else {
                    GenomeWithFitness parent1 = survivors.get(rng.nextInt(survivors.size()));
                    GenomeWithFitness parent2 = survivors.get(rng.nextInt(survivors.size()));

                    newGenome = NeatCrossover.crossGenomes(parent1, parent2);
                }

                NeatMutation.mutateGenome(newGenome);
                nextGeneration.addGenome(newGenome, 0);

            }



        }



        return nextGeneration;
    }


}

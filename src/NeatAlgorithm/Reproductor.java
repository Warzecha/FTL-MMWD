package NeatAlgorithm;

import NeatAlgorithm.operators.SelectionOparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproductor {



    public static Population createNextGeneration(Population oldGeneration) {
//TODO: Should preserve unchanged champion from each species
        Population nextGeneration = new Population(oldGeneration.getSize());
        double populationAdjustedFitness = oldGeneration.getPopulationTotalAdjustedFitness();
        SelectionOparator selectionOparator = new Selector();

        for(Species s : oldGeneration.getSpecies()) {
//            TODO: make sure that numberOfOffspring sums up to oldGeneration.getSize();
            int numberOfOffspring = (int) (s.getTotalAdjustedFitness() / populationAdjustedFitness * oldGeneration.getSize());
            List<GenomeWithFitness> survivors = selectionOparator.applySelection(s);
            Random rng = new Random();

            for (int i=0; i < numberOfOffspring; i++) {

                Genome newGenome = null;
                if(rng.nextDouble() < AlgorithmSettings.MUTATION_WITHOUT_CROSSOVER_CHANCE) {
                    newGenome = survivors.get(rng.nextInt(survivors.size())).getGenome().copy();
                } else {
                    GenomeWithFitness parent1 = survivors.get(rng.nextInt(survivors.size()));
                    GenomeWithFitness parent2 = survivors.get(rng.nextInt(survivors.size()));

                    newGenome = NeatCrossover.crossGenomes(parent1, parent2);
                }


                NeatMutation.mutateGenome(newGenome);




            }



        }


        return nextGeneration;
    }


}

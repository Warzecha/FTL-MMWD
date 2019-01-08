package NeatAlgorithm;

import NeatAlgorithm.operators.SelectionOparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproductor {



    public static Population createNextGeneration(Population oldGeneration) {

        Population nextGeneration = new Population(oldGeneration.getInputNumber(), oldGeneration.getOutputNumber());

        int oldPopulation = oldGeneration.getSize();

// TODO: kill stagnant species
        oldGeneration.killStagnantSpecies();


        double populationAdjustedFitness = oldGeneration.getPopulationTotalAdjustedFitness();
        SelectionOparator selectionOparator = new Selector();
        Random rng = new Random();

        int newGenerationSize = 0;
        for(Species s : oldGeneration.getSpecies()) {
//            TODO: pass stagnation to next generation
            nextGeneration.addGenome(s.getTopGenome().getGenome().copy(), s.getStagnation(), s.getTopGenome().getFitness());
            newGenerationSize++;

//            TODO: make sure that numberOfOffspring sums up to oldGeneration.getSize() - test it thoroughly
            int numberOfOffspring = (int) (s.getTotalAdjustedFitness() / populationAdjustedFitness * oldPopulation);
            newGenerationSize += numberOfOffspring - 1;

            List<GenomeWithFitness> survivors = selectionOparator.applySelection(s);

            for (int i=0; i < numberOfOffspring - 1; i++) {

                Genome newGenome;
                if(rng.nextDouble() < AlgorithmSettings.MUTATION_WITHOUT_CROSSOVER_CHANCE) {
                    newGenome = survivors.get(rng.nextInt(survivors.size())).getGenome().copy();
                } else {
                    newGenome = getChildOfSurvivors(rng, survivors);
                }

                NeatMutation.mutateGenome(newGenome);
                nextGeneration.addGenome(newGenome, 0, Double.NEGATIVE_INFINITY);


            }



        }

        System.out.println(newGenerationSize);


        for(int i = 0; i < oldPopulation - newGenerationSize; i++) {


            Genome additionalGenome;
            if(rng.nextDouble() < AlgorithmSettings.INTERSPECIES_MATING_CHANCE) {
                List<GenomeWithFitness> survivors1 = selectionOparator.applySelection(oldGeneration.getSpecies().get(rng.nextInt(oldGeneration.getSpecies().size())));
                List<GenomeWithFitness> survivors2 = selectionOparator.applySelection(oldGeneration.getSpecies().get(rng.nextInt(oldGeneration.getSpecies().size())));

                GenomeWithFitness parent1 = survivors1.get(rng.nextInt(survivors1.size()));
                GenomeWithFitness parent2 = survivors2.get(rng.nextInt(survivors2.size()));

                additionalGenome = NeatCrossover.crossGenomes(parent1, parent2);


            } else {
                List<GenomeWithFitness> survivors = selectionOparator.applySelection(oldGeneration.getSpecies().get(rng.nextInt(oldGeneration.getSpecies().size())));

                additionalGenome = getChildOfSurvivors(rng, survivors);


            }

            NeatMutation.mutateGenome(additionalGenome);
            nextGeneration.addGenome(additionalGenome, 0, Double.NEGATIVE_INFINITY);


        }




        return nextGeneration;
    }

    private static Genome getChildOfSurvivors(Random rng, List<GenomeWithFitness> survivors) {
        GenomeWithFitness parent1 = survivors.get(rng.nextInt(survivors.size()));
        GenomeWithFitness parent2 = survivors.get(rng.nextInt(survivors.size()));

        return NeatCrossover.crossGenomes(parent1, parent2);
    }


}

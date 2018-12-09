package algorithm;

import algorithm.operators.*;
import game.Genome;

import java.util.ArrayList;

public class Population {

    private ArrayList<Genome> genomes;

    public Population() {
        this.genomes = new ArrayList<>();
    }

    public int getSize() {
        return genomes.size();
    }

    public void createInitialPopulation(int size) {
        for(int i = 0; i < size; i++) {
            genomes.add(new Genome(0));
        }
    }

    public ArrayList<Genome> getGenomes() {
        return genomes;
    }

    public void mutation(MutationOperator mutationOperator) {
        for (Genome genome : genomes) {
            mutationOperator.mutateGenome(genome);
        }
    }

    public void crossover(CrossoverOperator crossoverOperator) {
        // pattern of crossover can be modified
        for (int i = 0; i < genomes.size() - 1; i++) {
            Genome genome1 = genomes.get(i);
            Genome genome2 = genomes.get(i + 1);
            crossoverOperator.crossGenomes(genome1, genome2);
        }
        // crossing last with first
        crossoverOperator.crossGenomes(
                genomes.get(genomes.size()-1),
                genomes.get(0));
    }

    public void selection(FitnessOperator fitnessOperator, SelectionOperator selectionOperator) {
        ArrayList<GenomeWithFitness> genomesWithFitness = new ArrayList<>();
        for (Genome genome : genomes) {
            int fitness = fitnessOperator.getFitness(genome);
            genomesWithFitness.add(new GenomeWithFitness(genome, fitness));
        }
        genomesWithFitness.sort(GenomeWithFitness::compareTo);
        genomes = selectionOperator.applySelection(genomesWithFitness);
    }

    public void multiplication() {
        // copying each genome
        ArrayList<Genome> copyOfGenomes = new ArrayList<>();
        copyOfGenomes.addAll(genomes);
        genomes.addAll(copyOfGenomes);
    }

}

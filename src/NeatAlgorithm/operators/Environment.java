package NeatAlgorithm.operators;

import NeatAlgorithm.Genome;
import NeatAlgorithm.GenomeWithFitness;
import NeatAlgorithm.Population;
import NeatAlgorithm.Reproductor;

public abstract class Environment {

    public int inputsNumber;
    public int outputsNumber;
    public int maxGenerations;
    public int populationSize;
    public int generationNumber = 0;
    public Population population;
    public GenomeWithFitness topGenome;
    public Genome startinGenome;

    public Environment(int inputsNumber, int outputsNumber, int populationSize, int maxGenerations) {
        this.inputsNumber = inputsNumber;
        this.outputsNumber = outputsNumber;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
    }

    public abstract double evaluateFitness(Genome genome);
    public abstract Genome generateStartingGenome();

    public void initPopulation() {
        population = new Population(populationSize, generateStartingGenome());
    }

    public void iterate() {
        generationNumber++;
        population.evaluateFitness(this);
        topGenome = population.getTopGenome();
        System.out.println("Generation: " + generationNumber);
        System.out.println("TopFitness: " + topGenome.getFitness());
        System.out.println("Population: " + population.getSize());
        System.out.println("Best Size: " + population.getTopGenome().getGenome().getConnections().size());
        System.out.println("Species: " + population.getSpecies().size());
        System.out.println("\n\n");
        population = Reproductor.createNextGeneration(population);
    }


    public void startLoop() {
        for (int i = 0; i < 300; i++) {
            iterate();
        }
        population.evaluateFitness(this);
    }

}

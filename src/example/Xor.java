package example;

import NeatAlgorithm.*;
import NeatAlgorithm.operators.Enviroment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Xor implements Enviroment {

    public int inputsNumber = 3;
    public int outputsNumber = 1;
    public int maxGenerations = 500;
    public int generationNumber = 0;
    public Population population;
    public GenomeWithFitness topGenome;
    public int populationSize = 400;


    public Genome generateStartingGenome() {
        Genome startingGenome = new Genome(inputsNumber, outputsNumber);

        startingGenome.addConnectionGene(new ConnectionGene(0, 3, ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));
        startingGenome.addConnectionGene(new ConnectionGene(1, 3, ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));
        startingGenome.addConnectionGene(new ConnectionGene(2, 3, ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));

        return startingGenome;
    }


    public void initPopulation() {
        population = new Population(populationSize, generateStartingGenome());
    }

    @Override
    public double evaluateFitness(Genome genome) {
        double fitness = 0;

        for (int x=0; x<=1; x++) {
            for (int y=0; y<=1; y++) {
                List<Double> output = Processor.processNetwork(genome, Arrays.asList(1.0, (double) x, (double) y));
                fitness += 1 - Math.pow((double) (x^y) - output.get(0), 2);
            }
        }

//        System.out.println(Math.pow(fitness, 2));
//        return Math.pow(fitness, 2);
        return fitness;
    }

    public static void main(String[] args) {
        Xor xor = new Xor();
        xor.initPopulation();
        xor.startLoop();
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

//    public static void oldMain() { // TODO probably remove this method
//        Xor xor = new Xor();
//        Population population = new Population(200, xor.generateStartingGenome());
//        GenomeWithFitness topGenome = null;
//        int generation = 0;
//        for(int i=0; i < 100; i++){
//            population.evaluateFitness(xor);
//            topGenome = population.getTopGenome();
//            population = Reproductor.createNextGeneration(population);
//            generation++;
//        }
//        population.evaluateFitness(xor);
//        System.out.println("Generation: " + generation );
//        System.out.println("TopFitness: " + topGenome.getFitness());
//        System.out.println("Population: " + population.getSize());
//        System.out.println("Max Size : " + population.getBiggestGenomeSize());
//        System.out.println("Species : " + population.getSpecies().size());
//    }


}

package example;

import NeatAlgorithm.*;
import NeatAlgorithm.operators.Enviroment;

import java.util.Arrays;
import java.util.List;

public class Xor implements Enviroment {

    public int inputsNumber = 2;
    public int outputsNumber = 1;
    public int maxGenerations = 500;
    public int generationNumber = 0;
    public Population population;
    public GenomeWithFitness topGenome;
    public int populationSize = 50;

    public void initPopulation() {
        population = new Population(populationSize, inputsNumber, outputsNumber);
    }

    @Override
    public double evaluateFitness(Genome genome) {
        double fitness = 0;

        for (int x=0; x<=1; x++) {
            for (int y=0; y<=1; y++) {
                List<Double> output = Processor.processNetwork(genome, Arrays.asList((double) x, (double) y));
//                System.out.println(output.get(0));
                fitness += 1 - Math.abs((double) (x^y) - output.get(0));
            }
        }
        return fitness;
    }

    public static void main(String[] args) {
        new Xor().startLoop();
    }

    public void iterate() {
        generationNumber++;
        population.evaluateFitness(this);
        topGenome = population.getTopGenome();
        System.out.println("Generation: " + generationNumber);
        System.out.println("TopFitness: " + topGenome.getFitness());
        System.out.println("Population: " + population.getSize());
        System.out.println("Max Size : " + population.getBiggestGenomeSize());
        population = Reproductor.createNextGeneration(population);
    }

    public void startLoop(){
        for(int i=0; i < 500; i++){
            iterate();
        }
    }
}

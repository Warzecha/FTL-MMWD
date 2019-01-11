package example;

import NeatAlgorithm.*;
import NeatAlgorithm.operators.Environment;

import java.util.Arrays;
import java.util.List;

public class Xor extends Environment {


    public Xor(int populationSize, int maxGenerations) {
        super(3, 1, populationSize, maxGenerations);
    }

    public Genome generateStartingGenome() {
        Genome startingGenome = new Genome(inputsNumber, outputsNumber);

        startingGenome.addConnectionGene(new ConnectionGene(0, 3, ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));
        startingGenome.addConnectionGene(new ConnectionGene(1, 3, ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));
        startingGenome.addConnectionGene(new ConnectionGene(2, 3, ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));

        return startingGenome;
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
        Xor xor = new Xor(200, 100);
        xor.initPopulation();
        xor.startLoop();
    }





}

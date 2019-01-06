package example;

import NeatAlgorithm.*;
import NeatAlgorithm.operators.Enviroment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Xor implements Enviroment {

    int inputsNumber = 2;
    int outputsNumber = 1;

    @Override
    public double evaluateFitness(Genome genome) {
        double fitness = 0;


//        System.out.println("In: " + genome.getConnections().values().stream().map(ConnectionGene::getInNode).collect(Collectors.toList()));
//        System.out.println("Out: " + genome.getConnections().values().stream().map(ConnectionGene::getOutNode).collect(Collectors.toList()));
//        System.out.println("Types: " + genome.getNodes().values().stream().map(NodeGene::getType).collect(Collectors.toList()));

        for (int x=0; x<=1; x++) {
            for (int y=0; y<=1; y++) {

                List<Double> output = Processor.processNetwork(genome, Arrays.asList((double) x, (double) y));
                fitness += 1 - Math.abs((double) (x^y) - output.get(0));
            }
        }

        return fitness;
    }


    public static void main(String[] args){
        Xor xor = new Xor();

        Population population = new Population(100, xor.inputsNumber, xor.outputsNumber);


        GenomeWithFitness topGenome = null;
        int generation = 0;
        for(int i=0; i < 50; i++){


            population.evaluateFitness(xor);



            topGenome = population.getTopGenome();



            System.out.println("Generation: " + generation );
            System.out.println("TopFitness: " + topGenome.getFitness());
            System.out.println();



            population = Reproductor.createNextGeneration(population);
            generation++;

        }

        population.evaluateFitness(xor);
        System.out.println("Generation: " + generation );
        System.out.println("TopFitness: " + topGenome.getFitness());
        System.out.println("Population: " + population.getSize());
        System.out.println("Max Size : " + population.getBiggestGenomeSize());

    }



}

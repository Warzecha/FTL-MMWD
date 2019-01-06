package example;

import NeatAlgorithm.*;
import NeatAlgorithm.operators.Enviroment;

import java.util.Arrays;
import java.util.List;

public class Xor implements Enviroment {

    int inputsNumber = 2;
    int outputsNumber = 1;

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


    public static void main(String arg0[]){
        Xor xor = new Xor();

        Population population = new Population(50, xor.inputsNumber, xor.outputsNumber);


        GenomeWithFitness topGenome = null;
        int generation = 0;
        for(int i=0; i < 500; i++){


            population.evaluateFitness(xor);


            topGenome = population.getTopGenome();



            System.out.println("Generation: " + generation );
            System.out.println("TopFitness: " + topGenome.getFitness());
            System.out.println("Population: " + population.getSize());
            System.out.println("Max Size : " + population.getBiggestGenomeSize());


            population = Reproductor.createNextGeneration(population);
            generation++;

        }

    }



}

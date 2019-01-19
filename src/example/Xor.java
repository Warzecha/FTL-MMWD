package example;

import evoNeat.ConnectionGene;
import evoNeat.Environment;
import evoNeat.Genome;
import evoNeat.InnovationCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Xor extends Environment {


    public Xor(int populationSize, int maxGenerations) {
        super(2, 1, populationSize, maxGenerations);
    }

    public Genome generateStartingGenome() {

        Genome startingGenome = new Genome(this.inputsNumber, this.outputsNumber);

//        public ConnectionGene(int into, int out, int innovation, float weight, boolean enabled) {
//        startingGenome.addConnectionGene(new ConnectionGene(0, 3, InnovationCounter.newInnovation(), ConnectionGene.randomWeight(), true));
//        startingGenome.addConnectionGene(new ConnectionGene(1, 3, InnovationCounter.newInnovation(), ConnectionGene.randomWeight(), true));
//        startingGenome.addConnectionGene(new ConnectionGene(2, 3, InnovationCounter.newInnovation(), ConnectionGene.randomWeight(), true));

        return startingGenome;
    }




    @Override
    public void evaluateFitness(Genome genome) {
        float fitness = 0;

        for (int x=0; x<=1; x++) {
            for (int y=0; y<=1; y++) {
                List<Float> output = genome.evaluateNetwork(Arrays.asList((float) x, (float) y));
                fitness += 1 - Math.pow((float) (x^y) - output.get(0), 2);
            }
        }

//        System.out.println(fitness);
        genome.setFitness(fitness);
    }

    public static void main(String[] args) {
        Xor xor = new Xor(400, 600);
        xor.initPopulation();
        xor.startLoop();
    }





}

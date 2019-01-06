package NeatAlgorithm;

import java.util.Random;

public class NeatMutation {

    public static void mutateGenome(Genome genome) {

        Random rng = new Random();



        double chance = rng.nextDouble();

        if(chance <= AlgorithmSettings.WEIGHT_MUTATION_CHANCE) {
            genome.connectionWeightMutation(rng);
        } else if (chance <= AlgorithmSettings.WEIGHT_MUTATION_CHANCE + AlgorithmSettings.ADD_CONNECTION_MUTATION_CHANCE) {
            genome.addConnectionMutation(rng);
        } else if (chance <= AlgorithmSettings.WEIGHT_MUTATION_CHANCE + AlgorithmSettings.ADD_CONNECTION_MUTATION_CHANCE + AlgorithmSettings.ADD_NODE_MUTATION_CHANCE) {
            genome.addNodeMutation(rng);
        }

        System.out.println(genome.getConnections().size());
    }
}

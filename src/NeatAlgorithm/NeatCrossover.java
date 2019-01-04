package NeatAlgorithm;

import algorithm.operators.CrossoverOperator;

import java.util.Random;

public class NeatCrossover {



    public static Genome crossGenomes(GenomeWithFitness parent1, GenomeWithFitness parent2) {

        Genome parent1Genome = parent1.getGenome();
        Genome parent2Genome = parent2.getGenome();

        if(parent1.getFitness() < parent2.getFitness()){
            Genome temp = parent1Genome;
            parent1Genome = parent2Genome;
            parent2Genome = temp;
        }

        Genome child = new Genome(0, 0);
        Random rng = new Random();

        for(ConnectionGene connection : parent1Genome.getConnections().values()) {
            if(parent2Genome.getConnections().containsKey(connection.getInnovation())) {
                if(rng.nextBoolean()) {
                    child.addConnectionGene(connection.copy());
                } else {
                    child.addConnectionGene(parent2Genome.getConnections().get(connection.getInnovation()).copy());
                }
            } else
            {
                child.addConnectionGene(connection.copy());
            }
        }

//        TODO: handle genomes with equal fitness

        for(NodeGene node : parent1Genome.getNodes().values()) {
            child.addNodeGene(node.copy());
        }

        return child;
    }
}

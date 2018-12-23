package NeatAlgorithm;

import java.util.Random;

public class NeatCrossover {



    public static Genome crossover(Genome parent1, Genome parent2) {

        if(parent1.getFitness() < parent2.getFitness()){
            Genome temp = parent1;
            parent1 = parent2;
            parent2 = temp;
        }

        Genome child = new Genome();
        Random rng = new Random();

        for(ConnectionGene connection : parent1.getConnections().values()) {
            if(parent2.getConnections().containsKey(connection.getInnovation())) {
                if(rng.nextBoolean()) {
                    child.addConnectionGene(connection.copy());
                } else {
                    child.addConnectionGene(parent2.getConnections().get(connection.getInnovation()).copy());
                }
            } else
            {
                child.addConnectionGene(connection.copy());
            }
        }

//        TODO: handle genomes with equal fitness

        for(NodeGene node : parent1.getNodes().values()) {
            child.addNodeGene(node.copy());
        }

        return child;
    }
}

package NeatAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Processor {

    public static LinkedList<Double> processNetwork(Genome genome, List<Double> inputs) {
        genome.resetNodeValues();

        if(genome.getInputNodesCount() != inputs.size()) {
            throw new IllegalArgumentException("Inputs length does not match genomes input nodes count");
        }

        int i = 0;
        for (NodeGene n : genome.getNodes().values()) {
            if(n.getType() == NodeGene.TYPE.INPUT) {
                genome.setNodeValueById(n.getId(), inputs.get(i));
                i++;
            }
        }

//        TODO: make sure the order is right - outputs go last
        for (ConnectionGene connection : genome.getConnections().values()) {
            int inNodeId = connection.getInNode();
            int outNodeId = connection.getOutNode();
            if(connection.isEnabled()) {
                genome.addNodeValueById(outNodeId, genome.getNodes().get(inNodeId).getValue() * connection.getWeight());
            }

        }

        LinkedList<Double> toReturn = new LinkedList<Double>();

        for (NodeGene n : genome.getNodes().values()) {
            System.out.println(n.getId() + " -> " + n.getType());
            if(n.getType() == NodeGene.TYPE.OUTPUT) {
                toReturn.add(n.getValue());
            }
        }

        return toReturn;
    }
}

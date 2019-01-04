package NeatAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Evaluator {

    public static LinkedList<Double> evaluateNetwork(Genome genome, List<Double> inputs) {


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


        for (ConnectionGene connection : genome.getConnections().values()) {
            int inNodeId = connection.getInNode();
            int outNodeId = connection.getOutNode();

            genome.setNodeValueById(outNodeId, genome.getNodes().get(inNodeId).getValue() * connection.getWeight());

        }






        LinkedList<Double> toReturn = new LinkedList<Double>();

        for (NodeGene n : genome.getNodes().values()) {
            if(n.getType() == NodeGene.TYPE.OUTPUT) {
                toReturn.add(n.getValue());
            }
        }

        return toReturn;
    }

}

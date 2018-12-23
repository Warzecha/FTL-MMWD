package NeatAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {

    private List<ConnectionGene> connections;
    private List<NodeGenes> nodes;

    Random rng = new Random();









    public void addConnectionMutation(Random rng) {

        List<NodeGenes> possibleInputs = new ArrayList<>();
        List<NodeGenes> possibleOutputs = new ArrayList<>();


        for(NodeGenes n : nodes) {

            if(n.getType() == NodeGenes.TYPE.INPUT) {
                possibleInputs.add(n);
            } else if (n.getType() == NodeGenes.TYPE.OUTPUT) {
                possibleOutputs.add(n);
            } else {
                possibleInputs.add(n);
                possibleOutputs.add(n);
            }
        }

        boolean possibleConnectionFound = false;

        while(!possibleConnectionFound) {

            int inIndex = rng.nextInt(possibleInputs.size());
            int outIndex = rng.nextInt(possibleOutputs.size());



            NodeGenes in = possibleInputs.get(inIndex);
            NodeGenes out = possibleOutputs.get(outIndex);



        }



    }



}

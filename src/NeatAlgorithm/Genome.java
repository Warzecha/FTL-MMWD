package NeatAlgorithm;

import java.util.*;

public class Genome {


    private static int currentMaxInnovationNumber = 0;
    private static int currentMaxNodeId = 0;
    private Map<Integer, ConnectionGene> connections = new HashMap<>();
    private Map<Integer, NodeGene> nodes = new HashMap<>();
    private int fitness = 0;

    Random rng = new Random();


    public void addConnectionGene(ConnectionGene newConnection) {
        connections.put(newConnection.getInnovation(), newConnection);
    }

    public void addNodeGene(NodeGene newNode) {
        nodes.put(newNode.getId(), newNode);
    }


    public static int getNextInnovationNumber() {
        return ++currentMaxInnovationNumber;
    }

    public static int getNextNodeId() {
        return ++currentMaxNodeId;
    }

    public void addConnectionMutation(Random rng) {

        List<NodeGene> possibleInputs = new ArrayList<>();
        List<NodeGene> possibleOutputs = new ArrayList<>();


        for(NodeGene n : nodes.values()) {

            if(n.getType() == NodeGene.TYPE.INPUT) {
                possibleInputs.add(n);
            } else if (n.getType() == NodeGene.TYPE.OUTPUT) {
                possibleOutputs.add(n);
            } else {
                possibleInputs.add(n);
                possibleOutputs.add(n);
            }
        }

        boolean possibleConnectionFound = false;

        NodeGene in = null;
        NodeGene out = null;

        for(int i=0; i < 50 && !possibleConnectionFound; i++) {

            int inIndex = rng.nextInt(possibleInputs.size());
            int outIndex = rng.nextInt(possibleOutputs.size());


            in = possibleInputs.get(inIndex);
            out = possibleOutputs.get(outIndex);

            possibleConnectionFound = true;
            for(ConnectionGene connection : connections.values()) {

                if(connection.getInNode() == in.getId() && connection.getOutNode() == out.getId()) {
                    possibleConnectionFound = false;
                }

            }


        }


        if(possibleConnectionFound) {
            addConnectionGene(new ConnectionGene(in.getId(), out.getId(), rng.nextDouble()*2-1, true, Genome.getNextInnovationNumber()));

        }




    }



    public void addNodeMutation(Random rng) {

        List<ConnectionGene> enabledConnections = new ArrayList<>();
        for(ConnectionGene connection : connections.values()) {

            if (connection.isEnabled()) {
                enabledConnections.add(connection);
            }
        }

        if(enabledConnections.isEmpty()) {
            throw new RuntimeException("No connections to break");
        }

        ConnectionGene connectionToBreak = enabledConnections.get(rng.nextInt(enabledConnections.size()));

        NodeGene newNode = new NodeGene(NodeGene.TYPE.HIDDEN, getNextNodeId());

        ConnectionGene leadingIntoNewNode = new ConnectionGene(connectionToBreak.getInNode(), newNode.getId(), 1, true, getNextInnovationNumber());
        ConnectionGene leadingOutFromNewNode = new ConnectionGene(newNode.getId(), connectionToBreak.getOutNode(), connectionToBreak.getWeight(), true, getNextInnovationNumber());


        addNodeGene(newNode);
        addConnectionGene(leadingIntoNewNode);
        addConnectionGene(leadingOutFromNewNode);

    }


    public Map<Integer, ConnectionGene> getConnections() {
        return connections;
    }

    public Map<Integer, NodeGene> getNodes() {
        return nodes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}

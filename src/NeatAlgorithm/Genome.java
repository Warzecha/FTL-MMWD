package NeatAlgorithm;

import java.util.*;

public class Genome {


    private Map<Integer, ConnectionGene> connections = new HashMap<>();
    private Map<Integer, NodeGene> nodes = new HashMap<>();
    private int inputNodesCount = 0;
    private int outputNodesCount = 0;

    Random rng = new Random();

    public Genome(int inputNodes, int outputNodes) {

        for(int i=0; i < inputNodes; i++) {
            addNodeGene(new NodeGene(NodeGene.TYPE.INPUT, i));
        }

        for(int j=inputNodes; j < outputNodes + inputNodes; j++) {
            addNodeGene(new NodeGene(NodeGene.TYPE.OUTPUT, j));
        }


        HistoricalMarkingsCounter.setCurrentMaxNodeId(inputNodes + outputNodes);
//        System.out.println("After Creation: " + HistoricalMarkingsCounter.peekCurrentMaxNodeId());
    }

    public Genome() {

    }

    public static Genome generateNewGenome(int inputNodes, int outputNodes) {
        Genome newGenome = new Genome(inputNodes, outputNodes);
        Random rng = new Random();

        newGenome.addConnectionGene(new ConnectionGene(rng.nextInt(inputNodes), inputNodes + rng.nextInt(outputNodes), ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));

        return newGenome;
    }

    public void addConnectionGene(ConnectionGene newConnection) {
        connections.put(newConnection.getInnovation(), newConnection);
    }

    public void addNodeGene(NodeGene newNode) {
        nodes.put(newNode.getId(), newNode);
        if(newNode.getType() == NodeGene.TYPE.INPUT) {
            inputNodesCount++;
        } else if (newNode.getType() == NodeGene.TYPE.OUTPUT) {
            outputNodesCount++;
        }
    }

    public Genome(Genome other) {
        this.inputNodesCount = other.inputNodesCount;
        this.outputNodesCount = other.outputNodesCount;

        for (int k : other.getConnections().keySet()) {
            this.connections.put(k, other.getConnections().get(k).copy());
        }

        for (int k : other.getNodes().keySet()) {
            this.nodes.put(k, other.getNodes().get(k).copy());
        }

    }



    public Map<Integer, ConnectionGene> getConnections() {
        return new HashMap<Integer, ConnectionGene>(connections);
    }

    public Map<Integer, NodeGene> getNodes() {
        return new HashMap<Integer, NodeGene>(nodes);
    }

    public void setNodeValueById(int id, double value) {
        nodes.get(id).setValue(value);
    }

    public void addNodeValueById(int id, double value) {
        nodes.get(id).addValue(value);
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
            addConnectionGene(new ConnectionGene(in.getId(), out.getId(), ConnectionGene.randomWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber()));

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
        connectionToBreak.setEnabled(false);

//        System.out.println("Previous node ID: " + HistoricalMarkingsCounter.peekCurrentMaxNodeId());
        NodeGene newNode = new NodeGene(NodeGene.TYPE.HIDDEN, HistoricalMarkingsCounter.getNextNodeId());

//        System.out.println("New node ID: " + newNode.getId());

        ConnectionGene leadingIntoNewNode = new ConnectionGene(connectionToBreak.getInNode(), newNode.getId(), 1, true, HistoricalMarkingsCounter.getNextInnovationNumber());
        ConnectionGene leadingOutFromNewNode = new ConnectionGene(newNode.getId(), connectionToBreak.getOutNode(), connectionToBreak.getWeight(), true, HistoricalMarkingsCounter.getNextInnovationNumber());


        addNodeGene(newNode);
        addConnectionGene(leadingIntoNewNode);
        addConnectionGene(leadingOutFromNewNode);

    }

    public void connectionWeightMutation(Random rng) {

        for(ConnectionGene c : connections.values()) {
            if(rng.nextDouble() <= AlgorithmSettings.PERTURBATION_CHANCE) {
                c.setWeight(c.getWeight() + rng.nextDouble() - 0.5);
            } else {
                c.setWeight(rng.nextDouble() * 2 * (AlgorithmSettings.MAX_CONNECTION_WEIGHT - AlgorithmSettings.MIN_CONNECTION_WEIGHT) + AlgorithmSettings.MIN_CONNECTION_WEIGHT);
            }
        }

    }





    public int getInputNodesCount() {
        return inputNodesCount;
    }

    public int getOutputNodesCount() {
        return outputNodesCount;
    }


    public void resetNodeValues() {
        for( NodeGene n : nodes.values()) {
            n.setValue(0);
        }
    }

    public Genome copy() {return new Genome(this); }






}

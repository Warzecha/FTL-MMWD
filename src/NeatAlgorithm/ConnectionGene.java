package NeatAlgorithm;

import java.util.Random;

public class ConnectionGene {

    private int inNode;
    private int outNode;
    private double weight;
    private boolean enabled;
    private int innovationNumber;

    public ConnectionGene(int inNode, int outNode, double weight, boolean enabled, int innovationNumber) {
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.enabled = enabled;
        this.innovationNumber = innovationNumber;
    }

    public int getInNode() {
        return inNode;
    }

    public void setInNode(int inNode) {
        this.inNode = inNode;
    }

    public int getOutNode() {
        return outNode;
    }

    public void setOutNode(int outNode) {
        this.outNode = outNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double value) {
        if (value < AlgorithmSettings.MIN_CONNECTION_WEIGHT) {
            value = AlgorithmSettings.MIN_CONNECTION_WEIGHT;
        } else if (value > AlgorithmSettings.MAX_CONNECTION_WEIGHT) {
            value = AlgorithmSettings.MAX_CONNECTION_WEIGHT;
        }
        this.weight = value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getInnovation() {
        return innovationNumber;
    }

    public void setInnovation(int innovationNumber) {
        this.innovationNumber = innovationNumber;
    }

    public ConnectionGene copy() {
        return new ConnectionGene(inNode, outNode, weight, enabled, innovationNumber);
    }

    public static double randomWeight() {
        Random rng = new Random();
        return rng.nextDouble() * (AlgorithmSettings.MAX_CONNECTION_WEIGHT - AlgorithmSettings.MIN_CONNECTION_WEIGHT) + AlgorithmSettings.MIN_CONNECTION_WEIGHT;
    }
}

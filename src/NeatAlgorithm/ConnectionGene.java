package NeatAlgorithm;

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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setExpressed(boolean enabled) {
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
}

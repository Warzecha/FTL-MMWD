package NeatAlgorithm;

public class NodeGene {

    enum TYPE {
        INPUT,
        HIDDEN,
        OUTPUT
    }

    private TYPE type;
    private int id;
    private double value;


    public NodeGene(TYPE type, int id) {
        this.type = type;
        this.id = id;
        this.value = 0;
    }

    public NodeGene copy() {
        return new NodeGene(type, id);
    }

    public TYPE getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addValue(double value) { this.value += value; }


}

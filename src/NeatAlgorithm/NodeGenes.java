package NeatAlgorithm;

public class NodeGenes {

    enum TYPE {
        INPUT,
        HIDDEN,
        OUTPUT;
    }

    private TYPE type;
    private int id;

    public NodeGenes(TYPE type, int id) {
        this.type = type;
        this.id = id;
    }

    public TYPE getType() {
        return type;
    }

    public int getId() {
        return id;
    }

}

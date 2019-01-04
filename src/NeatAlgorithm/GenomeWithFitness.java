package NeatAlgorithm;

public class GenomeWithFitness { ;

    private Genome genome;
    private int fitness;

    public GenomeWithFitness(Genome genome, int fitness) {
        this.genome = genome;
        this.fitness = fitness;
    }

    public GenomeWithFitness(Genome genome) {
        this.genome = genome;
        this.fitness = -1;
    }

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }


    public int compareTo(GenomeWithFitness other) {
        if (this.getFitness() > other.getFitness()) {
            return 1;
        } else if (this.getFitness() < other.getFitness()) {
            return -1;
        } else return 0;
    }

}



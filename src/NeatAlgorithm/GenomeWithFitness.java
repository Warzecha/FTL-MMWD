package NeatAlgorithm;

public class GenomeWithFitness { ;

    private Genome genome;
    private double fitness;
    private double adjustedFitness;

    public GenomeWithFitness(Genome genome, double fitness) {
        this.genome = genome;
        this.fitness = fitness;
        this.adjustedFitness = 0;
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

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getAdjustedFitness() {
        return adjustedFitness;
    }

    public void setAdjustedFitness(double adjustedFitness) {
        this.adjustedFitness = adjustedFitness;
    }

    public int compareTo(GenomeWithFitness other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }

}



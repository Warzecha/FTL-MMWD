package NeatAlgorithm;

import java.util.ArrayList;

public class Species {

    private ArrayList<GenomeWithFitness> genomes  = new ArrayList<>();
    private Genome representative;

    public Species(Genome genome) {
        genomes.add(new GenomeWithFitness(genome));
        representative = genome;
    }

    public Genome getRepresentative() {
        return representative;
    }

    public void addGenome(Genome genome) {
        genomes.add(new GenomeWithFitness(genome));
    }
}

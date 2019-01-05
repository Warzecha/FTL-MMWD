package NeatAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Species {


    private ArrayList<GenomeWithFitness> genomesWithFitness  = new ArrayList<>();
    private Genome representative;

    public Species(Genome genome) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
        representative = genome;
    }

    public Genome getRepresentative() {
        return representative;
    }

    public void addGenome(Genome genome) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
    }

    public ArrayList<GenomeWithFitness> getGenomesWithFitness() {
        return genomesWithFitness;
    }

    public List<Genome> getGenomes() {
        List<Genome> toReturn = genomesWithFitness.stream().map(GenomeWithFitness::getGenome).collect(Collectors.toList());
        return toReturn;
    }


//    public void calculateFitness() {
//        for( GenomeWithFitness genomeWithFitness : genomesWithFitness) {
//            genomeWithFitness.setFitness(g);
//        }
//    }

    public void calculateAdjustedFitness() {
        for( GenomeWithFitness genomeWithFitness : genomesWithFitness) {
            genomeWithFitness.setAdjustedFitness(genomeWithFitness.getFitness() / genomesWithFitness.size());
        }
    }

    public int size() {
        return genomesWithFitness.size();
    }

}

package NeatAlgorithm;

import NeatAlgorithm.operators.Enviroment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Species {


    private ArrayList<GenomeWithFitness> genomesWithFitness  = new ArrayList<>();
    private Genome representative;
    int stagnation;

    public Species(Genome genome) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
        representative = genome;
    }

    public Species(Genome genome, int stagnation) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
        representative = genome;
        this.stagnation = stagnation;
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


    public void evaluateFitness(Enviroment env) {
        for( GenomeWithFitness genomeWithFitness : genomesWithFitness) {
            genomeWithFitness.setFitness(env.evaluateFitness(genomeWithFitness.getGenome()));
        }
    }

    public void calculateAdjustedFitness() {
        for( GenomeWithFitness genomeWithFitness : genomesWithFitness) {
            genomeWithFitness.setAdjustedFitness(genomeWithFitness.getFitness() / genomesWithFitness.size());
        }
    }

    public int size() {
        return genomesWithFitness.size();
    }

    public double getTotalAdjustedFitness() {
        return genomesWithFitness.stream().map(GenomeWithFitness::getAdjustedFitness).mapToDouble(Double::doubleValue).sum();
    }

    public GenomeWithFitness getTopGenome() {
        GenomeWithFitness topGenome = null;
        double topGenomeFitness = 0;

        for (GenomeWithFitness gf : genomesWithFitness) {
            if(gf.getFitness() > topGenomeFitness) {
                topGenome = gf;
                topGenomeFitness = topGenome.getFitness();
            }
        }
        return topGenome;
    }

    public int getBiggestGenomeSize() {
        int maxSize = 0;

        for (GenomeWithFitness gf : genomesWithFitness) {
            if( gf.getGenome().getConnections().size() > maxSize) {
                maxSize = gf.getGenome().getConnections().size();
            }
        }
        return maxSize;
    }

    public int getStagnation() {
        return stagnation;
    }
}

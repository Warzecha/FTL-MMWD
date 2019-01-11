package NeatAlgorithm;

import NeatAlgorithm.operators.Environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Species {


    private ArrayList<GenomeWithFitness> genomesWithFitness  = new ArrayList<>();
    private Genome representative;
    private int stagnation;
    private double previousBestFitness;


    public Species(Genome genome) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
        representative = genome;
    }

    public Species(Genome genome, int stagnation) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
        representative = genome;
        this.stagnation = stagnation;
    }

    public Species(Genome genome, int stagnation, double previousBestFitness) {
        genomesWithFitness.add(new GenomeWithFitness(genome));
        representative = genome;
        this.stagnation = stagnation;
        this.previousBestFitness = previousBestFitness;
    }

    public Genome getRepresentative() {
        return representative;
    }

    public void addGenome(Genome genome) {
//        TODO: if possible and needed check if the added Genome is better then current best
        genomesWithFitness.add(new GenomeWithFitness(genome));
    }

    public ArrayList<GenomeWithFitness> getGenomesWithFitness() {
        return genomesWithFitness;
    }

    public List<Genome> getGenomes() {
        List<Genome> toReturn = genomesWithFitness.stream().map(GenomeWithFitness::getGenome).collect(Collectors.toList());
        return toReturn;
    }


    public void evaluateFitness(Environment env) {
        for( GenomeWithFitness genomeWithFitness : genomesWithFitness) {
            genomeWithFitness.setFitness(env.evaluateFitness(genomeWithFitness.getGenome()));
        }

        calculateStagnation();
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
        return Collections.max(genomesWithFitness, Comparator.comparing(GenomeWithFitness::getFitness));
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

    public double getPreviousBestFitness() {
        return previousBestFitness;
    }

    public void calculateStagnation() {
        if(getTopGenome().getFitness() <= previousBestFitness) {
            stagnation++;
        }
    }
}

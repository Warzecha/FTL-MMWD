package NeatAlgorithm;

import NeatAlgorithm.operators.Enviroment;

import java.util.ArrayList;

public class Population {

    private ArrayList<Species> species = new ArrayList<>();
    private int size;

    private final int inputNumber;
    private final int outputNumber;


    public Population(int size, int inputNumber, int outputNumber) {
        this.inputNumber = inputNumber;
        this.outputNumber = outputNumber;

        this.size = size;
        for(int i = 0; i < size; i++) {
            addGenome(Genome.generateNewGenome(inputNumber, outputNumber));
        }
    }

    public void addGenome(Genome genome) {

        for(Species s : species) {

            Genome representative = s.getRepresentative();
            if(GenomeComparator.isSameSpecies(genome, representative)) {
                s.addGenome(genome);
                return;
            }

        }

        species.add(new Species(genome));
    }

    public ArrayList<Species> getSpecies() {
        return species;
    }

    public int getSize() {
        return size;
    }

    public double getPopulationTotalAdjustedFitness() {
        return species.stream().map(Species::getTotalAdjustedFitness).mapToDouble(Double::doubleValue).sum();
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public int getOutputNumber() {
        return outputNumber;
    }

    public void evaluateFitness(Enviroment env) {
        for (Species s : species) {
            s.evaluateFitness(env);
        }
    }

    public GenomeWithFitness getTopGenome() {
        GenomeWithFitness topGenome = null;
        double topGenomeFitness = 0;

        for (Species s : species) {
            GenomeWithFitness gf = s.getTopGenome();
            if(gf.getFitness() > topGenomeFitness) {
                topGenome = gf;
                topGenomeFitness = topGenome.getFitness();
            }
        }
        return topGenome;
    }

}

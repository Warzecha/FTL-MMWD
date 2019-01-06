package NeatAlgorithm;

import java.util.ArrayList;

public class Population {

    private ArrayList<Species> species = new ArrayList<>();
    private int size;


    public Population(int size) {
        this.size = size;
        for(int i = 0; i < size; i++) {
            addGenome(Genome.generateNewGenome(AlgorithmSettings.INPUT_NUMBER, AlgorithmSettings.OUTPUT_NUMBER));
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



}

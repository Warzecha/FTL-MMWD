package NeatAlgorithm;

import java.util.ArrayList;

public class Population {

    ArrayList<Species> species = new ArrayList<>();


    public Population(int size) {
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

}

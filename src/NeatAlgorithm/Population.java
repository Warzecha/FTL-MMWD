package NeatAlgorithm;

import NeatAlgorithm.operators.Enviroment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Population {

    private ArrayList<Species> species = new ArrayList<>();
    private int size;

    private final int inputNumber;
    private final int outputNumber;


    public Population(int size, Genome startingGenome) {
        this.inputNumber = startingGenome.getInputNodesCount();
        this.outputNumber = startingGenome.getOutputNodesCount();

        for(int i = 0; i < size; i++) {
            addGenome(startingGenome.copy(), 0, Double.NEGATIVE_INFINITY);
        }
    }

    public Population(int inputNumber, int outputNumber) {
        this.inputNumber = inputNumber;
        this.outputNumber = outputNumber;
        this.size = 0;
    }


    public void addGenome(Genome genome, int stagnation, double previousFitness) {
        this.size++;
        for(Species s : species) {
            Genome representative = s.getRepresentative();

            if(GenomeComparator.isSameSpecies(genome, representative)) {
                s.addGenome(genome);
                return;
            }
        }
        species.add(new Species(genome, stagnation, previousFitness));

    }

    public ArrayList<Species> getSpecies() {
        return species;
    }

    public int getSize() {
        return species.stream().map(Species::size).mapToInt(Integer::intValue).sum();
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
            s.calculateAdjustedFitness();
        }
    }

    public GenomeWithFitness getTopGenome() {
        return Collections.max(species, Comparator.comparing((Species s) -> s.getTopGenome().getFitness())).getTopGenome();
    }

    public int getBiggestGenomeSize() {
        return species.stream().map(Species::getBiggestGenomeSize).collect(Collectors.summarizingInt(Integer::intValue)).getMax();
    }

    public void killStagnantSpecies() {
        for (Iterator<Species> iterator = species.iterator(); iterator.hasNext(); ) {
            Species s = iterator.next();
            if(s.getStagnation() > AlgorithmSettings.MAX_STAGNATION && species.size() > 1) {
                iterator.remove();
            }
        }

    }

}

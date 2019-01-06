package NeatAlgorithm.operators;

import NeatAlgorithm.GenomeWithFitness;
import NeatAlgorithm.Species;

import java.util.List;

public interface SelectionOparator {

    public List<GenomeWithFitness> applySelection(Species species);
}

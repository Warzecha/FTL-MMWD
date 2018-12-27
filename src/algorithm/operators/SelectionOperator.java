package algorithm.operators;



import NeatAlgorithm.Genome;

import java.util.ArrayList;

public interface SelectionOperator {

    ArrayList<Genome> applySelection(ArrayList<GenomeWithFitness> genomesWithFitness);

}

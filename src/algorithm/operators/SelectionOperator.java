package algorithm.operators;



import game.Genome;

import java.util.ArrayList;

public interface SelectionOperator {

    ArrayList<Genome> applySelection(ArrayList<GenomeWithFitness> genomesWithFitness);

}

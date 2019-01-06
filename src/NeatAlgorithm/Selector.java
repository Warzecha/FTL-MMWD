package NeatAlgorithm;

import NeatAlgorithm.operators.SelectionOparator;

import java.util.ArrayList;
import java.util.List;

public class Selector implements SelectionOparator {


    public List<GenomeWithFitness> applySelection(Species species) {


        ArrayList<GenomeWithFitness> list = new ArrayList<>(species.getGenomesWithFitness());
        list.sort(GenomeWithFitness::compareTo);

        return list.subList(list.size()/2, list.size());
    }

}

package NeatAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SelectorTest {

    Species species;
    Selector selector = new Selector();


    @BeforeEach
    void setup() {
        species = new Species(new Genome(1, 2));

        species.addGenome(new Genome(1, 2));
        species.addGenome(new Genome(1, 2));
        species.addGenome(new Genome(1, 2));
        species.addGenome(new Genome(1, 2));
        species.addGenome(new Genome(1, 2));
        species.addGenome(new Genome(1, 2));
        species.addGenome(new Genome(1, 2));


        for(GenomeWithFitness g : species.getGenomesWithFitness()) {
            g.setFitness(new Random().nextInt(10));
        }

    }


    @Test
    void applySelectionDecresesSizeOfSpecies() {
        assertTrue(selector.applySelection(species).size() < 8);
    }

//    @Test
//    void applySelection() {
//        List<GenomeWithFitness> genomes = selector.applySelection(species);
//
//        for (GenomeWithFitness g : genomes) {
//            System.out.println(g.getFitness());
//        }
//    }




}

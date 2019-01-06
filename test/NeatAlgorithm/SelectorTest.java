package NeatAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }


    @Test
    void applySelectionDecresesSizeOfSpecies() {
        assertTrue(selector.applySelection(species).size() < 8);
    }




}

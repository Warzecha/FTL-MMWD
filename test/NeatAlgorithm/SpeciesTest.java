package NeatAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpeciesTest {

    Species species;
    Genome genome;
    GenomeWithFitness genomeWithFitness;

    @BeforeEach
    void setup() {
        genome = new Genome(3, 2);
//        genomeWithFitness = new GenomeWithFitness(genome);


        species = new Species(genome);
    }

    @Test
    void getRepresentative() {
        assertSame(genome, species.getRepresentative());
    }

    @Test
    void addGenome() {
        assertEquals(1, species.size());
        species.addGenome(new Genome(3,2));
        assertEquals(2, species.size());
    }

    @Test
    void calculateAdjustedFitnessWithSingleGenome() {
        species.getGenomesWithFitness().get(0).setFitness(3);
        species.calculateAdjustedFitness();

        assertEquals(3, species.getGenomesWithFitness().get(0).getAdjustedFitness());
    }

    @Test
    void calculateAdjustedFitnessWithMultipleGenomes() {
        species.addGenome(new Genome(3,2));
        species.addGenome(new Genome(3,2));

        species.getGenomesWithFitness().get(0).setFitness(3);
        species.getGenomesWithFitness().get(1).setFitness(2);
        species.getGenomesWithFitness().get(2).setFitness(1);

        species.calculateAdjustedFitness();

        assertEquals(1, species.getGenomesWithFitness().get(0).getAdjustedFitness());
        assertEquals(0.66, species.getGenomesWithFitness().get(1).getAdjustedFitness(), 0.01);
        assertEquals(0.33, species.getGenomesWithFitness().get(2).getAdjustedFitness(), 0.01);
    }

    @Test
    void getTotalAdjustedFitness() {
        species.addGenome(new Genome(3,2));
        species.addGenome(new Genome(3,2));

        species.getGenomesWithFitness().get(0).setFitness(3);
        species.getGenomesWithFitness().get(1).setFitness(2);
        species.getGenomesWithFitness().get(2).setFitness(1);

        species.calculateAdjustedFitness();

        assertEquals(2, species.getTotalAdjustedFitness(), 0.01);

    }
}
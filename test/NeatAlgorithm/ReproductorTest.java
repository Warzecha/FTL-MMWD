package NeatAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ReproductorTest {

    Population population;

    @BeforeEach
    void setup() {

        population = new Population(11, new Genome(2, 1));

        for (Species s : population.getSpecies()) {
            for (GenomeWithFitness g : s.getGenomesWithFitness()) {
                g.setFitness(2);
            }
            s.calculateAdjustedFitness();
        }
    }



    @Test
    void createNextGenerationMaintainsPopulationSize() {
        assertEquals(11, population.getSize());

        Population newGeneration = Reproductor.createNextGeneration(population);

        assertEquals(11, newGeneration.getSize());
    }


}

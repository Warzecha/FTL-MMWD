package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PopulationTest {

    @Test
    public void testCreateEmptyPopulation() {
        Population population = new Population();
        assertTrue(population.getSize() == 0);
    }

    @Test
    public void testCreateInitialPopulation() {
        Population population = new Population();
        population.createInitialPopulation(20);
        assertTrue(population.getSize() == 20);
    }

}

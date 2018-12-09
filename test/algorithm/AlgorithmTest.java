package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlgorithmTest {

    @Test
    public void testCreatePopulation() {
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        assertTrue(algorithm.createPopulation().getSize()
                == algorithm.settings.POPULATION_SIZE);
    }

}

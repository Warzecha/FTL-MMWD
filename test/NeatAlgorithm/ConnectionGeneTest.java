package NeatAlgorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionGeneTest {

    @Test
    void randomWeight() {

        for (int i=0; i < 100; i++) {
            double randomeWeight = ConnectionGene.randomWeight();
            assertTrue(AlgorithmSettings.MIN_CONNECTION_WEIGHT <= randomeWeight && randomeWeight <= AlgorithmSettings.MAX_CONNECTION_WEIGHT);
        }

    }
}
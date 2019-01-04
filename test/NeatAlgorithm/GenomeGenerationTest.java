package NeatAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GenomeGenerationTest {

    Genome genome;

    @BeforeEach
    void setup() {
        genome = null;
    }

    @Test
    void genomeConstructor() {
        genome = new Genome(3, 5);

        assertEquals(8, genome.getNodes().size());
        assertEquals(3, genome.getInputNodesCount());
        assertEquals(5, genome.getOutputNodesCount());

        long inputNodeCount = genome.getNodes().values().stream().filter((NodeGene n) -> n.getType() == NodeGene.TYPE.INPUT).count();
        long outputNodeCount = genome.getNodes().values().stream().filter((NodeGene n) -> n.getType() == NodeGene.TYPE.OUTPUT).count();

        assertEquals(3, inputNodeCount);
        assertEquals(5, outputNodeCount);
    }

    @Test
    void generateNewGenome() {
        genome = Genome.generateNewGenome(5, 4);

        assertEquals(9, genome.getNodes().size());
        assertEquals(5, genome.getInputNodesCount());
        assertEquals(4, genome.getOutputNodesCount());

        assertEquals(1, genome.getConnections().size());
    }

}

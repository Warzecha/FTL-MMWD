package NeatAlgorithm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenomeMutationTest {


    Genome genome;

    NodeGene n1;
    NodeGene n2;
    NodeGene n3;
    NodeGene n4;

    ConnectionGene c1;
    ConnectionGene c2;

    @BeforeAll
    void setup()
    {
        genome = new Genome();

        n1 = new NodeGene(NodeGene.TYPE.INPUT, Genome.getNextNodeId());
        n2 = new NodeGene(NodeGene.TYPE.HIDDEN, Genome.getNextNodeId());
        n3 = new NodeGene(NodeGene.TYPE.OUTPUT, Genome.getNextNodeId());
        n4 = new NodeGene(NodeGene.TYPE.OUTPUT, Genome.getNextNodeId());
        genome.addNodeGene(n1);
        genome.addNodeGene(n2);
        genome.addNodeGene(n3);
        genome.addNodeGene(n4);

        c1 = new ConnectionGene(0, 1, 1, true, Genome.getNextInnovationNumber());
        c2 = new ConnectionGene(0, 1, 1, true, Genome.getNextInnovationNumber());
        genome.addConnectionGene(c1);
        genome.addConnectionGene(c2);
    }

    @Test
    void addConnectionMutation() {
        Random rng = new Random();
        genome.addConnectionMutation(rng);
        assertEquals(3, genome.getConnections().size());
    }

    @Test
    void addNodeMutation() {
        Random rng = new Random();
        int startingConnectionsSize = genome.getConnections().size();
        genome.addNodeMutation(rng);

        assertEquals(5, genome.getNodes().size());
        assertEquals(startingConnectionsSize + 2, genome.getConnections().size());

    }


    @Test
    void addNodeMutationDisablesTheOldConnection() {
        Random rng = new Random();
        genome.addNodeMutation(rng);

        int disabledCount = 0;
        for (ConnectionGene c : genome.getConnections().values()) {
            if (!c.isEnabled()) {
                disabledCount++;
            }
        }

        assertEquals(1, disabledCount);
    }


}

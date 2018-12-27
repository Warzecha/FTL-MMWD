package NeatAlgorithm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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

        c1 = new ConnectionGene(0, 1, 0.5, true, Genome.getNextInnovationNumber());
        c2 = new ConnectionGene(0, 1, 0.5, true, Genome.getNextInnovationNumber());
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

    @Test
    void connectionWeightMutationResultsInValueInRange() {
        Random rng = new Random();
        for(int i=0; i < 10; i++) {
            genome.connectionWeightMutation(rng);
            for(ConnectionGene c : genome.getConnections().values()) {
//                System.out.println(c.getWeight());
                assertTrue(AlgorithmSettings.MIN_CONNECTION_WEIGHT <= c.getWeight() && c.getWeight() <= AlgorithmSettings.MAX_CONNECTION_WEIGHT);
            }
        }
    }

    @Test
    void connectionWeightMutationChangesTheValue() {
        Random rng = new Random();

        List<Double> oldWeights = genome.getConnections().values().stream().map(ConnectionGene::getWeight).collect(Collectors.toList());

        genome.connectionWeightMutation(rng);
        List<Double> newWeights = genome.getConnections().values().stream().map(ConnectionGene::getWeight).collect(Collectors.toList());

        for(int i=0; i<oldWeights.size(); i++) {
            assertNotEquals(oldWeights.get(i), newWeights.get(i));
        }

    }



}

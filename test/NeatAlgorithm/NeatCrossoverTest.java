package NeatAlgorithm;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NeatCrossoverTest {

    GenomeWithFitness parent1;
    GenomeWithFitness parent2;



    @BeforeAll
    void setup() {

        Genome parent1Genome = new Genome(0, 0);
        Genome parent2Genome = new Genome(0, 0);

        NodeGene p1n1 = new NodeGene(NodeGene.TYPE.INPUT, 1);
        NodeGene p1n2 = new NodeGene(NodeGene.TYPE.INPUT, 2);
        NodeGene p1n3 = new NodeGene(NodeGene.TYPE.INPUT, 3);
        NodeGene p1n4 = new NodeGene(NodeGene.TYPE.OUTPUT, 4);
        NodeGene p1n5 = new NodeGene(NodeGene.TYPE.HIDDEN, 5);
        parent1Genome.addNodeGene(p1n1);
        parent1Genome.addNodeGene(p1n2);
        parent1Genome.addNodeGene(p1n3);
        parent1Genome.addNodeGene(p1n4);
        parent1Genome.addNodeGene(p1n5);

        ConnectionGene p1c1 = new ConnectionGene(1, 4, 2, true, 1);
        ConnectionGene p1c2 = new ConnectionGene(2, 4, 2, false, 2);
        ConnectionGene p1c3 = new ConnectionGene(3, 4, 2, true, 3);
        ConnectionGene p1c4 = new ConnectionGene(2, 5, 2, true, 4);
        ConnectionGene p1c5 = new ConnectionGene(5, 4, 2, true, 5);
        ConnectionGene p1c8 = new ConnectionGene(1, 5, 2, true, 8);
        parent1Genome.addConnectionGene(p1c1);
        parent1Genome.addConnectionGene(p1c2);
        parent1Genome.addConnectionGene(p1c3);
        parent1Genome.addConnectionGene(p1c4);
        parent1Genome.addConnectionGene(p1c5);
        parent1Genome.addConnectionGene(p1c8);


        NodeGene p2n1 = new NodeGene(NodeGene.TYPE.INPUT, 1);
        NodeGene p2n2 = new NodeGene(NodeGene.TYPE.INPUT, 2);
        NodeGene p2n3 = new NodeGene(NodeGene.TYPE.INPUT, 3);
        NodeGene p2n4 = new NodeGene(NodeGene.TYPE.OUTPUT, 4);
        NodeGene p2n5 = new NodeGene(NodeGene.TYPE.HIDDEN, 5);
        NodeGene p2n6 = new NodeGene(NodeGene.TYPE.HIDDEN, 6);
        parent2Genome.addNodeGene(p2n1);
        parent2Genome.addNodeGene(p2n2);
        parent2Genome.addNodeGene(p2n3);
        parent2Genome.addNodeGene(p2n4);
        parent2Genome.addNodeGene(p2n5);
        parent2Genome.addNodeGene(p2n6);

        ConnectionGene p2c1 = new ConnectionGene(1, 4, 1, true, 1);
        ConnectionGene p2c2 = new ConnectionGene(2, 4, 1, false, 2);
        ConnectionGene p2c3 = new ConnectionGene(3, 4, 1, true, 3);
        ConnectionGene p2c4 = new ConnectionGene(2, 5, 1, true, 4);
        ConnectionGene p2c5 = new ConnectionGene(5, 4, 1, false, 5);
        ConnectionGene p2c6 = new ConnectionGene(5, 6, 1, true, 6);
        ConnectionGene p2c7 = new ConnectionGene(6, 4, 1, true, 7);
        ConnectionGene p2c9 = new ConnectionGene(3, 5, 1, true, 9);
        ConnectionGene p2c10 = new ConnectionGene(1, 6, 1, true, 10);

        parent2Genome.addConnectionGene(p2c1);
        parent2Genome.addConnectionGene(p2c2);
        parent2Genome.addConnectionGene(p2c3);
        parent2Genome.addConnectionGene(p2c4);
        parent2Genome.addConnectionGene(p2c5);
        parent2Genome.addConnectionGene(p2c6);
        parent2Genome.addConnectionGene(p2c7);
        parent2Genome.addConnectionGene(p2c9);
        parent2Genome.addConnectionGene(p2c10);

        parent1 = new GenomeWithFitness(parent1Genome, 0);
        parent2 = new GenomeWithFitness(parent2Genome, 1);

    }

    @Test
    void crossGenomesGeneratesChildWithCorrectNumberOfNodes() {
        Genome child = NeatCrossover.crossGenomes(parent1, parent2);
        assertEquals(6, child.getNodes().size());
    }

    @Test
    void crossGenomesGeneratesChildWithCorrectNumberOfConnections() {
        Genome child = NeatCrossover.crossGenomes(parent1, parent2);
        assertEquals(9, child.getConnections().size());
    }

    @Test
    void crossGenomesGeneratesChildWithConnectionWeightsInheritedFromParents() {
        Genome child = NeatCrossover.crossGenomes(parent1, parent2);

        for(ConnectionGene c : child.getConnections().values()) {
            assertTrue(c.getWeight() == 1 || c.getWeight() == 2);
        }
    }

    @Test
    void crossGenomesGeneratesChildWithTheSameEnabledFieldIfBothParentsEnabledFieldsAreEqual() {
        Genome child = NeatCrossover.crossGenomes(parent1, parent2);

        for(int i = 1; i <= 5; i++) {
            if(parent1.getGenome().getConnections().get(i) == parent2.getGenome().getConnections().get(i)) {
                assertEquals(parent1.getGenome().getConnections().get(i).isEnabled(), child.getConnections().get(i).isEnabled());
            }
        }
    }

    @Test
    void crossGenomesGeneratesNewConnections() {
        Genome child = NeatCrossover.crossGenomes(parent1, parent2);

        for(int i = 1; i <= 7; i++) {
            assertNotSame(parent1.getGenome().getConnections().get(i), child.getConnections().get(i));
            assertNotSame(parent2.getGenome().getConnections().get(i), child.getConnections().get(i));
        }
    }


    @Test
    void crossGenomesGeneratesNewNodes() {
        Genome child = NeatCrossover.crossGenomes(parent1, parent2);

        for(int i = 1; i <= 6; i++) {
            assertNotSame(parent1.getGenome().getNodes().get(i), child.getNodes().get(i));
            assertNotSame(parent2.getGenome().getNodes().get(i), child.getNodes().get(i));
        }
    }



}


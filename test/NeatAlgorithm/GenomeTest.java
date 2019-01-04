package NeatAlgorithm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenomeTest {

    Genome genome;

    NodeGene n1;
    NodeGene n2;
    NodeGene n3;

    ConnectionGene c1;
    ConnectionGene c2;

    @BeforeAll
    void setup()
    {
        Genome.resetInnovationNumber();
        Genome.resetNodeId();

        genome = new Genome();

        n1 = new NodeGene(NodeGene.TYPE.INPUT, Genome.getNextNodeId());
        n2 = new NodeGene(NodeGene.TYPE.HIDDEN, Genome.getNextNodeId());
        n3 = new NodeGene(NodeGene.TYPE.OUTPUT, Genome.getNextNodeId());

        genome.addNodeGene(n1);
        genome.addNodeGene(n2);
        genome.addNodeGene(n3);

        c1 = new ConnectionGene(0, 1, 1, true, Genome.getNextInnovationNumber());
        c2 = new ConnectionGene(0, 1, 1, true, Genome.getNextInnovationNumber());
        genome.addConnectionGene(c1);
        genome.addConnectionGene(c2);
    }

    @Test
    void addConnectionGeneIncrementsInnovationNumber() {
        assertEquals(1, c1.getInnovation());
        assertEquals(2, c2.getInnovation());
    }

    @Test
    void addNodeGeneIncrementsNodeId() {
        assertEquals(1, n1.getId());
        assertEquals(2, n2.getId());
        assertEquals(3, n3.getId());
    }

    @Test
    void addConnectionGeneIncrementsConnectionsMapSize() {
        assertEquals(2, genome.getConnections().size());
    }

    @Test
    void addNodeGeneIncrementsNodesMapSize() {
        assertEquals(3, genome.getNodes().size());
    }


    @Test
    void setWeightFitsValueInRange() {
        c1.setWeight(2);
        assertEquals(1, c1.getWeight());

        c2.setWeight(-2);
        assertEquals(-1, c2.getWeight());
    }


    @Test
    void getInputNodesCount() {
        assertEquals(1, genome.getInputNodesCount());
    }


    @Test
    void getOutputNodesCount() {
        assertEquals(1, genome.getOutputNodesCount());
    }

    @Test
    void setNodeValue() {
        n1.setValue(3.14);

        assertEquals(3.14, n1.getValue());
    }

    @Test
    void resetNodeValues() {
        n1.setValue(1);
        n2.setValue(2);
        n3.setValue(3);

        genome.resetNodeValues();

        for (NodeGene n : genome.getNodes().values()) {
            assertEquals(0, n.getValue());
        }
    }

    @Test
    void setNodeValueById() {
        genome.setNodeValueById(1, 17.5);
        assertEquals(n1.getValue(), 17.5);
    }


}


package NeatAlgorithm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenomeSpeciesTest {


    private GenomeWithFitness parent1;
    private GenomeWithFitness parent2;

    @BeforeEach
    void setup()
    {
        Genome parent1Genome = new Genome();
        Genome parent2Genome = new Genome();

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

        ConnectionGene p1c1 = new ConnectionGene(1, 4, -1, true, 1);
        ConnectionGene p1c2 = new ConnectionGene(2, 4, -1, false, 2);
        ConnectionGene p1c3 = new ConnectionGene(3, 4, -1, true, 3);
        ConnectionGene p1c4 = new ConnectionGene(2, 5, -1, true, 4);
        ConnectionGene p1c5 = new ConnectionGene(5, 4, -1, true, 5);
        ConnectionGene p1c8 = new ConnectionGene(1, 5, -1, true, 8);
        parent1Genome.addConnectionGene(p1c1);
        parent1Genome.addConnectionGene(p1c2);
        parent1Genome.addConnectionGene(p1c3);
        parent1Genome.addConnectionGene(p1c4);
        parent1Genome.addConnectionGene(p1c5);
        parent1Genome.addConnectionGene(p1c8);


        parent2Genome = new Genome();

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
    void getGeneticDistanceOfTheSameGenome() {

        assertEquals(0, Genome.getGeneticDistance(parent1.getGenome(), parent1.getGenome()));

    }

    @Test
    void getGeneticDistance() {

        double expected = 2 * AlgorithmSettings.C1 / 9 + 3 * AlgorithmSettings.C2 / 9 + AlgorithmSettings.C3 * 2;
        assertEquals(expected, Genome.getGeneticDistance(parent1.getGenome(), parent2.getGenome()));

    }

}

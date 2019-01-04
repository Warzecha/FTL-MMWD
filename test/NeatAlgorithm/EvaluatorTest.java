package NeatAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {


    Genome genome;

    NodeGene n0;
    NodeGene n1;
    NodeGene n2;
    NodeGene n3;
    NodeGene n4;
    NodeGene n5;
    NodeGene n6;

    ConnectionGene c0;
    ConnectionGene c1;
    ConnectionGene c2;
    ConnectionGene c3;
    ConnectionGene c4;
    ConnectionGene c5;
    ConnectionGene c6;


    @BeforeEach
    void setup()
    {

        Genome.resetNodeId();
        Genome.resetInnovationNumber();

        genome = new Genome();

        n0 = new NodeGene(NodeGene.TYPE.INPUT, Genome.getNextNodeId());
        n1 = new NodeGene(NodeGene.TYPE.INPUT, Genome.getNextNodeId());

        n2 = new NodeGene(NodeGene.TYPE.OUTPUT, Genome.getNextNodeId());
        n3 = new NodeGene(NodeGene.TYPE.OUTPUT, Genome.getNextNodeId());
        n4 = new NodeGene(NodeGene.TYPE.OUTPUT, Genome.getNextNodeId());

        n5 = new NodeGene(NodeGene.TYPE.HIDDEN, Genome.getNextNodeId());
        n6 = new NodeGene(NodeGene.TYPE.HIDDEN, Genome.getNextNodeId());

        genome.addNodeGene(n0);
        genome.addNodeGene(n1);
        genome.addNodeGene(n2);
        genome.addNodeGene(n3);
        genome.addNodeGene(n4);
        genome.addNodeGene(n5);
        genome.addNodeGene(n6);


        c0 = new ConnectionGene(0, 5, 0.5, true, Genome.getNextInnovationNumber());
        c1 = new ConnectionGene(0, 6, 0.5, true, Genome.getNextInnovationNumber());
        c2 = new ConnectionGene(1, 5, 0.5, true, Genome.getNextInnovationNumber());
        c3 = new ConnectionGene(1, 6, 0.5, true, Genome.getNextInnovationNumber());
        c4 = new ConnectionGene(5, 2, 0.5, true, Genome.getNextInnovationNumber());
        c5 = new ConnectionGene(5, 3, 0.5, true, Genome.getNextInnovationNumber());
        c6 = new ConnectionGene(6, 3, 0.5, true, Genome.getNextInnovationNumber());


        genome.addConnectionGene(c0);
        genome.addConnectionGene(c1);
        genome.addConnectionGene(c2);
        genome.addConnectionGene(c3);
        genome.addConnectionGene(c4);
        genome.addConnectionGene(c5);
        genome.addConnectionGene(c6);



    }


    @Test
    void evaluateNetworkWithInputOfZeros() {
        List<Double> input = new ArrayList<Double>(Arrays.asList(0.0, 0.0));
        List<Double> expected = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0));

        List<Double> output = Evaluator.evaluateNetwork(genome, input);

        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), output.get(i));
        }
    }
}
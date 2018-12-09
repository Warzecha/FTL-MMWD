package algorithm;

import algorithm.operators.GenomeWithFitness;
import game.Genome;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenomeWithFitnessTest {

    @Test
    public void testComparison() {
        ArrayList<GenomeWithFitness> gwf = new ArrayList<>();
        gwf.add(new GenomeWithFitness(new Genome(0), 1));
        gwf.add(new GenomeWithFitness(new Genome(0), 4));
        gwf.add(new GenomeWithFitness(new Genome(0), 2));
        gwf.add(new GenomeWithFitness(new Genome(0), 3));
        gwf.sort(GenomeWithFitness::compareTo);
        assertTrue(gwf.get(0).getFitness() == 1);
        assertTrue(gwf.get(1).getFitness() == 2);
        assertTrue(gwf.get(2).getFitness() == 3);
        assertTrue(gwf.get(3).getFitness() == 4);
    }

}

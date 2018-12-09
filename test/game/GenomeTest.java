package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenomeTest {

    @Test
    void getLength() {

        int length = 7;
        Genome g1 = new Genome(length);
        Genome g2 = new Genome(length, 3);

        assertEquals(length, g1.getLength());
        assertEquals(length, g1.getLength());

    }


    @Test
    void getGene()
    {
        int length = 7;
        double value = 3;
        Genome g = new Genome(length, value);

        for (int i = 0; i < length; i++)
        {
            assertEquals(value, g.getGene(i));
        }


    }


    @Test
    void cross() {


        int length = 7;
        double value = 0.5;

        Genome g1 = new Genome(length, value);
        Genome g2 = new Genome(length, value);
        Genome g3 = new Genome(length + 1);



        Genome crossedGenome = g1.cross(g2);

        for(int i = 0; i < length; i++)
        {
            assertEquals(value, crossedGenome.getGene(i));

        }


    }
}
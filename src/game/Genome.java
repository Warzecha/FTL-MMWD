package game;

import java.util.Random;

public class Genome {


    double [] arr;

    private Random r = new Random();



    public Genome(int length)
    {
        arr = new double [length];

        for(int i = 0; i < length; i++)
        {
            arr[i] = r.nextDouble();

        }
    }



    public Genome(int length, double fillWith)
    {
        arr = new double [length];

        for(int i = 0; i < length; i++)
        {
            arr[i] = fillWith;

        }
    }



    public int getLength() { return arr.length; }

    public double getGene(int i) {
        if (i >= arr.length) {
            throw new IllegalArgumentException("Gene index is too big! Index: " + i + ", size: " + arr.length);
        }
        return arr[i];
    }

    public void setGene(int i, double gene) {
        if (i >= arr.length) {
            throw new IllegalArgumentException("Gene index is too big! Index: " + i + ", size: " + arr.length);
        }
        arr[i] = gene;
    }


    Genome cross(Genome other)
    {

        if(this.getLength() != other.getLength())
            throw new RuntimeException("Genomes must be of the same length");


        Genome newGenome = new Genome(this.getLength(), 0);



        for(int i = 0; i < arr.length; i++)
        {
            double random = r.nextDouble();

            if(random >= 0.5)
            {
                newGenome.arr[i] = this.arr[i];

            }else
            {
                newGenome.arr[i] = other.arr[i];
            }


        }



        return newGenome;
    }


}

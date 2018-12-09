package game;

import java.util.Random;

public class Genome {


    double [] arr;

    private Random r = new Random();



    Genome(int length)
    {
        arr = new double [length];

        for(int i = 0; i < length; i++)
        {
            arr[i] = r.nextDouble();

        }
    }



    Genome(int length, double fillWith)
    {
        arr = new double [length];

        for(int i = 0; i < length; i++)
        {
            arr[i] = fillWith;

        }
    }



    int getLength() { return arr.length; }

    double getGene(int i) {return arr[i]; }



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

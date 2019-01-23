package evoNeat;

import java.util.Random;

public class ConnectionGene {

    private int into,out, innovation;
    private float weight;
    private boolean enabled;

    public ConnectionGene(int into, int out, int innovation, float weight, boolean enabled) {
        this.into = into;
        this.out = out;
        this.innovation = innovation;
        this.weight = weight;
        this.enabled = enabled;
    }

    // Copy
    public ConnectionGene(ConnectionGene connectionGene){
        if(connectionGene!=null) {
            this.into = connectionGene.getInto();
            this.out = connectionGene.getOut();
            this.innovation = connectionGene.getInnovation();
            this.weight = connectionGene.getWeight();
            this.enabled = connectionGene.isEnabled();
        }
    }

    public static float randomWeight() {
        Random rng = new Random();
        return (rng.nextFloat() * (AlgorithmSettings.MAX_CONNECTION_WEIGHT - AlgorithmSettings.MIN_CONNECTION_WEIGHT) + AlgorithmSettings.MIN_CONNECTION_WEIGHT);
    }

    public int getInto() {
        return into;
    }

    public int getOut() {
        return out;
    }

    public int getInnovation() {
        return innovation;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public String toString() {
/*        return "ConnectionGene{" +
                "into=" + into +
                ", out=" + out +
                ", innovation=" + innovation +
                ", weight=" + weight +
                ", enabled=" + enabled +
                '}';*/
        return into+","+out+","+weight+","+enabled;
    }
}

package game;

import java.util.Random;

public class GenomeAttribute {

    private double value;
    private final double minimumValue;
    private final double maximumValue;

    public GenomeAttribute(double min, double max, double value) {
        this.minimumValue = min;
        this.maximumValue = max;
        this.value = value;
    }

    public void mutate(double mutationPercentage) {
        double mutation = Math.abs(
                new Random().nextInt()%(mutationPercentage*2 + 1)
        ) - mutationPercentage;
        double mutationFactor = 1.0 + mutation/1000.0;
        value *= mutationFactor;
        fitValueInRange();
        System.out.println(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private void fitValueInRange() {
        if (value < minimumValue) {
            value = minimumValue;
        } else if (value > maximumValue) {
            value = maximumValue;
        }
    }

}

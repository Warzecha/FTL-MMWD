package algorithm;

import algorithm.model.ShipStrategy;

import java.util.ArrayList;

public class Population {

    private ArrayList<ShipStrategy> elements;

    public Population() {
        this.elements = new ArrayList<>();
    }

    public int getSize() {
        return elements.size();
    }

    public void createInitialPopulation(int size) {
        for(int i = 0; i < size; i++) {
            elements.add(new ShipStrategy());
        }
    }

    public void mutation() {

    }

    public void crossover() {

    }

    public void selection() {

    }

}

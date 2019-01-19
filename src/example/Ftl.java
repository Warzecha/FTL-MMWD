package example;

import evoNeat.Environment;
import evoNeat.Genome;
import game.Battle;
import game.Ship;

import java.util.ArrayList;

public class Ftl extends Environment {

    ArrayList<Genome> testGroup;


    public Ftl(int inputsNumber, int outputsNumber, int populationSize, int maxGenerations) {
        super(inputsNumber, outputsNumber, populationSize, maxGenerations);

        testGroup = new ArrayList<>();
    }

    @Override
    public void evaluateFitness(Genome genome) {
        float fitness = 0;

        Ship ship = new Ship(genome);

        for(Genome enemy : testGroup) {
            Ship enemyShip = new Ship(enemy);
            Battle battle = new Battle(ship, enemyShip, 100);

            fitness += battle.fight();

        }

        genome.setFitness(fitness);
    }

    @Override
    public Genome generateStartingGenome() {
        Genome startingGenome = new Genome(this.inputsNumber, this.outputsNumber);
        return startingGenome;
    }


    public static void main(String[] args) {
        Ftl ftl = new Ftl(2, 1, 400, 600);
        ftl.initPopulation();
        ftl.startLoop();
    }
}

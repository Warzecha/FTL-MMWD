package example;

import evoNeat.Environment;
import evoNeat.Genome;
import game.Battle;
import game.Ship;
import game.exception.NoSuchRoomException;

import java.util.ArrayList;

public class Ftl extends Environment {

    ArrayList<Genome> testGroup;


    public Ftl(int inputsNumber, int outputsNumber, int populationSize, int maxGenerations) {
        super(inputsNumber, outputsNumber, populationSize, maxGenerations);

        testGroup = new ArrayList<>();


        for(int i=0; i < 4; i++) {
            testGroup.add(generateStartingGenome());
        }
    }

    @Override
    public void evaluateFitness(Genome genome) {
        float fitness = 0;

        Ship ship = null;
        try {
            ship = new Ship(4, genome);
        } catch (NoSuchRoomException e) {
            e.printStackTrace();
        }

        for(Genome enemy : testGroup) {
            Ship enemyShip = null;
            try {
                enemyShip = new Ship(4, enemy);
            } catch (NoSuchRoomException e) {
                e.printStackTrace();
            }
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
        Ftl ftl = new Ftl(14, 12, 400, 600);
        ftl.initPopulation();
        ftl.startLoop();
    }
}

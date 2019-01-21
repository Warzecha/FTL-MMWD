package example;

import evoNeat.*;
import game.Battle;
import game.Room;
import game.Ship;
import game.exception.NoSuchRoomException;

import java.util.ArrayList;
import java.util.Random;

public class Ftl extends Environment {

    ArrayList<Genome> testGroup;
    int mostEnemiesDefeated = 0;


    public Ftl(int inputsNumber, int outputsNumber, int populationSize, int maxGenerations) {
        super(inputsNumber, outputsNumber, populationSize, maxGenerations);

        testGroup = new ArrayList<>();


        for(int i = 0; i <= 5; i++) {
            Genome enemy = new Genome(this.inputsNumber, this.outputsNumber);
            enemy.addConnectionGene(new ConnectionGene(inputsNumber, inputsNumber + AlgorithmSettings.HIDDEN_NODES + i, 0, 1, true));
            testGroup.add(enemy);
        }


    }

    @Override
    public void evaluateFitness(Genome genome) {
        float fitness = 0;
        int enemiesDefeated = 0;
        Random rng = new Random();

        Ship ship = null;
        Ship enemyShip = null;
//        System.out.println("========================================");

        for (int i = 1; i < 4; i++) {

            for(Genome enemy : testGroup) {

                try {
                    ship = new Ship(4, genome);
                    enemyShip = new Ship(4, enemy);

                    enemyShip.getCrew().get(0).moveTo(Room.OXYGEN.getId());
                    for (int j = 1; j < 4; j++) {
                        enemyShip.getCrew().get(j).moveTo(rng.nextInt(6));
                    }

                    enemyShip.setHull(i * 30);

                } catch (NoSuchRoomException e) {
                    e.printStackTrace();
                }
                Battle battle = new Battle(ship, enemyShip, 100);

                int result = battle.fight();
                fitness += result;
//                System.out.println(battle.getShip1().getHull() + " : " + battle.getShip2().getHull());
////                System.out.println(battle.getShip2().isDead());
//                System.out.println(battle.getShip2().crewIsAlive());
                if(battle.getShip2().isDead()) {
                    enemiesDefeated++;
                }

            }

        }

        mostEnemiesDefeated = Math.max(mostEnemiesDefeated, enemiesDefeated);
        genome.setFitness(fitness);
    }

    @Override
    public Genome generateStartingGenome() {
        Genome startingGenome = new Genome(this.inputsNumber, this.outputsNumber);
        return startingGenome;
    }

    @Override
    public void iterate() {


        generationNumber++;
        mostEnemiesDefeated = 0;
        population.evaluateFitness(this);
        topGenome = population.getTopGenome();
        System.out.println("Generation: " + generationNumber);
        System.out.println("TopFitness: " + topGenome.getPoints());
        System.out.println("Population: " + population.getSize());
        System.out.println("Best Size: " + population.getTopGenome().size());
        System.out.println("Species: " + population.getSpecies().size());
        System.out.println("Enemies defeated: " + mostEnemiesDefeated );
        System.out.println("\n\n");
        population.breedNewGeneration();
    }


    public static void main(String[] args) {
        Ftl ftl = new Ftl(14, 12, 200, 100);
        ftl.initPopulation();
        ftl.startLoop();
    }
}

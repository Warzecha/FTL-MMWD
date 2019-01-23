package evoNeat;

public abstract class Environment {

     public int inputsNumber;
     public int outputsNumber;
     public int maxGenerations;
     public int populationSize;
     public int generationNumber = 0;
     public Population population;
     public Genome topGenome;


     public Environment(int inputsNumber, int outputsNumber, int populationSize, int maxGenerations) {
          this.inputsNumber = inputsNumber;
          this.outputsNumber = outputsNumber;
          this.populationSize = populationSize;
          this.maxGenerations = maxGenerations;
     }

     public abstract void evaluateFitness(Genome genome);

     public abstract Genome generateStartingGenome();

     public void initPopulation() {
          population = new Population(populationSize, generateStartingGenome());
     }

     public void iterate() {

          generationNumber++;
          population.evaluateFitness(this);
          topGenome = population.getTopGenome();
//          System.out.println("Generation: " + generationNumber);
//          System.out.println("TopFitness: " + topGenome.getPoints());
//          System.out.println("Population: " + population.getSize());
//          System.out.println("Best Size: " + population.getTopGenome().size());
//          System.out.println("Species: " + population.getSpecies().size());
//          System.out.println("\n\n");
          population.breedNewGeneration();
     }


     public void startLoop() {
          topGenome = population.getTopGenome();
          for (int i = 0; i < maxGenerations; i++) {
               iterate();
          }
          population.evaluateFitness(this);
     }


     public static void main(String[] args) {
     }

}

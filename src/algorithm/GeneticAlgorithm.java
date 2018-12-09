package algorithm;

public class GeneticAlgorithm {

    public final AlgorithmSettings settings = new AlgorithmSettings();

    public static void main(String[] args) {
        System.out.println("Starting Genetic Algorithm");



    }

    public Population createPopulation() {
        Population population = new Population();
        population.createInitialPopulation(settings.POPULATION_SIZE);
        return population;
    }

}

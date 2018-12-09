package algorithm;

import algorithm.operators.*;
import game.Genome;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PopulationTest {

    @Test
    public void testCreateEmptyPopulation() {
        Population population = new Population();
        assertTrue(population.getSize() == 0);
    }

    @Test
    public void testCreateInitialPopulation() {
        Population population = new Population();
        population.createInitialPopulation(20);
        assertTrue(population.getSize() == 20);
    }

    @Test
    public void testCrossover() {
        CrossoverOperator crossoverOperator = new CrossoverOperator() {
            @Override
            public void crossGenomes(Genome genomeA, Genome genomeB) {
                /* swapping first gene of two genomes,
                   given 0, 1, 2 will result in 0, 2, 1:
                   0   1   2
                   1<->0   2
                   1   2<->0
                  >0   2   1< (crossing last with first)
                 */
                double geneA = genomeA.getGene(0);
                double geneB = genomeB.getGene(0);
                genomeA.setGene(0, geneB);
                genomeB.setGene(0, geneA);
            }
        };
        Population population = new Population();
        population.getGenomes().add(simpleGenome(0));
        population.getGenomes().add(simpleGenome(1));
        population.getGenomes().add(simpleGenome(2));
        population.crossover(crossoverOperator);
        assertTrue(population.getGenomes().get(0).getGene(0) == 0);
        assertTrue(population.getGenomes().get(1).getGene(0) == 2);
        assertTrue(population.getGenomes().get(2).getGene(0) == 1);
    }

    @Test
    public void testMutation() {
        MutationOperator mutationOperator = new MutationOperator() {
            @Override
            public void mutateGenome(Genome genome) {
                // adding 1 to first gene
                genome.setGene(0, genome.getGene(0) + 1);
            }
        };
        Population population = new Population();
        population.getGenomes().add(simpleGenome(10));
        population.getGenomes().add(simpleGenome(20));
        population.getGenomes().add(simpleGenome(30));
        population.mutation(mutationOperator);
        assertTrue(population.getGenomes().get(0).getGene(0) == 11);
        assertTrue(population.getGenomes().get(1).getGene(0) == 21);
        assertTrue(population.getGenomes().get(2).getGene(0) == 31);
    }

    @Test
    public void testSelection() {
        SelectionOperator selectionOperator = new SelectionOperator() {
            @Override
            public ArrayList<Genome> applySelection(ArrayList<GenomeWithFitness> genomesWithFitness) {
                // 10-points-fitness selection
                ArrayList<Genome> genomes = new ArrayList<>();
                for(GenomeWithFitness gwf : genomesWithFitness) {
                    if (gwf.getFitness() >= 10) {
                        genomes.add(gwf.getGenome());
                    }
                }
                return genomes;
            }
        };
        FitnessOperator fitnessOperator = new FitnessOperator() {
            @Override
            public int getFitness(Genome genome) {
                // return first gene * 2
                return (int)genome.getGene(0)*2;
            }
        };
        Population population = new Population();
        population.getGenomes().add(simpleGenome(2));
        population.getGenomes().add(simpleGenome(4));
        population.getGenomes().add(simpleGenome(6));
        population.getGenomes().add(simpleGenome(6));
        population.selection(fitnessOperator, selectionOperator);
        assertTrue(population.getSize() == 2);
        assertTrue(population.getGenomes().get(0).getGene(0) == 6);
        assertTrue(population.getGenomes().get(1).getGene(0) == 6);
    }

    @Test
    public void testMultiplication() {
        Population population = new Population();
        population.getGenomes().add(simpleGenome(1));
        population.getGenomes().add(simpleGenome(2));
        population.multiplication();
        assertTrue(population.getSize() == 4);
        assertTrue(population.getGenomes().get(0).getGene(0)
                == population.getGenomes().get(2).getGene(0));
        assertTrue(population.getGenomes().get(1).getGene(0)
                == population.getGenomes().get(3).getGene(0));
    }

    private Genome simpleGenome(double geneValue) {
        return new Genome(1, geneValue);
    }

}

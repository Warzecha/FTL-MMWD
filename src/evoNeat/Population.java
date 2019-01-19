package evoNeat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Population {


    private ArrayList<Species> species = new ArrayList<>();
    private int generations = 0;
    private float topFitness ;
    private int poolStaleness = 0;
    private int targetSize;


    public Population(int size, Genome startingGenome) {
        this.targetSize = size;

        for(int i = 0; i < size; i++) {
            addToSpecies(startingGenome.copy());
        }
    }

    public ArrayList<Species> getSpecies() {
        return species;
    }

//    public void initializePool() {
//
//        for (int i = 0; i < AlgorithmSettings.POPULATION; i++) {
//            addToSpecies(new Genome());
//        }
//    }




    public void addToSpecies(Genome g) {
        for (Species s : species) {
            if (s.getGenomes().size() == 0)
                continue;
            Genome g0 = s.getGenomes().get(0);
//		System.out.println(s.genomes.size());
            if (Genome.isSameSpecies(g, g0)) {
                s.getGenomes().add(g);
                return;
            }
        }
        Species childSpecies = new Species();
        childSpecies.getGenomes().add(g);
        species.add(childSpecies);
    }



    public void evaluateFitness(Environment environment){

        ArrayList<Genome> allGenomes = extractGenomes();

        for(Genome g : allGenomes) {
            environment.evaluateFitness(g);
        }

        rankGlobally();
    }

    public ArrayList<Genome> extractGenomes() {
        ArrayList<Genome> allGenomes = new ArrayList<>();

        for(Species s: species){
            allGenomes.addAll(s.getGenomes());
        }
        return allGenomes;
    }

    // experimental
    private void rankGlobally(){                // set fitness to rank
        ArrayList<Genome> allGenomes = extractGenomes();
        Collections.sort(allGenomes);
  //      allGenomes.get(allGenomes.size()-1).writeTofile();
 //       System.out.println("TopFitness : "+ allGenomes.get(allGenomes.size()-1).getFitness());
        for (int i =0 ; i<allGenomes.size(); i++) {
            allGenomes.get(i).setPoints(allGenomes.get(i).getFitness());      //TODO use adjustedFitness and remove points
            allGenomes.get(i).setFitness(i);
        }
    }

    public Genome getTopGenome(){
        ArrayList<Genome> allGenomes = extractGenomes();
        Collections.sort(allGenomes,Collections.reverseOrder());

        return allGenomes.get(0);
    }
    // all species must have the totalAdjustedFitness calculated
    public float calculateGlobalAdjustedFitness() {
        float total = 0;
        for (Species s : species) {
            total += s.getTotalAdjustedFitness();
        }
        return total;
    }

    public void removeWeakGenomesFromSpecies(boolean allButOne){
        for(Species s: species){
            s.removeWeakGenomes(allButOne);
        }
    }

    public void removeStaleSpecies(){
        ArrayList<Species> survived = new ArrayList<>();

        if(topFitness<getTopFitness()){
            poolStaleness = 0;
        }

        for(Species s: species){
            Genome top  = s.getTopGenome();
            if(top.getFitness()>s.getTopFitness()){
                s.setTopFitness(top.getFitness());
                s.setStaleness(0);
            }
            else{
                s.setStaleness(s.getStaleness()+1);     // increment staleness
            }

            if(s.getStaleness()< AlgorithmSettings.STALE_SPECIES || s.getTopFitness()>= this.getTopFitness()){
                survived.add(s);
            }
        }

        Collections.sort(survived,Collections.reverseOrder());

        if(poolStaleness>AlgorithmSettings.STALE_POOL){
            for(int i = survived.size(); i>1 ;i--)
            survived.remove(i);
        }

        species = survived;
        poolStaleness++;
    }

    public void calculateGenomeAdjustedFitness(){
        for (Species s: species) {
            s.calculateGenomeAdjustedFitness();
        }
    }
    public ArrayList<Genome> breedNewGeneration() {


        calculateGenomeAdjustedFitness();
        ArrayList<Species> survived = new ArrayList<>();

        removeWeakGenomesFromSpecies(false);
        removeStaleSpecies();
        float globalAdjustedFitness = calculateGlobalAdjustedFitness();
        ArrayList<Genome> children = new ArrayList<>();
        float carryOver = 0;
        for (Species s : species) {
            float fchild = targetSize * (s.getTotalAdjustedFitness() / globalAdjustedFitness) ;//- 1;       // reconsider
            int nchild = (int) fchild;
            carryOver += fchild - nchild;
            if (carryOver > 1) {
                nchild++;
                carryOver -= 1;
            }

            if(nchild < 1)
                continue;

            survived.add(new Species(s.getTopGenome()));
            //s.removeWeakGenome(nchild);

            //children.add(s.getTopGenome());
            for (int i = 1; i < nchild; i++) {
                Genome child = s.breedChild();
                children.add(child);
            }


        }
        species = survived;
        for (Genome child: children)
            addToSpecies(child);
        //clearInnovations();
        generations++;
        return children;
    }

    public float getTopFitness(){
        float topFitness = 0;
        Genome topGenome =null;
        for(Species s : species){
            topGenome = s.getTopGenome();
            if(topGenome.getFitness()>topFitness){
                topFitness = topGenome.getFitness();
            }
        }
        return topFitness;
    }

    public int getCurrentPopulation() {
        int p = 0;
        for (Species s : species)
            p += s.getGenomes().size();
        return p;
    }


    public int getSize() {
        return species.stream().map(Species::size).mapToInt(Integer::intValue).sum();
    }

    public int getBiggestGenomeSize() {
        return species.stream().map(Species::getBiggestGenomeSize).collect(Collectors.summarizingInt(Integer::intValue)).getMax();
    }
}

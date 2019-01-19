package evoNeat;

import javax.management.RuntimeErrorException;
import java.io.*;
import java.util.*;

/**
 * Created by vishnughosh on 28/02/17.
 */
public class Genome implements Comparable {
    private static Random rand = new Random();
    private float fitness;                                          // Global Percentile Rank (higher the better)
    private float points;
    private ArrayList<ConnectionGene> connectionGeneList = new ArrayList<>();           // DNA- MAin archive of gene information
    private TreeMap<Integer, NodeGene> nodes = new TreeMap<>();                          // Generated while performing network operation
    private float adjustedFitness;                                      // For number of child to breed in species


    private int inputCount;
    private int outputCount;

    private HashMap<MutationKeys, Float> mutationRates = new HashMap<>();

    public void addConnectionGene(ConnectionGene connectionGene) {
        connectionGeneList.add(connectionGene);
    }


    private enum MutationKeys {
        STEPS,
        PERTURB_CHANCE,
        WEIGHT_CHANCE,
        WEIGHT_MUTATION_CHANCE,
        NODE_MUTATION_CHANCE,
        CONNECTION_MUTATION_CHANCE,
        BIAS_CONNECTION_MUTATION_CHANCE,
        DISABLE_MUTATION_CHANCE,
        ENABLE_MUTATION_CHANCE
    }

    public Genome(int inputCount, int outputCount){

        this.inputCount = inputCount;
        this.outputCount = outputCount;

        this.mutationRates.put(MutationKeys.STEPS, AlgorithmSettings.STEPS);
        this.mutationRates.put(MutationKeys.PERTURB_CHANCE, AlgorithmSettings.PERTURB_CHANCE);
        this.mutationRates.put(MutationKeys.WEIGHT_CHANCE,AlgorithmSettings.WEIGHT_CHANCE);
        this.mutationRates.put(MutationKeys.WEIGHT_MUTATION_CHANCE, AlgorithmSettings.WEIGHT_MUTATION_CHANCE);
        this.mutationRates.put(MutationKeys.NODE_MUTATION_CHANCE , AlgorithmSettings.NODE_MUTATION_CHANCE);
        this.mutationRates.put(MutationKeys.CONNECTION_MUTATION_CHANCE , AlgorithmSettings.CONNECTION_MUTATION_CHANCE);
        this.mutationRates.put(MutationKeys.BIAS_CONNECTION_MUTATION_CHANCE , AlgorithmSettings.BIAS_CONNECTION_MUTATION_CHANCE);
        this.mutationRates.put(MutationKeys.DISABLE_MUTATION_CHANCE , AlgorithmSettings.DISABLE_MUTATION_CHANCE);
        this.mutationRates.put(MutationKeys.ENABLE_MUTATION_CHANCE , AlgorithmSettings.ENABLE_MUTATION_CHANCE);
    }

    public Genome(Genome parent) {

        for (ConnectionGene c:parent.connectionGeneList){
            this.connectionGeneList.add(new ConnectionGene(c));
        }

        this.fitness = parent.fitness;
        this.adjustedFitness = parent.adjustedFitness;
        this.inputCount = parent.inputCount;
        this.outputCount = parent.outputCount;

        this.mutationRates = (HashMap<MutationKeys, Float>) parent.mutationRates.clone();

    }


    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    // Can remove below setter-getter after testing
    public ArrayList<ConnectionGene> getConnectionGeneList() {
        return connectionGeneList;
    }

    public void setConnectionGeneList(ArrayList<ConnectionGene> connectionGeneList) {
        this.connectionGeneList = connectionGeneList;
    }

    public static Genome crossOver(Genome parent1, Genome parent2){
        if(parent1.fitness < parent2.fitness){
            Genome temp = parent1;
            parent1 = parent2;
            parent2 = temp;
        }

        Genome child = new Genome(parent1.inputCount, parent1.outputCount);
        TreeMap<Integer, ConnectionGene> geneMap1 = new TreeMap<>();
        TreeMap<Integer, ConnectionGene> geneMap2 = new TreeMap<>();

        for(ConnectionGene con: parent1.connectionGeneList){
            assert  !geneMap1.containsKey(con.getInnovation());             //TODO Remove for better performance
            geneMap1.put(con.getInnovation(), con);
        }

        for(ConnectionGene con: parent2.connectionGeneList){
            assert  !geneMap2.containsKey(con.getInnovation());             //TODO Remove for better performance
            geneMap2.put(con.getInnovation(), con);
        }

        Set<Integer> innovationP1 = geneMap1.keySet();
        Set<Integer> innovationP2 = geneMap2.keySet();

        Set<Integer> allInnovations = new HashSet<Integer>(innovationP1);
        allInnovations.addAll(innovationP2);

        for(int key : allInnovations){
            ConnectionGene trait;

            if(geneMap1.containsKey(key) && geneMap2.containsKey(key)){
                if(rand.nextBoolean()){
                    trait = new ConnectionGene(geneMap1.get(key));
                }else {
                    trait = new ConnectionGene(geneMap2.get(key));
                }

                if((geneMap1.get(key).isEnabled()!=geneMap2.get(key).isEnabled())){
                    if( (rand.nextFloat()<0.75f ))
                        trait.setEnabled(false);
                    else
                        trait.setEnabled(true);
                }

            }else if(parent1.getFitness()==parent2.getFitness()){               // disjoint or excess and equal fitness
                if(geneMap1.containsKey(key))
                    trait = geneMap1.get(key);
                else
                    trait = geneMap2.get(key);

                if(rand.nextBoolean()){
                    continue;
                }

            }else
                trait = geneMap1.get(key);


            child.connectionGeneList.add(trait);
        }


        return child;

    }


    public static boolean isSameSpecies(Genome g1, Genome g2){
        TreeMap<Integer, ConnectionGene> geneMap1 = new TreeMap<>();
        TreeMap<Integer, ConnectionGene> geneMap2 = new TreeMap<>();

        int matching = 0;
        int disjoint = 0;
        int excess = 0;
        float weight = 0;
        int lowMaxInnovation;
        float delta = 0;

        for(ConnectionGene con: g1.connectionGeneList) {
            assert  !geneMap1.containsKey(con.getInnovation());             //TODO Remove for better performance
            geneMap1.put(con.getInnovation(), con);
        }

        for(ConnectionGene con: g2.connectionGeneList) {
            assert  !geneMap2.containsKey(con.getInnovation());             //TODO Remove for better performance
            geneMap2.put(con.getInnovation(), con);
        }
        if(geneMap1.isEmpty() || geneMap2.isEmpty())
            lowMaxInnovation = 0;
        else
            lowMaxInnovation = Math.min(geneMap1.lastKey(),geneMap2.lastKey());

        Set<Integer> innovationP1 = geneMap1.keySet();
        Set<Integer> innovationP2 = geneMap2.keySet();

        Set<Integer> allInnovations = new HashSet<Integer>(innovationP1);
        allInnovations.addAll(innovationP2);

        for(int key : allInnovations){

            if(geneMap1.containsKey(key) && geneMap2.containsKey(key)){
                matching ++;
                weight += Math.abs(geneMap1.get(key).getWeight() - geneMap2.get(key).getWeight());
            }else {
                if(key < lowMaxInnovation){
                    disjoint++;
                }else{
                    excess++;
                }
            }

        }

        //System.out.println("matching : "+matching + "\ndisjoint : "+ disjoint + "\nExcess : "+ excess +"\nWeight : "+ weight);

        int N = matching+disjoint+excess ;

        if(N>0)
            delta = (AlgorithmSettings.EXCESS_COEFFICENT * excess + AlgorithmSettings.DISJOINT_COEFFICENT * disjoint) / N + (AlgorithmSettings.WEIGHT_COEFFICENT * weight) / matching;

        return delta < AlgorithmSettings.COMPATIBILITY_THRESHOLD;

    }

    private void generateNetwork() {
        nodes.clear();
        //  Input layer
        for (int i = 0; i < inputCount; i++) {
            nodes.put(i, new NodeGene(0));                    //Inputs
        }
        nodes.put(inputCount, new NodeGene(1));        // Bias

        //output layer
        for (int i = inputCount + AlgorithmSettings.HIDDEN_NODES; i < inputCount + AlgorithmSettings.HIDDEN_NODES + outputCount; i++) {
            nodes.put(i, new NodeGene(0));
        }

        // hidden layer
        for (ConnectionGene con : connectionGeneList) {
            if (!nodes.containsKey(con.getInto()))
                nodes.put(con.getInto(), new NodeGene(0));
            if (!nodes.containsKey(con.getOut()))
                nodes.put(con.getOut(), new NodeGene(0));
            nodes.get(con.getOut()).getIncomingCon().add(con);
        }


    }

    public ArrayList<Float> evaluateNetwork(List<Float> inputs) {
        ArrayList<Float> output = new ArrayList<>(outputCount);
        generateNetwork();

        for (int i = 0; i < inputCount; i++) {
            nodes.get(i).setValue(inputs.get(i));
        }

        for (Map.Entry<Integer, NodeGene> mapEntry : nodes.entrySet()) {
            float sum = 0;
            int key = mapEntry.getKey();
            NodeGene node = mapEntry.getValue();

            if (key > inputCount) {
                for (ConnectionGene conn : node.getIncomingCon()) {
                    if (conn.isEnabled()) {
                        sum += nodes.get(conn.getInto()).getValue() * conn.getWeight();
                    }
                }
                node.setValue(sigmoid(sum));
            }
        }

        for (int i = 0; i < outputCount; i++) {
            output.add(nodes.get(inputCount + AlgorithmSettings.HIDDEN_NODES + i).getValue());
        }
        return output;
    }

    private float sigmoid(float x) {
        // TODO Auto-generated method stub
        return (float) (1 / (1 + Math.exp(-4.9 * x)));
    }

    // Mutations

    public void Mutate() {
        // Mutate mutation rates
        for (Map.Entry<MutationKeys, Float> entry : mutationRates.entrySet()) {
            if(rand.nextBoolean())
                mutationRates.put(entry.getKey(), 0.95f * entry.getValue() );
            else
                mutationRates.put(entry.getKey(), 1.05263f * entry.getValue() );
        }


        if (rand.nextFloat() <= mutationRates.get(MutationKeys.WEIGHT_MUTATION_CHANCE))
            mutateWeight();
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.CONNECTION_MUTATION_CHANCE))
            mutateAddConnection(false);
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.BIAS_CONNECTION_MUTATION_CHANCE))
            mutateAddConnection(true);
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.NODE_MUTATION_CHANCE))
            mutateAddNode();
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.DISABLE_MUTATION_CHANCE))
            disableMutate();
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.ENABLE_MUTATION_CHANCE))
            enableMutate();
    }

    void mutateWeight() {

        for (ConnectionGene c : connectionGeneList) {
            if (rand.nextFloat() < AlgorithmSettings.WEIGHT_CHANCE) {
                if (rand.nextFloat() < AlgorithmSettings.PERTURB_CHANCE)
                    c.setWeight(c.getWeight() + (2 * rand.nextFloat() - 1) * AlgorithmSettings.STEPS);
                else c.setWeight(4 * rand.nextFloat() - 2);
            }
        }
    }

    void mutateAddConnection(boolean forceBais) {
        generateNetwork();
        int i = 0;
        int j = 0;
        int random2 = rand.nextInt(nodes.size() - inputCount - 1) + inputCount + 1;
        int random1 = rand.nextInt(nodes.size());
        if(forceBais)
            random1 = inputCount;
        int node1 = -1;
        int node2 = -1;

        for (int k : nodes.keySet()) {
            if (random1 == i) {
                node1 = k;
                break;
            }
            i++;
        }

        for (int k : nodes.keySet()) {
            if (random2 == j) {
                node2 = k;
                break;
            }
            j++;
        }
//	System.out.println("random1 = "+random1 +" random2 = "+random2);
//	System.out.println("Node1 = "+node1 +" node 2 = "+node2);


        if (node1 >= node2)
            return;

        for (ConnectionGene con : nodes.get(node2).getIncomingCon()) {
            if (con.getInto() == node1)
                return;
        }

        if (node1 < 0 || node2 < 0)
            throw new RuntimeErrorException(null);          // TODO Population.newInnovation(node1, node2)
        connectionGeneList.add(new ConnectionGene(node1, node2, InnovationCounter.newInnovation(), 4 * rand.nextFloat() - 2, true));                // Add innovation and weight

    }

    void mutateAddNode() {
        generateNetwork();
        if (connectionGeneList.size() > 0) {
            int timeoutCount = 0;
            ConnectionGene randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
            while (!randomCon.isEnabled()) {
                randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
                timeoutCount++;
                if (timeoutCount > AlgorithmSettings.HIDDEN_NODES)
                    return;
            }
            int nextNode = nodes.size() - outputCount;
            randomCon.setEnabled(false);
            connectionGeneList.add(new ConnectionGene(randomCon.getInto(), nextNode, InnovationCounter.newInnovation(), 1, true));        // Add innovation and weight
            connectionGeneList.add(new ConnectionGene(nextNode, randomCon.getOut(), InnovationCounter.newInnovation(), randomCon.getWeight(), true));
        }
    }
    void disableMutate() {
        //generateNetwork();                // remove laters
        if (connectionGeneList.size() > 0) {
            ConnectionGene randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
            randomCon.setEnabled(false);
        }
    }


    void enableMutate() {
        //generateNetwork();                // remove laters
        if (connectionGeneList.size() > 0) {
            ConnectionGene randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
            randomCon.setEnabled(true);
        }
    }

    @Override
    public int compareTo(Object o) {
        Genome g = (Genome)o;
        if (fitness==g.fitness)
            return 0;
        else if(fitness >g.fitness)
            return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return "Genome{" +
                "fitness=" + fitness +
                ", connectionGeneList=" + connectionGeneList +
                ", nodeGenes=" + nodes +
                '}';
    }

    public void setAdjustedFitness(float adjustedFitness) {
        this.adjustedFitness = adjustedFitness;
    }

    public float getAdjustedFitness() {
        return adjustedFitness;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public void writeTofile(){
        BufferedWriter bw = null;
        FileWriter fw = null;
        StringBuilder builder = new StringBuilder();
        for (ConnectionGene conn: connectionGeneList) {
            builder.append(conn.toString()+"\n");
        }
        try {


            fw = new FileWriter("Genome.txt");
            bw = new BufferedWriter(fw);
            bw.write(builder.toString());

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }


    public Genome copy() {return new Genome(this); }

    public int size() {return connectionGeneList.size(); }

}

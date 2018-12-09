package algorithm.model;

import game.Genome;

/** wrapper class for genome which applies genome's
 * elements into real fight strategies
 * and uses elements from genome to decide
 * what action to take during the fight
 */
public class ShipStrategy {

    private Genome genome;

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }
}

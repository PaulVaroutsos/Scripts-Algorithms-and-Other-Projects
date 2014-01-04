/**
 * Stores information for a GreedyOverselectino Run
 *
 * @author Paul Varoutsos
 * Spring 2010
 */
package ec.app.grid;

public class GreedyRun {

    private double top;
    private double gets;
    private int bestFitness;

    public GreedyRun(int t, double mutationP, int bestFit) {
        top = t;
        gets = mutationP;
        bestFitness = bestFit;
    }

    public GreedyRun() {
        top = -1;
        gets = -1;
        bestFitness = -1;
    }

    public double getTop() {
        return top;
    }

    public double getGets() {
        return gets;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public void setTournSize(double tSize) {
        top = tSize;
    }

    public void setGets(double g) {
        gets = g;
    }

    public void setBestFitness(int fitness) {
        bestFitness = fitness;
    }
}

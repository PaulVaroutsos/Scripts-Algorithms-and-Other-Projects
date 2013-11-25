package ec.app.grid;

/**
 *
 * Holds information for a Tournament Run
 * @author Paul
 */
public class TournamentRun {

    private int tournSize;
    private double mutationProb;
    private int bestFitness;

    public TournamentRun(int tSize, double mutationP, int bestFit){
        tournSize = tSize;
        mutationProb = mutationP;
        bestFitness = bestFit;
    }

    public TournamentRun(){
        tournSize = -1;
        mutationProb = -1;
        bestFitness = -1;
    }

    public int getTournSize(){
        return tournSize;
    }

    public double getMutationProb(){
        return mutationProb;
    }

    public int getBestFitness(){
        return bestFitness;
    }

    public void setTournSize(int tSize){
        tournSize = tSize;
    }

    public void setMutationProb(double mProb){
        mutationProb = mProb;
    }

    public void setBestFitness(int fitness){
        bestFitness = fitness;
    }
}

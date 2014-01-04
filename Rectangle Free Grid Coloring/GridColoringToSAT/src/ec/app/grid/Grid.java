/**
 * A ECJ Problem class that contains the evaluate function that determines how
 * closely an "individual" is to being a perfectly colored grid. 
 * 
 * This Problem is currently only able to evaluate square grids (such as 17x17 
 * and not 21x12).
 * 
 * Caution: Ensure the Validator class has the correct row and column sizes 
 * defined!
 * 
 * @author Paul Varoutsos
 */
package ec.app.grid;

import ec.EvolutionState;
import ec.Problem;
import ec.simple.SimpleProblemForm;
import ec.vector.IntegerVectorIndividual;
import ec.*;
import ec.simple.SimpleFitness;

public class Grid extends Problem implements SimpleProblemForm {

    /**
    Evalutes the individual using the MAXSAT fitness function.
     */
    public void evaluate(final EvolutionState state, final Individual ind, final int subpopulation, final int threadnum) {
        IntegerVectorIndividual ind2 = (IntegerVectorIndividual) ind;
        double fitness = 0;
        Validator VLD = new Validator(ind2.genome);

        /*Normally we want 0 to be the highest fitness and 0-fitness to be the
         * individual.  However, some selection algorithms require fitness to be
         * greater than 0.  What I'm doing is making the number of rectangles the
         * highest fitness and 0 the lowest fitness by the following calculation:
         * (numOfPosRecs - numOfActualRecs)
         * Currently only works for SQUARE GRIDS
         */

        //new fitness for square grids
        fitness = VLD.nChoose2() - VLD.getNumberOfRec();

        ((SimpleFitness) (ind2.fitness)).setFitness(state, (float) fitness, false);
    }

    public void describe(final Individual ind,
            final EvolutionState state,
            final int threadnum,
            final int subpopulation,
            final int log,
            final int verbosity) {
    }
}

/**
 * Caution: Ensure the row size and column size match the grid size problem that
 * is being solved!
 * @author Mike Groh
 */
class Validator {

    String OUTPUT = "";
    //The unmapped solution
    int[][] coloringGrid;
    int numberOfRec = 0;
    int ROW_SIZE = 17;
    int COLUMN_SIZE = 17;
    int NUM_COLORS = 4;
    //Shows where a rectangle occurs, if one exists.
    int[][] solutionGrid = new int[ROW_SIZE][COLUMN_SIZE];

    public Validator(int[] coloringGrid) {
        this.coloringGrid = convertToGrid(coloringGrid);
        ValidateGridColoring();
    }

    public int[][] convertToGrid(int[] coloringGrid) {

        int[][] CG = new int[ROW_SIZE][COLUMN_SIZE];
        int k = 0;
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = k % ROW_SIZE; j < ROW_SIZE; j++, k++) {
                CG[i][j] = coloringGrid[k];
            }
        }
        return CG;
    }

    /**
     * Checks whether or not the grid contains any sub rectangles
     */
    void ValidateGridColoring() {
        boolean ohCrapItDidntWork = false;
        for (int i = 1; i <= ROW_SIZE && !ohCrapItDidntWork; i++) {
            for (int j = 1; j <= COLUMN_SIZE && !ohCrapItDidntWork; j++) {
                for (int k = 1; k <= NUM_COLORS && !ohCrapItDidntWork; k++) {
                    if (j == 11 && i == 11 && k == 4) {
                        int g = 0;
                    }
                    if (coloringGrid[translate(i)][translate(j)] == k) {
                        if (CheckForRectangles(i, j, k)) {
                            //  System.out.println("found rectangle");
                            // numberOfRec++;
                            OUTPUT += "found rectangle+\n";
                            // ohCrapItDidntWork = true;
                        }
                    }
                }
            }
        }
        // printValidationGrid();
        if (!ohCrapItDidntWork) {
            //  System.out.println("validation sucessful");
            OUTPUT += "validation sucessful\n";
        }
    }

    //Helper method that checks for rectangles
    private boolean CheckForRectangles(int i, int j, int k) {

        boolean TheresSomptinUpWitYourCodeMan = false;
        for (int a = i; a <= ROW_SIZE && !TheresSomptinUpWitYourCodeMan; a++) {
            if (a == i) {
                //do nothing
            } else {
                int debug = coloringGrid[translate(a)][translate(j)];
                if (coloringGrid[translate(a)][translate(j)] == k) {
                    for (int b = j; b <= COLUMN_SIZE && !TheresSomptinUpWitYourCodeMan; b++) {
                        if (b == j) {
                            //do nothing
                        } else {
                            if (coloringGrid[translate(i)][translate(b)] == k) {
                                if (coloringGrid[translate(a)][translate(b)] == k) {
                                    numberOfRec++;
                                    if (numberOfRec % 81 == 0) {
                                        int adf = 0;
                                    }
                                    // TheresSomptinUpWitYourCodeMan = true;
                                }
                            }
                        }
                    }
                }
            }
        }



        if (TheresSomptinUpWitYourCodeMan) {
            solutionGrid[translate(i)][translate(j)] = 1;
        } else {
            solutionGrid[translate(i)][translate(j)] = 0;
        }
        return TheresSomptinUpWitYourCodeMan;
    }

    /**
     * Prints the grid
     */
    private void printValidationGrid() {
        String output = "";
        for (int i = 1; i <= ROW_SIZE; i++) {
            for (int j = 1; j <= COLUMN_SIZE; j++) {
                output += solutionGrid[translate(i)][translate(j)] + " ";
            }
            output += "\n";
        }

        OUTPUT += output + "\n";
    }

    //Translates to array locations
    private static int translate(int x) {
        return x - 1;
    }

    double getNumberOfRec() {
        return numberOfRec;
    }

    public double nChoose2() {
        int num = (int) (Math.pow((ROW_SIZE * (ROW_SIZE - 1)) / 2, 2));
        return num;
    }
}


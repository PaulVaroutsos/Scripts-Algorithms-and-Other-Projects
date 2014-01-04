/**
 * This class was used to validate the solutions.
 * CHANGE LINE 20 TO DETERMINE THE INPUT FILE
 * 
 * @author Mike Groh
 */
package ec.app.Grid.solutions;

import java.io.FileReader;
import java.util.Scanner;

public class SolutionValidation {

    private final static String FILE_TO_VALIDATE = "src/ec/app/Grid/solutions/sol.txt";
    //The unmapped solution
    int[][] coloringGrid;
    int numberOfRec = 0;
    int ROW_SIZE = 12;
    int COLUMN_SIZE = 12;
    int NUM_COLORS = 4;
    //Shows where a rectangle occurs, if one exists.
    int[][] solutionGrid = new int[ROW_SIZE][COLUMN_SIZE];
    String OUTPUT = "";

    public SolutionValidation(int[] coloringGrid) {
        this.coloringGrid = convertToGrid(coloringGrid);
        printColoringGrid();
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
                            OUTPUT += "Found rectangle+\n";
                            // ohCrapItDidntWork = true;
                        }
                    }
                }
            }
        }
        // printValidationGrid();
        if (!ohCrapItDidntWork) {
            //  System.out.println("validation sucessful");
            OUTPUT += "Validation successful.\n";
        }
        printValidationGrid();
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

        //System.out.println(output);
        OUTPUT += output + "\n";
        System.out.print(OUTPUT);
    }

    //Translates to array locations
    private static int translate(int x) {
        return x - 1;
    }

    double getNumberOfRec() {
        return numberOfRec;
    }

    public static void main(String[] args) {
        int[] A = new int[144];

        try {
            FileReader gridSolution;

            gridSolution = new FileReader(FILE_TO_VALIDATE);

            //scanner used to read the file
            Scanner sc = new Scanner(gridSolution);
            sc.useDelimiter(" ");
            int i = 0;
            while (sc.hasNext()) {
                A[i] = sc.nextInt();
                i++;
            }
        } catch (Exception e) {
            System.err.println(e.getCause());
        }

        SolutionValidation v = new SolutionValidation(A);
        System.out.println("There are " + v.getNumberOfRec() + "rectangles.");
    }

    private void printColoringGrid() {
        String output = "";
        for (int i = 1; i <= ROW_SIZE; i++) {
            for (int j = 1; j <= COLUMN_SIZE; j++) {
                output += coloringGrid[translate(i)][translate(j)] + " ";
            }
            output += "\n";
        }

        //System.out.println(output);
        OUTPUT += output + "\n";
    }
}

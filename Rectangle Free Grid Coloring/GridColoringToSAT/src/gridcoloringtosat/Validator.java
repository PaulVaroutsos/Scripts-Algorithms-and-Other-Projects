/**
 * Validates a grid.  If no rectangles are found, it will print out the grid and
 * a second grid filled with zeroes.
 *
 * If rectangles are found, it will print out the grid, and a "1" on the second
 * grid pointing to where the rectangle occured.
 * @author Mike Groh
 */
package gridcoloringtosat;

import java.io.FileWriter;
import java.io.IOException;

public class Validator {

    String OUTPUT = "";
    //The unmapped solution
    int[][] coloringGrid;
    //Shows where a rectangle occurs, if one exists.
    int[][] solutionGrid = new int[ConstantHolder.ROW_SIZE][ConstantHolder.COLUMN_SIZE];

    public Validator(int[][] coloringGrid) {
        this.coloringGrid = coloringGrid;
    }

    /**
     * Checks whether or not the grid contains any sub rectangles
     */
    void ValidateGridColoring(FileWriter out) throws IOException {
        boolean ohCrapItDidntWork = false;
        for (int i = 1; i <= ConstantHolder.ROW_SIZE && !ohCrapItDidntWork; i++) {
            for (int j = 1; j <= ConstantHolder.COLUMN_SIZE && !ohCrapItDidntWork; j++) {
                for (int k = 1; k <= ConstantHolder.NUM_COLORS && !ohCrapItDidntWork; k++) {
                    if (j == 11 && i == 11 && k == 4) {
                        int g = 0;
                    }
                    if (coloringGrid[translate(i)][translate(j)] == k) {
                        if (CheckForRectangles(i, j, k)) {
                            System.out.println("Found rectangle.");
                            OUTPUT += "Found rectangle.+\n";
                            ohCrapItDidntWork = true;
                        }
                    }
                }
            }
        }
        printValidationGrid();
        if (!ohCrapItDidntWork) {
            System.out.println("Validation succssful.");
            OUTPUT += "Validation successful.\n";
        }
        out.write(OUTPUT);
        out.close();
    }

    //Helper method that checks for rectangles
    private boolean CheckForRectangles(int i, int j, int k) {
        boolean TheresSomptinUpWitYourCodeMan = false;
        for (int a = 1; a <= ConstantHolder.ROW_SIZE && !TheresSomptinUpWitYourCodeMan; a++) {
            if (a == i) {
                //do nothing
            } else {
                int debug = coloringGrid[translate(a)][translate(j)];
                if (coloringGrid[translate(a)][translate(j)] == k) {
                    for (int b = 1; b <= ConstantHolder.COLUMN_SIZE && !TheresSomptinUpWitYourCodeMan; b++) {
                        if (b == j) {
                            //do nothing
                        } else {
                            if (coloringGrid[translate(i)][translate(b)] == k) {
                                if (coloringGrid[translate(a)][translate(b)] == k) {
                                    TheresSomptinUpWitYourCodeMan = true;
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
        for (int i = 1; i <= ConstantHolder.ROW_SIZE; i++) {
            for (int j = 1; j <= ConstantHolder.COLUMN_SIZE; j++) {
                output += solutionGrid[translate(i)][translate(j)] + " ";
            }
            output += "\n";
        }

        System.out.println(output);
        OUTPUT += output + "\n";
    }

    //Translates to array locations
    private static int translate(int x) {
        return x - 1;
    }
}

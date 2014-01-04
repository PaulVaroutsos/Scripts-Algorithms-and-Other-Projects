/**
 * Takes the output of a sat solver, converts the colors of the grid into
 * a partially colored 17x17, punches holes into it, and creates a CNF file for the
 * remaining grid colors.  This is used in our approach to convert smaller grid 
 * colorings to a 17x17, as in the WalkSat to MiniSat approach.
 *
 * This can be used to convert small colored grids (11x11, 12x12, ...) and try
 * to see if any remaining combinations of the empty squares can lead to a solution.
 *
 * This can also be used to convert a 16x16 grid, punching many many holes into it
 * and seeing if the remaining colos can lead to a solution.
 *
 * THIS CLASS IS ONLY FOR SQUARE GRIDS!
 *
 * To run the program, compile all of the classes and put them into a folder names
 * MutationSatPackage.  To run the program, cd to the directory of this folder and type:
 *  java MutationSatPackage.MutationSat [file] [size] [holes]
 *    -Where file is the output of the satsolver.  Usually done with a walksat out put
 *    -Where size is the size difference between the grid size of the output and a
 *     16x16 grid.  For example, if the output file of a satsolver is giving the
 *     solution to a 15x15 grid, size=1.  Size=2 for 15x15, etc. Size=0 for 16x16.
 *    -Where holes is the number of holes that will be randomly punched into the
 *     grid.
 *
 * @author Mike Groh
 *
 */
package mutationSatPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MutationSat {

    //this variable is where we will store our answered grid coloring if a
    //solution is found
    private static int[][] coloringGrid = new int[ConstantHolder.ROW_SIZE][ConstantHolder.COLUMN_SIZE];

    public static void main(String[] args) throws FileNotFoundException {
        try {
            Random R = new Random();
            
            String fileOutput = "";
            String fileName = ConstantHolder.fileNameOutput;
            File file = new File(fileName);
            FileWriter out = new FileWriter(file);

            FileReader gridSolution;

            gridSolution = new FileReader(args[0]);
            int diff = Integer.parseInt(args[1]);
            int NumberOfRandoms = Integer.parseInt(args[2]);

            ConstantHolder.setRowAndCol(ConstantHolder.ROW_SIZE - diff, ConstantHolder.COLUMN_SIZE - diff);
            coloringGrid = new int[ConstantHolder.ROW_SIZE][ConstantHolder.COLUMN_SIZE];

            //scanner used to read the file
            Scanner sc = new Scanner(gridSolution);

            //if there is a solution in the file, store the variable line in this file
            String varLine = "";

            //stores false if a solution is not found in the file, true otherwise
            boolean solutionFound = false;

            //this will loop through each line of the solver's output file
            //until it reaches the line that starts with Instance Satisfiable
            //if there is not a solution
            StringBuilder SB = new StringBuilder();
            solutionFound = true;
            while (sc.hasNextLine()) {
                SB.append(sc.nextLine());
            }
            varLine = SB.toString();
            //  while (sc.hasNextLine()) {
            //  varLine = sc.nextLine();
            //if we found a solution then we begin to parse the data,

            if (solutionFound) {

                //easiest way to parse the string into ints
                Scanner varlines = new Scanner(varLine);


                //temp variable to hold the next int from the string
                int tempNum;

                //variables used for
                int quotient, remainder, row, column, value;

                while (varlines.hasNext()) {
                    Scanner vars = new Scanner(varlines.nextLine());
                    vars.skip("-*");
                    //search the string until no more ints are present
                    while (vars.hasNextInt()) {

                        // store the next int value in the string
                        tempNum = vars.nextInt();

                        if (tempNum == 202) {
                            System.out.println();
                        }

                        //start with the lowest valid variable #
                        //Go to the highest variable number
                        if (tempNum >= (Math.pow(ConstantHolder.BASE, 2) + ConstantHolder.BASE + 1)) {
                            //make sure that the integer has only three digits
                            if (tempNum <= ConstantHolder.BASE * (Math.pow(ConstantHolder.BASE, 2)) + ConstantHolder.BASE * ConstantHolder.BASE + 4) {

                                //345 / 100 = 3 (row ) remainder 45

                                //    quotient = (int)(tempNum / ((Math.pow(ConstantHolder.BASE ,2))  + 1.0000001));
                                double correction = 2 * ConstantHolder.NUM_COLORS / (Math.pow(ConstantHolder.BASE, 2));
                                quotient = (int) ((tempNum / ((Math.pow(ConstantHolder.BASE, 2)))) - correction);
                                remainder = tempNum - (int) (Math.pow(ConstantHolder.BASE, 2) * quotient);
                                row = quotient;

                                //45 / 10 = 4 (column) remaider 5 (value)
                                quotient = remainder / (int) ConstantHolder.BASE;
                                column = quotient;
                                value = remainder % ConstantHolder.BASE;

                                //need to take into account variables like
                                //110 210 310 .... 201 301 401
                                //these values can not be used since 0 is not valid value

                                if (value > 0 && value < 5 && column != 0) {

                                    //place the value into the sudoku puzzle

                                    //place the value into the grid
                                    try {
                                        coloringGrid[translate(column)][translate(row)] = value;
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }

                    }//end of while loop

                    //once we are done parsing the variables we then need to print the
                    //soduku board

                    String output = "";
                    for (int i = 1; i <= ConstantHolder.ROW_SIZE; i++) {
                        for (int j = 1; j <= ConstantHolder.COLUMN_SIZE; j++) {
                            output += coloringGrid[translate(i)][translate(j)] + " ";
                        }
                        output += "\n";
                    }
                }
            } else {
                System.out.println("The sat solver was not able to find a solution.");
            }
            //  }
            MapTo17by17 grid17b17 = new MapTo17by17(coloringGrid, NumberOfRandoms);
            fileOutput = grid17b17.getOutput();
            out.write(fileOutput);
            out.close();

        } catch (Exception e) {
            System.err.println(e.getCause());
        }
    }

    /**
     * Creates the array index equivelant of the number
     * @param x The number you want to translate to an index
     * @return x-1
     */
    private static int translate(int x) {
        return x - 1;
    }
}

class MapTo17by17 {

    StringBuilder output = new StringBuilder();

    public MapTo17by17(int[][] coloringGrid, int NumberOfRandoms) {
        FileReader cnf17by17 = null;
        try {
            ArrayList<Integer> ranList = pickRandoms(NumberOfRandoms);
            cnf17by17 = new FileReader(ConstantHolder.fileName17by17cnf);
            Scanner sc = new Scanner(cnf17by17);
            sc.nextLine();
            int numClauses = 76007 + ConstantHolder.BASE * ConstantHolder.BASE - NumberOfRandoms;
            String top = new String("p cnf 5206 " + numClauses + "\n");
            output.append(top);
            int k = 0;
            while (sc.hasNextLine()) {
                k++;
                String nextLine = sc.nextLine();

                nextLine += "\n";
                output.append(nextLine);
            }
            int counter = 0;
            for (int i = 1; i <= ConstantHolder.ROW_SIZE; i++) {
                for (int j = 1; j <= ConstantHolder.COLUMN_SIZE; j++) {
                    if (!ranList.contains(counter)) {
                        int index = (i * (int) Math.pow(17, 2)) + (j * (int) Math.pow(17, 1)) + coloringGrid[translate(i)][translate(j)];
                        output.append(index + " 0\n");
                    }
                    counter++;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapTo17by17.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cnf17by17.close();
            } catch (IOException ex) {
                Logger.getLogger(MapTo17by17.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Integer> pickRandoms(int NumberOfRandoms) {
        ArrayList<Integer> ranList = new ArrayList<Integer>();
        boolean finished = false;
        int i = 0;
        while (!finished) {
            Random Rg = new Random();
            int next = Rg.nextInt(ConstantHolder.BASE * ConstantHolder.BASE - 1);
            if (!Inlist(next, ranList)) {
                ranList.add(next);
                i++;
            }
            if (i == NumberOfRandoms) {
                finished = true;
            }
        }
        return ranList;
    }

    private boolean Inlist(int next, ArrayList<Integer> ranList) {
        boolean inList = false;
        for (Integer i : ranList) {
            if (next == i) {
                inList = true;
            }
        }

        return inList;
    }

    public String getOutput() {
        return output.toString();
    }

    private static int translate(int x) {
        return x - 1;
    }
}

class ConstantHolder {

    static int NUM_COLORS = 4;
    static int ROW_SIZE = 16;
    static int COLUMN_SIZE = 16;
    static int BASE = ROW_SIZE;
    static String fileName17by17cnf = "17x17.cnf";
    static String fileNameOutput = "out.txt";

    static void setRowAndCol(int row, int col) {
        ROW_SIZE = row;
        COLUMN_SIZE = col;
        BASE = ROW_SIZE;
    }

    static {

        if (ROW_SIZE > COLUMN_SIZE) {
            BASE = ROW_SIZE;
        } else {
            BASE = COLUMN_SIZE;
        }
    }
    static String SOLUTION_COUNTER = "1";
}

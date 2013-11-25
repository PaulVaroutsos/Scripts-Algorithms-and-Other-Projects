/**
 * This class takes the output of the solver and creates a solution to the
 * grid coloring based on that output.  If the solver could not find an answer to
 * solve the puzzle it will output that an answer could not be found.
 */
package gridcoloringtosat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class MutationUnmapper {

    //this variable is where we will store our answered grid coloring if a
    //solution is found
    private static int[][] coloringGrid = new int[ConstantHolder.ROW_SIZE][ConstantHolder.COLUMN_SIZE];

    public static void main(String[] args) throws FileNotFoundException {
        try{
        //formattAnswer FA = new formattAnswer();
        String fileOutput = "";
        String fileName ="src/solution_squares/" +
                          ConstantHolder.ROW_SIZE + "x" + ConstantHolder.COLUMN_SIZE + "Answer"
                          +ConstantHolder.SOLUTION_COUNTER + "validation.txt";
        File file = new File(fileName);
        FileWriter out = new FileWriter(file);

        FileReader gridSolution;

        if (args == null) {
            gridSolution = new FileReader(args[0]);
        } else {
            gridSolution = new FileReader("src/gridcoloringtosat/satsolution.txt");
        }

        //scanner used to read the file
        Scanner sc = new Scanner(gridSolution);

        ////temp string for when reading in lines from file
        String tmpLine = "";

        //if there is a solution in the file, store the variable line in this file
        String varLine = "";

        //stores false if a solution is not found in the file, true otherwise
        boolean solutionFound = false;

        //this will loop through each line of the solver's output file
        //until it reaches the line that starts with Instance Satisfiable
        //if there is not a solution
        while (sc.hasNextLine()) {
            tmpLine = sc.nextLine();

            if (tmpLine.startsWith("Instance Satisfiable")) {

                //since the line we just read contains that string we know
                //the next line contains all the variables we need to look at
                varLine = sc.nextLine();

                //this file contains a solution
                solutionFound = true;

                //we got what we need from the file, just break out of the loop
                break;
            }
        }

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
                            double correction = 2*ConstantHolder.NUM_COLORS/(Math.pow(ConstantHolder.BASE,2));
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
                                try{
                                    coloringGrid[translate(column)][translate(row)] = value;
                                }catch(Exception e){

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

                System.out.println(output);

        fileOutput +=output;
            }
        } else {
            System.out.println("The sat solver was not able to find a solution");
        }

        Validator val = new Validator(coloringGrid);


    }catch(Exception E){
        E.printStackTrace();
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

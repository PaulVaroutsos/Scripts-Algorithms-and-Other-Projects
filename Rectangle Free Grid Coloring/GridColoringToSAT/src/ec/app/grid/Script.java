/**
 *
 * Runs the GA for all of the output files in the currect working directory.
 * The output fils are created via the ParameterGenerator classes.
 *
 * To run this script in NetBeans, change the working directory to:
 * src/ec/app/Grid and run the Main method in this class.
 *
 * @author Paul Varoutsos
 */
package ec.app.grid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Script {

    public static void main(String[] args) throws IOException {

        //Params given to ec.Evolve, the param filename
        String[] params = {"-file", ""};

        //the run number, corresponds to out.stat.# where # is the run number
        int runNum = 0;

        //paramfile to use
        String paramFile = "grid.params." + runNum;

        //the output file that we will copy and write to out.stat.#
        String outputFile = "out.stat";

        //The string we will write to the file
        StringBuilder fileContents = new StringBuilder();

        do {
            try {

                fileContents = new StringBuilder();
                params[1] = paramFile;
                ec.Evolve.main(params);

                //go through the param file and copy the data to the stringbuilder
                BufferedReader input = new BufferedReader(new FileReader(paramFile));
                String line = null;
                while ((line = input.readLine()) != null) {
                    fileContents.append(line);
                    fileContents.append("\n");
                }
                input.close();
                input = null;

                //create a divider between the param file contetns and the output
                fileContents.append("\n");
                fileContents.append("\n");
                fileContents.append("==============================================");
                fileContents.append("\n");
                fileContents.append("==============================================");
                fileContents.append("\n");
                fileContents.append("==============================================");
                fileContents.append("\n");
                fileContents.append("\n");
                fileContents.append("\n");

                //go through the output file and copy the data to the stringbuilder
                input = new BufferedReader(new FileReader(outputFile));
                while ((line = input.readLine()) != null) {
                    fileContents.append(line);
                    fileContents.append("\n");
                }
                input.close();
                input = null;


                //write the stringbuilder to the file
                File f = new File(outputFile);
                f.delete();

                f = new File(outputFile + "." + runNum);

                Writer output = new BufferedWriter(new FileWriter(f));
                output.write(fileContents.toString());
                output.close();
                output = null;

            } catch (IOException e) {
                System.out.println(e);
            }

            //next file
            runNum++;
            paramFile = "grid.params." + runNum;
        } while (new File(paramFile).canRead());
    }
}

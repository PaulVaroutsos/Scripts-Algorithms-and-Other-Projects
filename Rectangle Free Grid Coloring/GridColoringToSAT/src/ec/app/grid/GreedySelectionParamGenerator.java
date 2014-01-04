/**
 * This class generates a set of Parameter files for the Script.java class to
 * run.  These parameter files are generated with various top and gets 
 * percentages.
 *
 * To run this script in NetBeans, change the working directory to:
 * src/ec/app/Grid and run the Main method in this class.
 *
 * @author Paul Varoutsos, Mike Groh
 * Spring 2010
 */
package ec.app.grid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class GreedySelectionParamGenerator {

    public static void main(String[] args) throws IOException {
        int counter = 0;
        double maxTop = .2;
        double maxGets = 1.0;
        double mutationProb = .012;
        double topDelta = .01;
        double getsDelta = .01;

        for (double topStart = .18; topStart <= maxTop; topStart += topDelta) {
            for (double getsTop = .98; getsTop <= maxGets; getsTop += getsDelta) {

                //The string we will write to the file
                StringBuilder fileContents = new StringBuilder();

                fileContents.append("parent.0 = greedy.params");
                fileContents.append("\n");
                fileContents.append("pop.subpop.0.species.pipe.source.0.source.0.top = " + topStart);
                fileContents.append("\n");
                fileContents.append("pop.subpop.0.species.pipe.source.0.source.0.gets = " + getsTop);
                fileContents.append("\n");
                fileContents.append("pop.subpop.0.species.mutation-prob = " + mutationProb);
                fileContents.append("\n");

                String paramFileName = "grid.params." + counter;

                Writer output = new BufferedWriter(new FileWriter(paramFileName));
                output.write(fileContents.toString());
                output.close();
                output = null;

                counter++;
            }//top
        }//gets
    }//main
}

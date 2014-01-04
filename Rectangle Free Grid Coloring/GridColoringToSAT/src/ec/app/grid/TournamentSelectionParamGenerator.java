/**
 * This class generates a set of parameter files for the Script.java class to
 * run.  These parameter files are generated with various mutation probabilities
 * and tournament sizes.
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

public class TournamentSelectionParamGenerator {

    public static void main(String[] args) throws IOException {
        int counter = 0;
        double maxMutationProb = .01;
        double DELTA = .0003;

        double maxTournamentSize = 7;

        for (double mutationProb = .005; mutationProb <= maxMutationProb; mutationProb += DELTA) {
            for (double tournamentSize = 2; tournamentSize <= maxTournamentSize; tournamentSize += 5) {

                //The string we will write to the file
                StringBuilder fileContents = new StringBuilder();

                fileContents.append("parent.0 = ../../simple/simple.params");
                fileContents.append("\n");
                fileContents.append("parent.1 = tournament.params");
                fileContents.append("\n");

                fileContents.append("select.tournament.size =                " + tournamentSize);
                fileContents.append("\n");
                fileContents.append("pop.subpop.0.species.mutation-prob = " + mutationProb);
                fileContents.append("\n");

                String paramFileName = "grid.params." + counter;

                Writer output = new BufferedWriter(new FileWriter(paramFileName));
                output.write(fileContents.toString());
                output.close();
                output = null;

                counter++;
            }//end tournament size
        }//end mutation prob
    }//end main
}

package ec.app.grid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Parses the output files from the output of all of the TournamentSelection runs
 * after the Parser class is run.
 *
 * To run this script in NetBeans, change the working directory to:
 * src/ec/app/Grid and run the Main method in this class.
 *
 * @author Paul
 * Spring 2010
 */
public class TournamentParser {

    //The string we will write to the file
    static StringBuilder fileContents = new StringBuilder();
    //the run number, corresponds to out.stat.# where # is the run number
    static int runNum = 0;
    //the output file that we will copy and write to out.stat.#
    static String outputFile = "out.stat." + runNum;
    static ArrayList<TournamentRun> runs = new ArrayList<TournamentRun>();

    public static void main(String[] args) throws IOException {

        do {
            //go through the param file and copy the data to the stringbuilder
            BufferedReader input = new BufferedReader(new FileReader(outputFile));
            String line = null;
            TournamentRun run = new TournamentRun();
            while ((line = input.readLine()) != null) {
                String[] tokens;

                if (line.contains("select.tournament.size")) {
                    tokens = line.split(" ");
                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                        run.setTournSize((int)Double.parseDouble(s));

                    }
                } else if (line.contains("pop.subpop.0.species.mutation-prob")) {
                    tokens = line.split(" ");

                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                        run.setMutationProb(Double.parseDouble(s));
                    }
                } else if (line.contains("Fitness:")) {
                    tokens = line.split(" ");

                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                        run.setBestFitness((int)Double.parseDouble(s));
                    }
                }

            }

             runs.add(run);

            //next file
            runNum++;
            outputFile = "out.stat." + runNum;
        } while (new File(outputFile).canRead());

        makeFile();
    }

    /**
     * Creates the CVS file containing all of the data for all of the runs.
     */
    static private void makeFile() {
        fileContents = new StringBuilder();
        TreeSet<Integer> tournamentSizes = new TreeSet<Integer>();
        TreeSet<Float> mutationProbs = new TreeSet<Float>();

        for(TournamentRun r : runs){
            tournamentSizes.add(r.getTournSize());
            mutationProbs.add((float)r.getMutationProb());
        }

        fileContents.append(", ");
        for(Float d : mutationProbs){
            fileContents.append(d + ", ");
        }
        fileContents.append("\n");

        Float[] mutProbs = (Float[])mutationProbs.toArray(new Float[1]);
        Arrays.sort(mutProbs);

        Integer[] tournSizes = (Integer[])tournamentSizes.toArray(new Integer[1]);
        Arrays.sort(tournSizes);

        for(Integer i : tournSizes){
            fileContents.append(i + ", ");
            for(Float d : mutProbs){
               for(TournamentRun r : runs){
                   if(somewhatEqual(r.getMutationProb(),d.doubleValue()) && r.getTournSize() == i.intValue() ){
                       fileContents.append(r.getBestFitness() + ", ");
                       System.out.println("1");
                   }
               }
            }
            fileContents.append("\n");
        }


        String out = "parsed.csv";

        try {
            //write the stringbuilder to the file
            File f = new File(out);
            f.delete();

            f = new File(out);

            Writer output = new BufferedWriter(new FileWriter(f));
            output.write(fileContents.toString());
            output.close();
            output = null;

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /**
     * Compare operations for doubles are unpredictable.  We use this method
     * to determine whether or not two numbers are equal.
     * EX: .0002000000000019 is the same as .00020000000000002
     * @param x First number to compare
     * @param y Second number to compare.
     * @return
     */
    private static boolean somewhatEqual(double x, double y){
        double difference = 0;
       if(x > y){
            difference = x-y;
       }else{
            difference = y-x;
       }

        if(difference < .000001){
            return true;
        }

        return false;

    }
}

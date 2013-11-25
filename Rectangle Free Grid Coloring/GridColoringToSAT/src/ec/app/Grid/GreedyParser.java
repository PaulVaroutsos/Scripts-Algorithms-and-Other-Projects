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
 * This class will output a CSV file that contains the the best individual's fitness
 * from each run for each output file in the directory. It will create an output file
 * with this data for each parameter file found.
 *
 * To run this script in NetBeans, change the working directory to:
 * src/ec/app/Grid and run the Main method in this class.
 * 
 * @author Paul
 * Spring 2010
 */
public class GreedyParser {

    //The string we will write to the file
    static StringBuilder fileContents = new StringBuilder();
    //the run number, corresponds to out.stat.# where # is the run number
    static int runNum = 0;
    //the output file that we will copy and write to out.stat.#
    static String outputFile = "out.stat." + runNum;

    //Holds all of the information for the runs
    static ArrayList<GreedyRun> runs = new ArrayList<GreedyRun>();

    /**
     * Starts the parser and goes through all of the output files.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        do {
            //go through the param file and copy the data to the stringbuilder
            BufferedReader input = new BufferedReader(new FileReader(outputFile));
            String line = null;
            GreedyRun run = new GreedyRun();
            while ((line = input.readLine()) != null) {
                String[] tokens;

                if (line.contains("top")) {
                    tokens = line.split(" ");
                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                        run.setTournSize((Double) Double.parseDouble(s));

                    }
                } else if (line.contains("gets")) {
                    tokens = line.split(" ");

                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                        run.setGets(Double.parseDouble(s));
                    }
                } else if (line.contains("Fitness:")) {
                    tokens = line.split(" ");

                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                        run.setBestFitness((int) Double.parseDouble(s));
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
     * Creates the CSV file after parsing all of the output files
     */
    static private void makeFile() {
        fileContents = new StringBuilder();
        TreeSet<Double> tournamentSizes = new TreeSet<Double>();
        TreeSet<Double> mutationProbs = new TreeSet<Double>();

        for (GreedyRun r : runs) {
            tournamentSizes.add(r.getTop());
            mutationProbs.add((Double) r.getGets());
        }

        fileContents.append(", ");
        for (Double d : mutationProbs) {
            fileContents.append(d + ", ");
        }
        fileContents.append("\n");

        Double[] mutProbs = (Double[]) mutationProbs.toArray(new Double[1]);
        Arrays.sort(mutProbs);

        Double[] tournSizes = (Double[]) tournamentSizes.toArray(new Double[1]);
        Arrays.sort(tournSizes);

        for (Double i : tournSizes) {
            fileContents.append(i + ", ");
            for (Double d : mutProbs) {
                for (GreedyRun r : runs) {
                    if (somewhatEqual(r.getGets(), d.doubleValue()) && somewhatEqual(r.getTop(), (i))) {
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
    private static boolean somewhatEqual(double x, double y) {
        double difference = 0;
        if (x > y) {
            difference = x - y;
        } else {
            difference = y - x;
        }

        if (difference < .000001) {
            return true;
        }
        return false;
    }
}

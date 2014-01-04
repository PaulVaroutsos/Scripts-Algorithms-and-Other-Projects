/**
 * DO NOT USE.  Use the selection method specific parsers instead.
 * @deprecated 
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Parser {

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

                if (line.contains("top")) {
                    tokens = line.split(" ");
                    for (String s : tokens) {
                        if (s.equals(tokens[0]) || s.equals(" ") || s.equals("") || s.equals("=")) {
                            continue;
                        }
                    }
                } else if (line.contains("gets")) {
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

    static private void makeFile() {
        fileContents = new StringBuilder();
        TreeSet<Double> tournamentSizes = new TreeSet<Double>();
        TreeSet<Double> mutationProbs = new TreeSet<Double>();

        for(TournamentRun r : runs){
          //  tournamentSizes.add(r.getTournSize());
            mutationProbs.add((Double)r.getMutationProb());
        }

        fileContents.append(", ");
        for(Double d : mutationProbs){
            fileContents.append(d + ", ");
        }
        fileContents.append("\n");

        Double[] mutProbs = (Double[])mutationProbs.toArray(new Double[1]);
        Arrays.sort(mutProbs);

        Double[] tournSizes = (Double[])tournamentSizes.toArray(new Double[1]);
        Arrays.sort(tournSizes);

        for(Double i : tournSizes){
            fileContents.append(i + ", ");
            for(Double d : mutProbs){
               for(TournamentRun r : runs){
                   if(somewhatEqual(r.getMutationProb(),d.doubleValue()) && somewhatEqual(r.getTournSize(), (i) ) ){
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

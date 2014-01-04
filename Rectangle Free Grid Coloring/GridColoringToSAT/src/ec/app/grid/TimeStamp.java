/**
 * Used to reduce the amount of printlns and determine when an individual is
 * hard coded into the GA population.
 *
 * @author Mike Groh
 */
package ec.app.grid;

public class TimeStamp {

    private static TimeStamp TS;

    public static TimeStamp getInstance() {
        if (TS == null) {
            TS = new TimeStamp();
        }
        return TS;
    }
    private boolean printResults = false;

    private TimeStamp() {
        Runnable runnable = new Time();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public boolean getPrintResults() {
        return printResults;
    }

    public void setPrintResults(boolean toPrint) {
        printResults = toPrint;
    }
}

class Time implements Runnable { // This method is called when the thread runs

    boolean printResults = false;

    public void run() {
        while (true) {
            printResults = false;
            TimeStamp.getInstance().setPrintResults(printResults);
            try {
                Thread.sleep(10000);
                printResults = true;
                TimeStamp.getInstance().setPrintResults(printResults);
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }
}
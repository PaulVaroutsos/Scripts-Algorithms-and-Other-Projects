package gridcoloringtosat;

/**
 *  This class contains the constants used throughout the gridcoloringtosat
 *  package.
 *
 *  It is important to remember to ensure that the ROW_SIZE and COLUMN_SIZE
 *  values match the size of the grid you would like to UnMap and Validate.
 *  It can be easy toforget if you map one grid size and decided to unmap
 *  a different size.
 * 
 * @author Mike Groh, Paul Varoutsos
 */
public class ConstantHolder {
     static final int NUM_COLORS = 4;
     static final int ROW_SIZE = 14;
     static final int COLUMN_SIZE = 8;
     static int BASE = ROW_SIZE;

     static{

        if( ROW_SIZE > COLUMN_SIZE){
            BASE = ROW_SIZE;
        }
        else{
            BASE = COLUMN_SIZE;
        }
     }
    static String SOLUTION_COUNTER =1+"";
}

/**
 *
 * There are 1156 variables for a 17x17 grid using 4 colors:
 *  17 * 17 * 4
 *
 * This class is the Mapper.  This class creates all clauses that are
 * required for all grid colorings.  Thse are constraitns such as:
 *
 *   //2023 clauses for constraint 1.1 and 1.2
 *   1.1    each box has at most 1 color
 *   1.2    each box has at least 1 color
 *   2.1    There should be no sub boxes with 4 corners of the same color
 *
 * Files will be output to src/gridcoloringtosat/cnf files/
 *
 *
 * @author Paul Varoutsos, Mike Groh
 * February 2010
 */
package gridcoloringtosat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Mapper {

     final int NUM_COLORS = ConstantHolder.NUM_COLORS;
     final int ROW_SIZE = ConstantHolder.ROW_SIZE;
     final int COLUMN_SIZE = ConstantHolder.COLUMN_SIZE;
     FileWriter out;
     int BASE = ConstantHolder.BASE;

    public Mapper(FileWriter out) {

        if( ROW_SIZE > COLUMN_SIZE){
            BASE = ROW_SIZE;
        }
        else{
            BASE = COLUMN_SIZE;
        }

        this.out = out;
    }

    /**
     * This method creates all of the clauses and outputs them to the file
     * defined by the constructor when the object was created.
     *
     * @throws java.io.IOException
     */
    public void buildGridColoringClauses() throws IOException {

        //Create all the clauses based on the given constraints
       int num_vars = (int)((Math.pow(ConstantHolder.BASE,3)
                +Math.pow(ConstantHolder.BASE,2) +ConstantHolder.NUM_COLORS));
       double term1 =(int)ConstantHolder.NUM_COLORS*nChoose2() ;
       double term2  = Math.pow(ConstantHolder.BASE,2);
       double term3 =         + Math.pow(ConstantHolder.BASE,2)*6;

        int num_cla = (int)(term1 +term2 +term3 );
        String fileStart = "p cnf " + num_vars + " " + num_cla + "\n";
        out.write(fileStart);
        constraint_11();
        constraint_12();
        constraint_21();

    }

    //Constraint 1.1
    //Check that each box has at least 1 color
    //V111 V V112 V V113 V ... V V17144
    private void constraint_11() throws IOException {

        for (int i = 1; i <= COLUMN_SIZE; i++) {
            for (int j = 1; j <= ROW_SIZE; j++) {
                for (int k = 1; k <= NUM_COLORS; k++) {
                    String var = yoDawgIHerdULikeBaseN(i,j,k);
                    out.write(var + " ");
                }
                out.write("0\n");
            }
        }
    }

    //Constraint 1.2
    //Check that each box has at most 1 COLOR
    //(-V111 V -V112) /\ (-V111 V -V113) /\ ...
    private void constraint_12() throws IOException {
        for (int i = 1; i <= COLUMN_SIZE; i++) {
            for (int j = 1; j <= ROW_SIZE; j++) {
                for (int k = 1; k <= NUM_COLORS; k++) {
                    for (int m = k + 1; m <= NUM_COLORS; m++) {
                        String temp;
                        temp = yoDawgIHerdULikeBaseN(i,j,k);
                        out.write("-" + temp + " ");
                        temp = yoDawgIHerdULikeBaseN(i,j,m);
                        out.write("-" + temp + " 0\n");
                    }
                }
            }
        }
    }

    //Constraint 2.1
    //Check that there are no squares containing 4 corners of the same color
    //(-V111 V -V112) /\ (-V111 V -V113) /\ ...
    public void constraint_21() throws IOException{

        for(int i = 1; i <= COLUMN_SIZE; i++){
            for(int j=1; j <= ROW_SIZE; j++){
                createSubSquares(i,j);
            }
        }


    }

    //creates the subsquare constrains for the position (i,j)
    private void createSubSquares(int x, int y) throws IOException{

        for(int i = x+1;i <= COLUMN_SIZE; i++){
            for(int j = y+1; j <= ROW_SIZE; j++){
                 //TopLeft corner
                 int topLeftX = x;
                 int topLeftY = y;

                 //BottomRight corner
                 int bottomRightX = i;
                 int bottomRightY = j;

                 //TopRight corner;
                 int topRightX = bottomRightX;
                 int topRightY = bottomRightY - (bottomRightY - topLeftY);

                 //BottomLeft corner
                 int bottomLeftX = bottomRightX - (bottomRightX - topLeftX);
                 int bottomLeftY = bottomRightY;

                 //Go through each color, make sure edges are not the same
                 for(int k = 1; k <= NUM_COLORS; k++){

                     String tempVar = yoDawgIHerdULikeBaseN(topLeftX,topLeftY,k);
                     out.write("-" + tempVar + " ");
                     tempVar = yoDawgIHerdULikeBaseN(bottomRightX,bottomRightY,k);
                     out.write("-" + tempVar + " ");
                     tempVar = yoDawgIHerdULikeBaseN(topRightX,topRightY,k);
                     out.write("-" + tempVar + " ");
                     tempVar = yoDawgIHerdULikeBaseN(bottomLeftX,bottomLeftY,k);
                     out.write("-" + tempVar + " 0\n");
                 }
            }
        }
    }

    /**
     * Converts a grid's i,j,k indicies into a  equivelent.  This
     * allows for discrimination between row, column, and color
     * The base is determined by ConstantHolder.java
     * @param i - row
     * @param j - column
     * @param k - color
     * @return Base ConstanteHolder.Base equivelent
     */
    private String yoDawgIHerdULikeBaseN(int i, int j, int k){

        return ((i* (int)Math.pow(BASE,2)) + (j*(int)Math.pow(BASE,1)) + (k*(int)Math.pow(BASE,0))) + "";
    }

    private double nChoose2() {
        int num = (int)(Math.pow((ConstantHolder.BASE*(ConstantHolder.BASE-1))/2,2));
        return num;
    }
     public static void main(String[] args) throws IOException{

        //The files that we use for input/ouput
        String fileName ="src/gridcoloringtosat/cnf files/" + ConstantHolder.ROW_SIZE + "x" + ConstantHolder.COLUMN_SIZE + ".cnf";
        File file = new File(fileName);
        FileWriter out = new FileWriter(file);

        Mapper m = new Mapper(out);

        m.buildGridColoringClauses();

        out.close();

    }

}
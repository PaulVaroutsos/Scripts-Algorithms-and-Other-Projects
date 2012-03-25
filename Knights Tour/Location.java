/**
 * This class is to assist the Knights Tour class to represent a location
 * on a chessboard.  It has a row and column element
 * @author Paul Varoutsos
 * 
 * November 2008
 *
 */
public class Location {
	
	//The board location's row
	private int row = 0;
	
	//The board location's column
	private int column = 0;
	
	//Constructor, setting the locations row and column
	public Location(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	//Returns this.row
	public int getRow(){
		return row;
	}
	
	//Returns this.column
	public int getColumn(){
		return column;
	}
	
	//Not used, but can set the row.
	public void setRow(int row){
		this.row = row;
	}
	
	//Not used, but can set the column
	public void setColumn(int column){
		this.column = column;
	}
}

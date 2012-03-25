import java.util.ArrayList;

/**
 * This class demonstrates the knights tour on a NxN sized board.
 * 
 * It uses H C Warnsdorff's technique which includes the following algorithm: 
 * 1. Mark squares when you land on them. 
 * 2. To decide where to land next, look at all the possible next unmarked 
 * 	  moves. Rank each possibility by the number of possible next moves from 
 * 	  that location. Move to the location with the lowest rank. In a tie, 
 * 	  any of the lowest scoring locations will work.
 * 
 * 
 * @author Paul Varoutsos
 * 
 * November 2008
 * 
 */
public class KnightsTour {

	//A 1 on the board represents a visited location
	private static final int VISITED = 1;

	//a 0 represents all other locations on the board
	private static final int UNVISITED_LOCATION = 0;

	private static  final int SIZE = 20;

	private int[][] board = new int[SIZE][SIZE];
	
	
	/**
	 * This method tests the Knights tour by starting the first knight at 0,0
	 * Which also could be started from any point.
	 */
	public void test(){
		
		//initialize all squares to 0 representing a
		//board with all squares unvisited.
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; i < SIZE; i++){
				board[i][j] = UNVISITED_LOCATION;
			}	
		}
	
		//call nextMove to start the tour, starting at the first 0,0 position
		//starting position could also be randomly generated...
		nextMove(0,0);
	}
	
	/**
	 * This method begins and produces the tour.
	 * First row and column start at 0, and go to 7 (for 8x8 board)
	 * 
	 * @param board This is the board for the knights tour
	 * @param row	The row of the knights current location
	 * @param column The column of the knights current location
	 * @return true if the tour is complete, false otherwise
	 * 
	 * In:	board - The chess board
	 * 		row - The knights current row location
	 * 		column - The knights current column location
	 * Out:		True if the tour is complete, no more moves to go to
	 * 			false otherwise, continue...
	 * Pre:		A valid row and column, and board that has unvisited
	 * 			locations
	 * Post:	The knight will have moved and the method will be called 
	 * 			recursively again with the new row and column location.
	 */
	public boolean nextMove(int row, int column){
		
		//This spot has been landed on.  Mark it
		board[row][column] = VISITED;
		
		//search for all next possible valid moves
		ArrayList<Location> nextMovePossibilities = possibleMoves(row,column);
		
		//no more spots to go to. tour is done
		if(nextMovePossibilities.size() == 0){
			displayBoard();
			System.out.println("Tour complete");
		}
		else{
		
		//Find the lowest scoring move
		
		Location nextMove = findBest(nextMovePossibilities);	
		//move there
		int nextRow = nextMove.getRow();
		int nextColumn = nextMove.getColumn();
		
		//we moved. display the board again
		displayBoard();
		System.out.println();
		System.out.println();
		
		return nextMove(nextRow,nextColumn);
		}
		
		return true;
	}
	
	/**
	 * Given a row and column, this method will return all possible
	 * next moves.
	 * 
	 * @param 	row The row to check
	 * @param 	column the column to check
	 * @return	All possible next moves from location: row,column
	 */
	public ArrayList<Location> possibleMoves(int row, int column){
		
		//this arraylist will hold all of the validmoves
		ArrayList<Location> validMoves = new ArrayList<Location>();
		
		//Each location has up to eight possible valid moves:
		//can either move up or down two spots and left or right one spot
		//or left or right two spots and up or down one spot
		
		//check up two, right one
		if( isValidMove(new Location(row+2, column +1)) ){
			validMoves.add(new Location(row+2, column +1));
		}
		
		//check up two, left one
		if( isValidMove(new Location(row+2, column-1)) ){
			validMoves.add(new Location(row+2, column-1));
		}	
		
		//check down two, right one
		if( isValidMove(new Location(row-2, column +1)) ){
			validMoves.add(new Location(row-2, column +1));
		}	
		
		//check down two left one
		if( isValidMove(new Location(row-2, column -1)) ){
			validMoves.add(new Location(row-2, column -1));
		}
		
		//check up one, right two
		if( isValidMove(new Location(row+1, column +2)) ){
			validMoves.add(new Location(row+1, column +2));
		}	
			
		//check up one, left two
		if( isValidMove(new Location(row+1, column -2)) ){
			validMoves.add(new Location(row+1, column -2));
		}	
		
		//check down one, right two
		if( isValidMove(new Location(row-1, column +2)) ){
			validMoves.add(new Location(row-1, column +2));
		}	
		
		//check down one, left two
		if( isValidMove(new Location(row-1, column -2)) ){
			validMoves.add(new Location(row-1, column-2));
		}	
		
		return validMoves;
	}
	
	/**
	 * This method will find the best location given an array of
	 * locations and return it.
	 * 
	 * @param Locations	All of the possible locations
	 * @return The best location to move to, the one with the
	 * 		   least amount of possible next moves
	 */
	public Location findBest(ArrayList<Location> locations){
		Location bestMove = null;
		
		//Best move score cannot be greater than 8
		//The max amount of moves from any location is 8
		//The first location will become the best move
		//It will then be adjusted if better one is found
		int bestMoveScore = 1000;	
		
		//Check all of the possible moves
		for(Location location : locations){
			
			//get the possible moves and store them in a list.  Use this list
			//to determine how many moves are possible from the location.  Its
			//size is the number of moves.  
			ArrayList<Location> movesFromThisPos = possibleMoves(location.getRow(), location.getColumn());
			
			//There is a better move, change the value of bestMove and bestMoveScore
			if(movesFromThisPos.size() < bestMoveScore){
				bestMove = new Location(location.getRow(), location.getColumn());
				bestMoveScore = movesFromThisPos.size();
			}
		}
		
		return bestMove;
	}
	
	/**
	 * This method checks to see if the location is valid
	 * 
	 * In:	The location to check validity
	 * Out:	True if the location can be moved to
	 * Pre:	None
	 * Post:None
	 */
	public boolean isValidMove(Location loc){
		
		//position is out of the range of the board
		if(loc.getRow() >= SIZE || loc.getColumn() >= SIZE){
			return false;
		}
		
		//position is out of the range of the board
		if(loc.getRow() < 0 || loc.getColumn() < 0){
			return false;
		}
		
		//the location has already been visited
		if(board[loc.getRow()][loc.getColumn()] == VISITED){
			return false;
		}
		
		//valid
		return true;
	}
	
	/**
	 * This just displays the board to system out.
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:Prints the board to standard out
	 */
	public void displayBoard(){
		
		//row
		for(int i = 0; i < SIZE; i++){
			
			//columns
			for(int j = 0; j < SIZE; j++){
				System.out.print(board[i][j]);
			}
			
			System.out.println();
		}
	}
}

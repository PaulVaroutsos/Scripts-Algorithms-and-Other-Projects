=begin

 This class demonstrates the knights tour on a NxN sized board.

 It uses H C Warnsdorff's technique which includes the following algorithm:
 1. Mark squares when you land on them.
 2. To decide where to land next, look at all the possible next unmarked
 	  moves. Rank each possibility by the number of possible next moves from
 	  that location. Move to the location with the lowest rank. In a tie,
 	  any of the lowest scoring locations will work.

March 2009
Paul Varoutsos

=end

#Multiple classes may be defined in one file.  This small class represents
#a chess board location with a row and a column
class Location

  #Getter and setting methods for location Class
  attr_accessor :row, :column

  #Constructor
  def initialize(row, column)
    @row = row
    @column = column
  end
end

#This is the class that does the knights tour algorithm
class KnightsTour
  #require Location
  
  def initialize
    @@VISITED = 1
    @@UNVISITED = 0
    @@SIZE = 8

    #Instances cariables start with @
    @board = [ [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0]
    ]
  end

  #Start the tour at the given location:
  def tour

    #Print to screen and take input
    puts "Enter a starting row: "
    row = gets
    puts "Enter a Starting Column: "
    column = gets

    #convert the row and column to an integer
    row = row.to_i
    column = column.to_i

    nextMove(row,column)
  end

  def nextMove(row,column)

    #This location on the board has been visited
    @board[row][column] = @@VISITED

    #Put all valid moves in an array
    next_move_possibilities = possibleMoves(row,column)

    #Check if the tour is done
    if next_move_possibilities.length == 0

      #Methods that do not have paremeters dont need parentheses
      displayBoard
      puts "Tour complete"
    else
      #Find the lowest scoring move
      next_move = findBest(next_move_possibilities)

      #move there
      next_row = next_move.row
      next_column = next_move.column

      #display progress
      displayBoard
      puts "\n"
      puts "\n"

      return nextMove(next_row,next_column)
    end

    return true
  end

  def possibleMoves(row, column)

    valid_moves = Array.new

    if isValidMove(Location.new(row+2, column+1))
      valid_moves << Location.new(row+2,column+1)
    end

    if isValidMove(Location.new(row+2,column-1))
      valid_moves << Location.new(row+2,column-1)
    end

    if isValidMove(Location.new(row-2,column+1))
      valid_moves << Location.new(row-2,column+1)
    end

    if isValidMove(Location.new(row-2, column-1))
      valid_moves << Location.new(row-2,column-1)
    end

    if isValidMove(Location.new(row+1, column+2))
      valid_moves << Location.new(row+1,column+2)
    end

    if isValidMove(Location.new(row+1, column-2))
      valid_moves << Location.new(row+1,column-2)
    end

    if isValidMove(Location.new(row-1, column+2))
      valid_moves << Location.new(row-1,column+2)
    end

    if isValidMove(Location.new(row-1, column-2))
      valid_moves << Location.new(row-1,column-2)
    end

    return valid_moves
  end

  def findBest(locations)
    best_move = nil
    best_move_score = 10

    #Equivelent to the for each loop on java 1.5+
    for location in locations
      moves_from_this_pos = possibleMoves(location.row,location.column)

      if(moves_from_this_pos.length < best_move_score)
        best_move = Location.new(location.row,location.column)
        best_move_score = moves_from_this_pos.length
      end
    end

    return best_move
  end

  def isValidMove(loc)
    if loc.row >= @@SIZE || loc.column >= @@SIZE
      return false
    end

    if loc.row < 0 || loc.column < 0
      return false
    end

    if @board[loc.row][loc.column] == @@VISITED
      return false
    end

    return true

  end

  def  displayBoard
    i = 0
    while i < @@SIZE
      j = 0
      while j < @@SIZE
        print @board[i][j]
        print " "
        j += 1
      end
      puts "\n"
      i +=1
    end
  end

end

kt = KnightsTour.new
kt.tour

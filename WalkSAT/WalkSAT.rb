class WalkSAT
  def initialize(file_name)
    ###Data structures and variables used...###
    #Array of variables and their assignments
    #Variables start at index 1 (a 0 delimits the end of a clause in DIMACS format)
    @assignments = Array.new
    
    #Array of booleans that represent whether or not the clause is ACTIVE(unsatisfied) in the formula
    #The index corresponds to the clause, starting at index 1
    @active_clauses = Array.new
    
    #A 2D array of all of clauses in the formula.
    #The array at index, x, contains all variables in the clause.
    #Clause x: @clauses_to_variables[x]
    @clauses_to_variables = Array.new
    
    #A 2D array that maps variables to all clauses in which they appear
    #For variable x,  The array containing all clauses that have variable x is at index (2x-1)
    #For variable -x, The array containing all clauses that have variable -x is at index (2x)
    #This is the reverse of the above 2D array.
    @variables_to_clauses = Array.new
    
    #The number of clauses that are active(unsatisfied) in the formula
    #This value should be equal to the number of TRUE values in active_clauses
    @num_clauses = -1
    
    #The maximum number of flips for WalkSAT
    @max_flips = 5000
    
    #The maximum number of restarts for WALKSAT
    @max_restarts = 100
    
    #Probability of chosing a random flip from an unsatisfied clause
    #@p = 0 is equal to the GSAT algorithm, WalkSAT typically uses .5
    @p = 0.5
    
    #Machine's minimum int value
    @@FIXNUM_MIN = -(2**(0.size * 8 -2))
    ###End data structures and variables used###
  
    clause_count = 1;
    File.open(file_name).each_line{ |s|
      if(s.start_with?("p")) #get # of clauses  and vars
        #DIMACS format: p cnf {vars} {clauses}
        vars = s.split(" ")[2].to_i
        @num_clauses = s.split(" ")[3].to_i
        @assignments = Array.new(vars+1,false)#There is never a variable 0, vars start at 1
        @active_clauses = Array.new(@num_clauses+1,true) #First clause at index 1

        #init all arrays
        @variables_to_clauses = Array.new(vars*2+1) #Create space for negated variables too
        for i in (0...@variables_to_clauses.size())
          @variables_to_clauses[i] = Array.new
        end
        @clauses_to_variables = Array.new(@num_clauses+1)
        for i in(0...@clauses_to_variables.size())
          @clauses_to_variables[i] = Array.new
        end
      elsif(s.start_with?("c")) #skip it
      else#get clause data
        vars_in_clause = s.split(" ")
        for i in 0...(vars_in_clause.size() -1) #the final 0 delimits the clause
          cur_var = vars_in_clause[i].to_i
          @clauses_to_variables[clause_count] << cur_var
          if(cur_var > 0)
            @variables_to_clauses[(cur_var*2)-1] << clause_count #Positive value at location (2var-1)
          else #negated variable, put it in the correct array location: abs(var)*2
            @variables_to_clauses[((-1*cur_var)*2)] << clause_count
          end
        end
        clause_count = clause_count + 1
      end 
    }
  end
  
  #The basic WalkSAT algorithm
  def solve
    for restart in (1..@max_restarts)
      random_assignment()
      for flip in (1..@max_flips)
        if(@num_clauses == 0)
          return @assignments
        else
          next_var = find_next_flip()
          flip(next_var,true)
        end#end if
      end#end max_flips
      if(@num_clauses == 0)
        return @assignments
      end
    end
    return nil #No solution found :(
  end
  
  #Generates a random assignment for all variables, resets all removed clauses, and applies the new assignment
  def random_assignment()
    for i in (0...@assignments.size())
      @assignments[i] = rand(2).zero?
    end
    @num_clauses = @active_clauses.size()-1
    @active_clauses = Array.new(@num_clauses+1,true) #First clause at index 1
    #Apply the new random assignment, remove satisfied clauses
    for i in 1...@assignments.size()
      flip(i,false)
    end
  end

  #Finds the next variable to flip
  #i.e. With probability 1-@p The one that satisfies the most variables if flipped (Can be 0 or negative)
  # =>  Or with probability @p, choose a random variable in an unsat clause
  def find_next_flip()
    walksat_prob = rand(100)/100.0
    if walksat_prob <= @p #Choose a variable from a random unsat clause and flip TODO make better
      for i in 1...@active_clauses.size()
        if(@active_clauses[i])#active clause = not sat
          var = @clauses_to_variables[i][rand(@clauses_to_variables[i].size())]
          if var > 0
            return var
          else
            return -var
          end
        end
      end
    else
      #choose the variable that sats the most clauses (can be 0 or neg)
      num_sat = 0
      best_var = 0
      best_var_score = @@FIXNUM_MIN #integer min
      for var in 1...@assignments.size()
        score = calculate_flip_affect(var)
        if(score > best_var_score)
          best_var = var
          best_var_score = score
        end
      end
      return best_var
    end
  end
  
  #How many SAT clauses would we gain if we fliped, var
  #This score is used to determine which variable to flip (the one that sats the most clauses)
  def calculate_flip_affect(var)
    num_of_clauses = @num_clauses 
    @assignments[var] = !@assignments[var] #do hypothetical flip (undone before return)
    
    #See if any clauses are affected by the change.
    #-1 for clauses that were true and became false 
    #+1 for clauses that were false and became true
    #0 for clauses not affected
    for clause_index in 0...@variables_to_clauses[(2*var)-1].size() #Check effects to all clauses containing V
      clause = @clauses_to_variables[@variables_to_clauses[(2*var)-1][clause_index]]
      is_sat = false
      for i in 0...clause.size()
        if((clause[i] > 0 and @assignments[clause[i]]) or (clause[i] < 0 and !@assignments[(-1)*clause[i]])) #is it sat?
          is_sat = true
          break #The clause is SAT, check next one without looking at more variables within the clause
        end
      end
      #If it wasn't SAT, was it SAT before?
      if is_sat
        if(@active_clauses[@variables_to_clauses[(2*var)-1][clause_index]])#Was it false before
          num_of_clauses -= 1 #Yup, it was. We gained a clause, +1
        end
      else
        if(!@active_clauses[@variables_to_clauses[(2*var)-1][clause_index]])#Was it true before
          num_of_clauses += 1 #Yup, it was.  We lost a clause  -1
        end
      end
    end #end clause loop
    
    for clause_index in 0...@variables_to_clauses[(2*var)].size() #Check effects to all clauses containing -V
      clause = @clauses_to_variables[@variables_to_clauses[(2*var)][clause_index]]
      is_sat = false
      for i in 0...clause.size()
        if((clause[i] > 0 and @assignments[clause[i]]) or (clause[i] < 0 and !@assignments[(-1)*clause[i]])) #is it sat?
          is_sat = true
          break #The clause is SAT, check next one without looking at more variables within the clause
        end
      end
      #if it wasnt SAT, was it SAT before?
      if is_sat
        if(@active_clauses[@variables_to_clauses[(2*var)][clause_index]])#Was it false before
          num_of_clauses -= 1 #Yup, it was. We gained a clause, +1
        end
      else
        if(!@active_clauses[@variables_to_clauses[(2*var)][clause_index]])#Was it true before
          num_of_clauses += 1 #Yup, it was.  We lost a clause  -1 
        end
      end
    end #end clause loop
    
    #undue assignment
    @assignments[var] = !@assignments[var]
    return @num_clauses - num_of_clauses
  end
  
  #Flips the variable, var, and updates data structures and variables
  #Also can be used to just update data structures after a randomn assignment is made.
  #When flip is false, no variable assignment is changed.
  def flip(var, flip)
    if(flip)
      #Flip it, flip it good
      @assignments[var] = !@assignments[var]
    end
    for clause_index in 0...@variables_to_clauses[(2*var)-1].size() #Check effects to clauses containing V
      clause = @clauses_to_variables[@variables_to_clauses[(2*var)-1][clause_index]]
      is_sat = false
      for i in 0...clause.size()
        if((clause[i] > 0 and @assignments[clause[i]]) or (clause[i] < 0 and !@assignments[(-1)*clause[i]])) #is it sat?
          is_sat = true
          break #The clause is SAT, check next one without looking at more variables within the clause
        end
      end 
      
      if is_sat #It is SAT
        if @active_clauses[@variables_to_clauses[(2*var)-1][clause_index]] #It wasn't sat before, so we got SAT another clause, -1 active clauses
          @active_clauses[@variables_to_clauses[(2*var)-1][clause_index]] = false
          @num_clauses -= 1
        end
      else #It wasn't SAT
        if(!@active_clauses[@variables_to_clauses[(2*var)-1][clause_index]]) #It was sat before, so we lost a clause +1 active clauses
          @active_clauses[@variables_to_clauses[(2*var)-1][clause_index]] = true
          @num_clauses += 1
        end
      end
    end #end clause loop
    
    for clause_index in 0...@variables_to_clauses[(2*var)].size() #Check effects to clauses containing -V
      clause = @clauses_to_variables[@variables_to_clauses[(2*var)][clause_index]]
      is_sat = false
      for i in 0...clause.size()
        if((clause[i] > 0 and @assignments[clause[i]]) or (clause[i] < 0 and !@assignments[(-1)*clause[i]])) #is it sat?
          is_sat = true
          break #The clause is SAT, check next one without looking at more variables within the clause
        end
      end
      
      if is_sat #It is SAT
        if @active_clauses[@variables_to_clauses[(2*var)][clause_index]] #It wasn't sat before, so we got SAT another clause, -1 active clauses
          @active_clauses[@variables_to_clauses[(2*var)][clause_index]] = false
          @num_clauses -= 1
        end
      else #It wasn't SAT
        if(!@active_clauses[@variables_to_clauses[(2*var)][clause_index]]) #It was sat before, so we lost a clause +1 active clauses
          @active_clauses[@variables_to_clauses[(2*var)][clause_index]] = true
          @num_clauses += 1
        end
      end
    end #end clause loop
  end
end

w = WalkSAT.new("test.cnf")
solution = w.solve
if solution != nil
  puts solution
else
  puts "Solution not found." 
end

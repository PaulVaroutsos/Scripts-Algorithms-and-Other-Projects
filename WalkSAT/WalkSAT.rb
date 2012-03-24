class WalkSAT
  def initialize(file_name)
    ###Data Structures used by WalkSAT
    #Array of variables and their assignments vars start at 0
    @assignments = Array.new
    
    #Array of booleans whose index corresponds to a clause and whose value 
    #represents whether or not they are active in the formula
    @active_clauses = Array.new
    
    #A hash whose key is the variable and whose value is an array containing
    #all of the clauses in which the variable can be found
    @variables_to_clauses = Array.new
    
    #A hash whose key is the clause and whose value is an array containing
    #all of the variables in which the clause can be found
    @clauses_to_variables = Array.new
    
    #The number of clauses that are active in the formula
    #should be equal to the number of TRUE values in activeClauses
    @num_clauses = -1
    ###End data structures
    
    ###Variables used by the WalkSAT algorithm
    #The maximum number of flips
    @max_flips = 5000
    
    #The maximum number of restarts
    @max_restarts = 100
    
    #Probability of chosing a random flip
    @p = 0.5
    ###End variables
  
    clause_count = 1;
    File.open(file_name).each_line{ |s|
      if(s.start_with?("p")) #get # of clauses  and vars
        vars = s.split(" ")[2].to_i
        @num_clauses = s.split(" ")[3].to_i
        @assignments = Array.new(vars+1,false)#There is never a variable 0
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
            @variables_to_clauses[(cur_var*2)-1] << clause_count
          else #negated variable, put it in the correct array location: abs(var)*2
            @variables_to_clauses[((-1*cur_var)*2)] << clause_count
          end
        end
        clause_count = clause_count + 1
      end 
    }
  end
  
  def solve
    for restart in (1..@max_restarts)
      puts restart
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
    return nil
  end
  
  #Generates a random assignment and stores it into the assignemnts variable
  def random_assignment()
    for i in (0...@assignments.size())
      @assignments[i] = rand(2).zero?
    end
    @num_clauses = @active_clauses.size()-1
    @active_clauses = Array.new(@num_clauses+1,true) #First clause at index 1
    #apply the random assignment
    for i in 1...@assignments.size()
      flip(i,false)
    end
  end

  #Finds the next variable to flip
  #i.e. The one that satisfies the most variables if flipped (Can be 0 or negative)
  # =>  Or with probability @p, choose a random variable in an unsatclause
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
      best_var_score = -11111111 #TODOchange to integer min
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
  
  #how many SAT clauses would we gain if we fliped, var
  def calculate_flip_affect(var)
    num_of_clauses = @num_clauses 
    @assignments[var] = !@assignments[var] #do hypothetical flip (undone before return)
    
    #See if any clauses are affected by the change.
    #-1 for clauses that were true and became false 
    #+1 for clauses that were false and became true
    #0 for clauses not affected
    for clause_index in 0...@variables_to_clauses[(2*var)-1].size() #Check effects to V
      clause = @clauses_to_variables[@variables_to_clauses[(2*var)-1][clause_index]]
      is_sat = false
      for i in 0...clause.size()
        if((clause[i] > 0 and @assignments[clause[i]]) or (clause[i] < 0 and !@assignments[(-1)*clause[i]])) #is it sat?
          is_sat = true
          break #The clause is SAT, check next one without looking at more variables within the clause
        end
      end
      #if it wasnt SAT, was it SAT before?
      if is_sat
        if(@active_clauses[@variables_to_clauses[(2*var)-1][clause_index]])#was it false before
          num_of_clauses -= 1 #yup, it was. we gained a clause, +1
        end
      else
        if(!@active_clauses[@variables_to_clauses[(2*var)-1][clause_index]])#was it true before
          num_of_clauses += 1 #yup, it was.  We lost a clause  -1 clause
        end
      end
    end #end clause loop
    
    for clause_index in 0...@variables_to_clauses[(2*var)].size() #Check effects to V
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
        if(@active_clauses[@variables_to_clauses[(2*var)][clause_index]])#was it false before
          num_of_clauses -= 1 #yup, it was. we gained a clause, +1
        end
      else
        if(!@active_clauses[@variables_to_clauses[(2*var)][clause_index]])#was it true before
          num_of_clauses += 1 #yup, it was.  We lost a clause  -1 clause
        end
      end
    end #end clause loop
    
    #undue assignment
    @assignments[var] = !@assignments[var]
    return @num_clauses - num_of_clauses
  end
  
  #Flips the variable and updates data structures
  def flip(var, flip)
    if(flip)
      #Flip it, flip it good
      @assignments[var] = !@assignments[var]
    end
    for clause_index in 0...@variables_to_clauses[(2*var)-1].size() #Check effects to V
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
    
    for clause_index in 0...@variables_to_clauses[(2*var)].size() #Check effects to V
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

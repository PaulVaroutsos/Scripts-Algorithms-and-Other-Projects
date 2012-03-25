/**
 * This class recognizes and evaluates fully parenthesized infix expressions.
 * Infix expressions are in the form of ( A + B )
 * The following example is invalid:
 * 	( A - B ) + ( C - D )
 * It should be
 * ( ( A - B ) + ( C - D ) )
 * 
 * Data Fields:
 * 	start - Used to keep track of the start of an infix expression when
 * 			using a method that calls recursion twice
 * 
 * Public Methods:
 * 	evaluateInfix(String) - Evaluates the given infix expression and returns
 * 							the result
 * 	isInfix(String - Determines if the infix expression is valid
 * 
 * Private Methods:
 * 	checkParentheses - Determines if the parentheses in an infix expression are
 * 					   evenly matched
 * 	evaluateIn(String[]) - Uses recursion to evaluate the expression.
 * 	findMiddle(String[], int, int) - Finds the middle operator in an expression
 * 	isInfixExpression(String[], int, int) - Determines if the infix expression
 * 				- is valid
 * 	isOp() - Determines if the character is an operator
 * 
 * @author Paul Varoutsos
 * November 2008
 * 
 *
 */
public class InFix {
	
	//Used by evaluateInfix() to keep track of the start location.
	//This is needed because evaluateInfix() calls recursion twice
	//inside the method, so we need a way to keep track of the 
	//start w/o losing it before the next recursive call and return
	//back to previous method call.
	private static int start;
	
	/**
	 * Purpose:		This is the public method called to evaluate an infix expression.
	 * 				It calls isInfix first, to check to see if it is 
	 * 				invalid, then evaluates it by calling a private method that
	 * 				uses recursion.
	 * 
	 * In:	expr - The expression to evaluate
	 * Out: Double.NaN if the expression is invalid (e.g. divides by 0)
	 * 		The value of the evaluated expression otherwise/
	 * Pre:	None
	 * Post:None
	 * 
	 * @param expr - The expression to evaluate
	 * @return The value of the expression
	 */
	public static double evaluateInfix(String expr){
		
		//Check to see if it is a valid expression
		//If invalid return Double.NaN because it is invalid
		if (!isInfix(expr)) {
			return Double.NaN;
		}
		
		//Expression is valid, split it into an array of strings 
		String[] stringArray = expr.split(" ");
		
		//Initialize the field start so it will change with each
		//recursive call  and start at the appropriate location
		start = 0;
		
		//Call the private method to evaluate it
		return evaluateIn(stringArray);
	}
	
	/**
	 * Purpose:		This is the private method called by evaluateInFixExpression
	 * 				to recursively evaluate the expression and return the result.
	 * 
	 * In:	stringArray - The expression as an array in which to evaluate
	 * Out:	Double.NaN if the expression is invalid (e.g. divide by 0)
	 * 		The result of the expression otherwise
	 * Pre:	None
	 * Post:None
	 * 
	 * @param stringArray The expression to evaluate
	 * @return The value of the expression
	 */
	private static double evaluateIn(String[] stringArray){

		//If there is an opening bracket, there is another expression
		//to evaluate.  All expressions are in the form of:
		// ( <Operand> <operator> <Operand> )

		//All expressions start and end with ( and ) for example:
		// ( ( 4 + 2 ) - ( 5 - 1 ) )
		//If the first char is a '(' begin by increasing the start
		//location and make a recursive call.
		if(stringArray[start].charAt(0) == '('){
			start = start+1;

			//Get operand1
			Double operand1 = evaluateIn(stringArray);

			//Go to the next position
			start = start +1;

			//We can ignore ')'s
			while(stringArray[start].charAt(0) == ')'){
				start = start+1;
			}

			//Every operator is preceded by an operand
			char op = stringArray[start].charAt(0);

			//Next pos
			start = start +1;

			//Again ignore ')'s
			while(stringArray[start].charAt(0) == ')'){
				start = start+1;
			}

			//Get operand 2
			Double operand2 = evaluateIn(stringArray);

			//Choose which operation to do now that we have both
			//operands and an operator
			switch (op) {

			case '*':
				return operand1 * operand2;
			case '-':
				return operand1 - operand2;
			case '+':
				return operand1 + operand2;
			case '/':
				if(operand2 != 0){
					return operand1 / operand2;
				}
				else 
					return Double.NaN;

			case '^':
				return Math.pow(operand1,operand2);
			default:
				return Double.NaN;
			}
		}//end if

		//Return result
		return Double.parseDouble(stringArray[start]);			
	}
	
	/**
	 * Purpose:	This method is the public method that determines if the given expression
	 * 			is valid.  This method depends on isIn() to determine if the expression
	 * 			is in the correct form.  This method does checking to see if there are
	 * 			any invalid characters or numbers that are not operands or operators
	 * 			and returns false if any are found.
	 * 
	 * In:	The expression as a string
	 * Out:	True, if the expression is  valid
	 * 		False, otherwise
	 * Pre:	None
	 * Post:None
	 * 
	 * @param expr - The string to determine validity
	 * 
	 * @return True, if the expression is  valid
	 * 		   False, otherwise
	 */
	public static boolean isInfix(String expr){
		
		//Start by splitting the expression into an array of strings
		String[] stringArray = expr.split(" ");
		
		//First we need to check that each element in the new 
		//array is a valid number or a valid operator.  If
		//they are not, then we return false.  If they are,
		//then we must continue our validity check to see
		//if the expression is in proper Infix form.
		
		//Check to make sure each element in the array is either a number
		//or a single character operator.  If they are not, then return
		//false.  If they are, they we muse continue and check to see if
		//it is in valid form:
		for(int i=0; i < stringArray.length; i++){
			try{
				
				//If parse is unsuccessful, it is not a number, 
				//check to see if it is a valid operand:  
				//1 character long and is + - ^ / *
				Double.parseDouble(stringArray[i]);
					
			}
			//Thrown when the parse fails.  The string is
			//Not a valid number
			catch(NumberFormatException e){

				//If it is 1 character long, check to see if it is
				//a valid operand
				if(stringArray[i].length() == 1){
					
					//It is one character long
					//Make a character out of the string
					//So we can do a switch on it
					char op = stringArray[i].charAt(0);
					
					//Valid operands
					switch(op){
					case '*':
					case '/':
					case '+':
					case '-':
					case '^':
					case '(':
					case ')':
						//valid, keep checking other strings
						break;
					default:
						//invalid character
						return false;
					}
				}
				//Found a string that is not a number and is more than
				//one character so it is not an operator
				else{
					return false;
				}
			}
		}
		
		//All of the strings in the array are valid operators or operands
		//We need to check whether it is in valid Infix form
		
		//Start by checking that each '(' is accompanied by a ')'
		//This method does not check of cases like ')' followed by '(' but
		//it is a quick approximation.
		//This method is not needed because it is checked by isIn() anyway
		if( ! checkParentheses(stringArray) ){
			//return false, parentheses are incorrect
			return false;
		}
		
		//The recursive call that determines if the expression is in the form of
		//<operand> <op> <operand>
		return isIn(stringArray, 0,stringArray.length -1);
	}
	
	
	/**
	 * Purpose:		This method checks to make sure the parentheses are balanced.
	 * 				This method is not needed, but is a quick approximation to
	 * 				see if the expression has matching parentheses.  IsIn() will
	 * 				do the actual accurate checking for matching parentheses
	 * 
	 * In:	stringArray - The array to check for balanced parentheses
	 * Out:	true if parentheses are balanced, false otherwise
	 * Pre:	None
	 * Post:None
	 * 
	 * @param stringArray The array to check for balanced parentheses
	 * @return True if parentheses are balanced
	 * 			False otherwise
	 */
	private static boolean checkParentheses(String[] stringArray){
		
		//Increase this counter when a ( is found
		//Decrease thus counter when a ) is found
		//Will be zero if parentheses are balanced
		int parenCount = 0;
		
		for(int i = 1; i < stringArray.length - 1;i++){
			
			//Found opening brace
			if(stringArray[i].charAt(0) == '('){
				parenCount++;
			}
			
			//found closing brace
			if(stringArray[i].charAt(0) == ')'){
				parenCount--;
			}
		}
		
		return parenCount == 0;
	}
	
	
	
	/**
	 * Purpose:	Recursive method to check that the expression is in a valid
	 * 			infix form.  This method uses findMiddle() to find the middle
	 * 			of the expression, then recursively checks each side of the 
	 * 			middle to see if each side is a valid expression or constant
	 * 			number.
	 * 
	 * In:	stringArray - The expression to evaluate
	 * 		start - The start location of where you want to begin
	 * 				checking the expression or subexpression.
	 * 		end - The end location of where you want to stop checking
	 			  the expression or subexpression
	 * Out:	Returns The location of the middle operand of the expression
	 * 		Returns -2 if there is only one element in the subexpression
	 * 		    and it is a valid number, then it is a valid expression which is
	 * 			either <infix> <op> <infix> || <infix> .  A single number is 
	 * 			considered <infix>
	 * 		Returns -1 If there is no valid middle operand.  The expression is
	 * 				invalid.
	 * Pre:	None
	 * Post:Nonr
	 * @param stringArray - The expression to evaluate
	 * @param start - The start location of where you want to begin
	 * 				  checking the expression or subexpression.
	 * @param end - The end location of where you want to stop checking
	 * 				the expression or subexpression
	 * @return Returns The location of the middle operand of the expression
	 * 		   Returns -1 if there is only one element in the subexpression
	 * 		    and it is a valid number, then it is a valid expression which is
	 * 			either <infix> <op> <infix> || <infix> .  A single number is 
	 * 			considered <infix>
	 * 		   Returns -2 If there is no valid middle operand.  The expression is
	 * 				invalid.
	 */
	private static boolean isIn(String[] stringArray, int start, int end){
		
		//Get the middle location
		int middleOpLoc = findMiddle(stringArray, start, end);
		
		//There is no middle location.  Return false.  The expression is invalid
		if(middleOpLoc == -2){
			return false;
		}
		
		//The expression was just a valid number so it is a valid expression
		//Return true
		if(middleOpLoc == -1){
			return true;
		}
		
		//We want to check to the left and the right of the middle operator to make sure
		//That is is preceded and succeeded by valid operands:  Either a single operand
		//number or another expression.
		return isIn(stringArray,start+1, middleOpLoc-1) && isIn(stringArray, middleOpLoc+1, end-1);
	}
	
	/**
	 * Purpose:  This method will find the middle operator of an expression
	 * 			 and return its index location in the array.  This will allow
	 * 			 for a recursive call on each side of the location to check
	 * 			 if each side of the operator is a valid expression.
	 * 
	 * In:	stringArray[] - The expression as an array of strings
	 * 		start - The starting point of hte expression
	 * 		out - The end point of the expression
	 * Out:	The index in the array that the middle operator of the expression
	 * 		is.
	 * 		-2 If there is no middle operand location. This means the expression
	 * 		is invalid so the entire expression contained in stringArray is invalid
	 * 		-1 If the expression is just a single valid number.  This is a valid
	 * 		expression.
	 * Pre:	None
	 * Post:None
	 * 
	 * 
	 * @param stringArray  - The expression as an array of strings
	 * @param start  - The starting point of hte expression
	 * @param end  - The end point of the expression
	 * @return The index in the array that the middle operator of the expression
	 * 			is.
	 * 		-2 If there is no middle operand location. This means the expression
	 * 			is invalid so the entire expression contained in stringArray is invalid
	 * 		-1 If the expression is just a single valid number.  This is a valid
	 */
	private static int findMiddle(String[] stringArray, int start, int end){
		
		//it is valid operand
		if(start == end){
			if(!isOp(stringArray[start].charAt(0)))
			{
				return -1;
			}
		}
		
		//For every '(' we find, Increase count by one.  For every
		// ')' we find, decrease count by one.  When the count
		//reaches zero, we have found the index of the middle operator
		//Which is start +1
		int parenCount = 0;
		start++;
		end--;
		
		//Stop when start and end are the same point
		while(!(start >= end))
		{
			//We found '(' increase count and index
			if(stringArray[start].charAt(0) == '('){
				parenCount++;
			}
			else if(stringArray[start].charAt(0) == ')')
			{
				parenCount--;
			}
			//The next position is the operator
			//If start is a number and start+1 is an operator (not a parent)
			//return 

			//Parentheses count is 0
			else if(parenCount == 0){
				
				//if stringArray[start] is a operator, return its location
				if(isOp(stringArray[start].charAt(0)))
					return start;
				
				//if it is a number and the next string in the array is a 
				//operand, then return the location of the operand (start +1)
				else if(isOp(stringArray[start +1].charAt(0)) && !isOp(stringArray[start].charAt(0))){
					return start+1;
				}
			}
			
			//Increment start location
			start++;
		}
		
		//Invalid
		return -2;
	}
	
	/**
	 * Purpose:	Checks to see if the character is a operand
	 * 			This method is used by find middle.
	 * 
	 * In:	c - The character to check
	 * Out:	True, if the character is a valid operator
	 * 		False, otherwise
	 * Pre:	None
	 * Post:None
	 * 
	 * @param c - The character to check
	 * @return True, if the character is a valid operator
	 * 		   False, otherwise
	 */
	private static boolean isOp(char c){
		return  c == '+' || 
				c == '*' || 
				c == '/' || 
				c == '^' || 
				c == '-';
		
	}
}
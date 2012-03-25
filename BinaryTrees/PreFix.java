/**
 * This class will recognize and evaluate prefix expressions.
 * 
 * @author Paul Varoutsos
 * November 2008
 * 
 */
public class PreFix {
	
	//Used for isPosFix method to keep track of the position in
	//the string as we make recursive calls
	private static Integer start;

	/**
	 * Constructor, currently unused.  All of the methods are static and have 
	 * String parameters which are the strings to check or evaluate
	 * Syntax is Prefix.evaluateprefixExpression(String);
	 * 
	 */
	public PreFix() {
	}

	/**
	 * Purpose:     This is the public method used to check the validity
	 * 		of a prefix expression.
	 * In:		expr - The string to evaluate
	 * Out:		true, if expr is in valid prefix form
	 * 			false, otherwise
	 * Pre:		None
	 * Post:	Returns wheter or not the expression is valid
	 * 
	 * @param expr - The string to check validity
	 * @return true is valid, false otherwise
	 */
	public static boolean isPrefix(String expr) {
		
		//Start by splitting the expression into an array of strings
		String[] stringArray = expr.split(" ");
		
		//First we need to check that each element in the new 
		//array is a valid number or a valid operator.  If
		//they are not, then we return false.  If they are,
		//then we must continue our validity check to see
		//if the expression is in proper prefix form.
		
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
			//Not a valid number
			catch(NumberFormatException e){

				//If it is 1 character long, check to see if it is
				//a valid operand
				if(stringArray[i].length() == 1){
					
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
		//We need to check whether it is in valid prefix form
		
		//call the recursive method isPre()
		if(isPre(expr)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Purpose:	Determines if the given prefix expression is in valid prefix form
	 * 
	 * In:	The expression as a string to check
	 * Out:	True, if the expression is valid
	 * 		False, otherwise
	 * Pre:	None
	 * Post:Returns the validity of the expression
	 * 
	 * @param string - The string to check validity
	 * @return - True, if the expression is valid
	 * 			False, otherwise
	 */
	private static boolean isPre(String string){
		
		//Split the string into an array of strings
		String[] stringArray = string.split(" ");
		
		int size = stringArray.length;
		
		//Finds the end of the last prefix expression
		int lastString = endPre(stringArray,0,size-1);
		
		//If lastString is greater than zero and less than
		//lastString than we have gone through the entire
		//expression and it is valid
		if(lastString >= 0 && lastString == size-1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Purpose:	Finds the end of the prefix expression if one exists
	 * 
	 * In:	stringArray - The expression as an array of string
	 * 		first - The start location of the last prefix expression
	 * 		last - The end location of the last prefix expression
	 * Out:	The index of the end of the prefix expression
	 * 		-1 if no prefix expression exists
	 * Pre:	None
	 * Post:Retunrs the index of the last string that begins at first
	 * 
	 * 
	 * @param stringArray - The expression as an array of string to check
	 * @param first - The stat of the expression
	 * @param last - The end of the expression
	 * @return The index of the end of the prefix expression
	 * 			Returns -1 if no prefix expression exists.
	 */
	private static int endPre(String[] stringArray, int first, int last){
		
		if(first < 0 || first > last){
			return -1;
		}
		
		String next = stringArray[first];
		
		//if next is a number, return first
		//Other strings were ruled out in the public method
		if( !(next.charAt(0) == '+' || next.charAt(0) == '-' || next.charAt(0) == '*' ||
				next.charAt(0) == '/' || next.charAt(0) == '^')){
			
			//index of the last character in the prefix expression
			return first;
		}
		//operator
		else{
			
			//find the end of the first prefix expression
			int firstEnd = endPre(stringArray,first+1,last);
			
			
			//if the first end was found, find the end of the second
			if(firstEnd > -1){
				return endPre(stringArray, firstEnd +1,last);
			}
			else{
				return -1;
			}
		}
		
	}

	/**
	 * Purpose:	This method evaluates the given post fix expression
	 * 			as a string and returns the result.
	 * 
	 * In:		A post fix expression as a string
	 * Out:		Double.NaN if the expression is an invalid post fix
	 * 			expression.  
	 * 			The result, if the expression is valid
	 * Pre:		None
	 * Post:	None
	 * 
	 * 
	 * @param expr - The expression to be evaluated
	 * @return	The result of the expression
	 */
	public static double evaluatePrefix(String expr) {
		
		//Check to see if it is a valid expression
		//If invalid return Double.NaN because it is invalid
		if (!isPre(expr)) {
			return Double.NaN;
		}
		
		//Expression is valid, split it into an array of strings
		String[] stringArray = expr.split(" ");
		
		//Initialize the field start so it will change with each
		//recursive call
		start = 0;
		
		//Call the private method
		return evaluatePre(stringArray);
	}

	/**
	 * Purpose:	This method accompanies the public evaluate method
	 * 			to evaluate the expression recursively.
	 * 
	 * In:		A post fix expression as a String[]
	 * Out:		Double.NaN if the expression is an invalid post fix
	 * 			expression.  
	 * 			The result, if the expression is valid
	 * Pre:		None
	 * Post:	None
	 * 
	 * 
	 * @param stringArray - The expression to be evaluated
	 * @return	The result of the expression
	 */
	private static double evaluatePre(String[] stringArray) {
		
		// check to see if it is a number or a operator
		try {
			
			//Double.parseDouble() takes a string paramater and tries to parse
			//the string into a Double.  If it fails, it will throw a 
			//NumberFormatException, implying that it is a non-numerical
			//value which must be a operator
			
			//Double.isNaN() takes a double parameter which returns true if
			//the parameter is not a valid double, true otherwise
			if (!Double.isNaN(Double.parseDouble(stringArray[start]))) {
				
				//It is a value, return it
				return Double.parseDouble(stringArray[start.intValue()]);
			}
		}

		// If we catch this exception, it is not a number so it
		// must be an operator
		catch (NumberFormatException e) {

			// get the operator
			String operator = stringArray[start.intValue()];
			char op = operator.charAt(0);

			// recursive calls to get each operand
			start = start + 1; 
			Double operand1 = evaluatePre(stringArray);
			
			start = start + 1; 
			Double operand2 = evaluatePre(stringArray);

			//Choose which operation to do
			switch (op) {

			case '*':
				return operand1 * operand2;
			case '-':
				return operand1 - operand2;
			case '+':
				return operand1 + operand2;
			case '/':
				if(operand2 != 0)
					return operand1 / operand2;
				else
					return Double.NaN;
			case '^':
				return Math.pow(operand1,operand2);
			default:
				return Double.NaN;
			}
		}
		//Thrown when isNaN is called
		catch(IndexOutOfBoundsException e){
			System.err.println("Evaluating expression:  Improperly formated prefix expression.");
			return Double.NaN;
		}

		//Needed to compile
		return -1;

	}
}

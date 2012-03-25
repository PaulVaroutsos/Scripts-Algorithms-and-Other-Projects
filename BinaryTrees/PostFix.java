/**
 * This class will recognize and evaluate postfix expressions.
 * Postfix is in the form: A B +
 * 
 * Data Fields:
 * 	length - Used for recursive methods to keep track of the position
 * 			 in the string as we make recursive calls.  This is needed
 * 			 when two recursive calls are made within the same method.
 * 
 * Public Methods:
 * 	evaluatePostfix(String) - Used to evaluate a postfix expression.
 * 	isPost() - Used to determine if a postfix expression is valid
 * 
 * Private Methods:
 * 	isPost(String) - Used to determine if the expression is a valid
 * 					 postfix expression
 * evaluatePost(String[]) - Used to recursively evaluate the expression
 * 						    and return the result
 * beginPost(String[]) - Used to find the beginning of the last postfix expression
 * 					   This method is used in isPost().
 * 
 * 
 * @author Paul Varoutsos
 * November 2008
 * 
 */
public class PostFix {
	
	//Used in evaluatePosfix method to keep track of the position in
	//the string as we make recursive calls.  The position will be 
	//reset when dealing with a method that makes two recursive calls.
	private static Integer length;

	/**
	 * Constructor, currently unused.  All of the methods are static and have 
	 * String parameters which are the strings to check or evaluate.
	 * This allows us to check the validity and evaluate expressions without
	 * creating an object.
	 * Syntax is PostFix.evaluatePostFixExpression(String);
	 * 
	 */
	public PostFix() {
	}

	/**
	 * Purpose:	This is the public method used to check the validity
	 * 			of a postfix expression.
	 * In:		expr - The expression, as a string, to evaluate
	 * Out:		true, if expr is in valid postfix form
	 * 			false, otherwise
	 * Pre:		None
	 * Post:	None
	 * 
	 * @param expr - The string to check validity
	 * @return true is valid, false otherwise
	 */
	public static boolean isPostfix(String expr) {
		
		//Start by splitting the expression into an array of strings
		String[] stringArray = expr.split(" ");
		
		//First we need to check that each element in the new 
		//array is a valid number or a valid operator.  If
		//they are not, then we return false.  If they are,
		//then we must continue our validity check to see
		//if the expression is in proper postfix form.
		
		//Check to make sure each element in the array is either a number
		//or a single character operator.  If they are not, then return
		//false.  If they are, they we muse continue and check to see if
		//it is in valid form:
		for(int i=0; i < stringArray.length; i++){
			try{
				
				//If parse is unsuccessful, it is not a number, 
				//we need to then check to see if it is a
				//valid operand:  
				//1 character long and is + - ^ / or *
				Double.parseDouble(stringArray[i]);
					
			}
			//Not a valid number, check for operand
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
		//We need to check whether it is in valid postfix form
		
		if(isPost(expr)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Purpose:	This method determines if the expression is a
	 * 			valid prefix expression
	 * 
	 * In:	string - The expression as a string to check
	 * Out:	True if the expression is in postfix form
	 * 		False, otherwise
	 * Pre: None
	 * Post:Returns the validity of the expression
	 * 
	 * @param string - The string to check
	 * @return True if valid postfix
	 *          False otherwise.
	 */
	private static boolean isPost(String string){
		
		String[] stringArray = string.split(" ");
		
		
		int size = stringArray.length;
		
		//Determine the location of the start of the last 
		//postfix expression
		int lastString = beginPost(stringArray,0,size-1);
		
		//If the location is 0, then the entire string
		//is a valid postfix expression.
		if(lastString == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Purpose:	Find the start of the last postfix expression.  This
	 * 			method is used by isPost() to determine if the
	 * 			expression is valid
	 * 
	 * In:	stringArray - The expression as an array of strings
	 * 		first - The beginning of the last postfix expression
	 * 		last - The end of the last expression
	 * Out:	Returns the index of the last string in expression
	 * 		that begins at first if one exists. 
	 * 		Returns -1 if no postfix expression exists
	 * Pre:	None
	 * Post:None
	 * 
	 * 
	 * @param stringArray - The expression as an array of strings
	 * @param first - The beginning of the last postfix expression
	 * @param last - The end of the last expression
	 * @returnReturns the index of the last string in stringArray
	 * 		  that begins at first if one exists. 
	 * 		  Returns -1 if no postfix expression exists
	 */
	private static int beginPost(String[] stringArray, int first, int last){
		
		//If we overlap, we did not find the start point of the expression
		if(last < 0 || first > last){
			return -1;
		}
		
		String next = stringArray[last];
		
		//if next is a number, return first
		//invalid strings were ruled out already
		if( !(next.charAt(0) == '+' || next.charAt(0) == '-' || next.charAt(0) == '*' ||
				next.charAt(0) == '/' || next.charAt(0) == '^')){
			
			//return last, found a number
			return last;
		}
		//it was an operator
		else{
			
			//find the first start of the expression
			int firstEnd = beginPost(stringArray,first,last-1);
			
			//if the first start was found we need to find the
			//second start
			if(firstEnd > -1){
				return beginPost(stringArray,first,firstEnd-1);
			}
			else{
				//No first start
				return -1;
			}
		}
	}

	/**
	 * Purpose:	This method evaluates the given post fix expression
	 * 			as a string and returns the result.
	 * 
	 * In:		A post fix expression as a string
	 * Out:		Double.NaN if the expression is an invalid postfix
	 * 			expression.  
	 * 			The result, if the expression is valid
	 * Pre:		None
	 * Post:	None
	 * 
	 * 
	 * @param expr - The expression to be evaluated
	 * @return	THe result of the expression
	 */
	public static double evaluatePostfix(String expr) {
		
		//Check to see if it is a valid expression
		//If invalid return Double.NaN because it is invalid
		if (!isPostfix(expr)) {
			return Double.NaN;
		}
		
		//Expression is valid, split it into an array of strings
		String[] stringArray = expr.split(" ");
		
		//Initialize the field length so it will change with each
		//recursive call correctly
		length = new Integer(stringArray.length - 1);
		
		//Call the private method
		return evaluatePost(stringArray);
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
	private static double evaluatePost(String[] stringArray) {
		
		// check to see if it is a number or a operator
		try {
			
			//Double.parseDouble() takes a string parameter and tries to parse
			//the string into a Double.  If it fails, it will throw a 
			//NumberFormatException, implying that it is a non-numerical
			//value which must be a operator. Invalid strings were ruled
			//int in isPostfix()
			
			//Double.isNaN() takes a double parameter which returns true if
			//the parameter is not a valid double, false otherwise
			if (!Double.isNaN(Double.parseDouble(stringArray[length]))) {
				
				//It is a value, return it
				return Double.parseDouble(stringArray[length.intValue()]);
			}
		}

		// If we catch this exception, it is not a number so it
		// must be an operator
		catch (NumberFormatException e) {

			// get the operator
			String operator = stringArray[length.intValue()];
			char op = operator.charAt(0);

			// recursive calls to get each operand
			length = length - 1; 
			Double operand1 = evaluatePost(stringArray);
			
			length = length - 1; 
			Double operand2 = evaluatePost(stringArray);

			//Choose which operation to do
			switch (op) {

			case '*':
				return operand2 * operand1;
			case '-':
				return operand2 - operand1;
			case '+':
				return operand2 + operand1;
			case '/':
				if(operand1 != 0)
					return operand2 / operand1;
				else
					return Double.NaN;
			case '^':
				return Math.pow(operand2,operand1);
			default:
				return Double.NaN;
			}
		}

		//Needed to compile
		return -1;

	}
}

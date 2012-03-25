/**
 * This class is used by ExpressionGUI to generate a random expression.
 * Bases on the parameter to generateExpression(), the method will return
 * a prefix expression if the parameter is a '1', postfix expression if
 * the parameter is '2' and an infix expression if the parameter is '3'
 * 
 * @author Paul Varoutsos
 * November 2008
 */
import java.util.Random;

public class ExpressionGenerator {
	
	//Random number generator used to create random expression
	//lengths and random number values for operands
	private static final Random rand = new Random();
	
	/**
	 * Purpose:	Generate a random expression based on the input x which decided
	 *			the type of expression to generate.
	 *
	 *In:	x - Determines the type of expression
	 *Out:	a valid infix prefix or postfix expression
	 *Pre:	X is a 1 2 or 3
	 *Post:	an expression is created and returned
	 *
	 * @param x - Determines the type of expression
	 * 
	 * @return - a valid infix prefix or postfix expression
	 */
	public static String generateExpression(int x){
		
		switch(x){
		case 1:
			//make prefix
			return generatePrefix();
		case 2:
			//make postfix
			return generatePostfix();
		case 3:
			//make infix
			return generateInfix();
		default:
			return null;
		}
	}
	
	/**
	 * Purpose:	Generate a random prefix expression
	 *
	 *In:	None
	 *Out:	a valid infix prefix or postfix expression around 3 to 30 characters
	 *Pre:	None
	 *Post:	an expression is created  and returned
	 * 
	 * @return - a valid prefix expression
	 */
	private static String generatePrefix(){
		//determine size between 0 and 10
		int size = rand.nextInt(10)+1;
		
		String expression = "";
		//Expressions should be between 3 and 30 characters
		//size * 3 creates  a number from 3 to 30
		//increase i by 3 each time because we add 3 or 4 things each time
		for(int i = 0; i< 3*size; i= i+3){
			if(i == 0){
				
				//append the first <operator> <operand> <operand> 
				expression = generateOp() + " " + rand.nextInt(10) + " " + rand.nextInt(10) + " " + expression;
			}
			else{
				//append another  <operand> <operand> <operator> <operator> <Previous Expression>
				expression = generateOp() + " " + generateOp() + " " + rand.nextInt(10) + " " + rand.nextInt(10) + " " + expression;
				i++;
			}
		}
		
		return expression;
	}
	
	/**
	 * Purpose:	Generate a random postfix expression around 3 to 30 characters
	 *
	 *In:	None
	 *Out:	a valid infix prefix or postfix expression
	 *Pre:	None
	 *Post:	an expression is created  and returned
	 * 
	 * @return - a valid postfix expression
	 */
	private static String generatePostfix(){
		//determine size between 0 and 10
		int size = rand.nextInt(10)+1;
		
		String expression = "";
		
		//3 * size is a number from 3 to 30
		//increase i by 3 each time because we add 3 or 4 things each time
		for(int i = 0; i< 3*size; i= i+3){
			if(i == 0){
				expression = expression + rand.nextInt(10) + " " + rand.nextInt(10) + " " + generateOp() + " ";
			}
			else{
				expression = expression + rand.nextInt(10) + " " + rand.nextInt(10) + " " + generateOp() + " " + generateOp() + " ";
				i++;
			}
		}
		return expression.trim();
	}
	
	/**
	 * Purpose:	Generate a random infix expression between 3 and 30 character
	 * 			not including '(' and ')'
	 *
	 *In:	None
	 *Out:	a valid infix prefix or infix expression
	 *Pre:	None
	 *Post:	an expression is created  and returned
	 * 
	 * @return - a valid infix expression
	 */
	private static String generateInfix(){
		//determine size between 0 and 10
		int size = rand.nextInt(10)+1;
		
		String expression = "";
		
		//3 * size is 3 to 30
		for(int i = 0; i< 3*size; i= i+3){
			if(i == 0){
				//append ( <operand> <op> <operand> )
				expression ="( "+  rand.nextInt(10) + " " +  generateOp() + " " + rand.nextInt(10) + " ) ";
			}
			else{
				//append ( <previous expression> <op>  ( <operand> <op> <operand> ) )
				expression = "( " + expression + generateOp() +" ( " +  rand.nextInt(10) + " " +  generateOp() +" " + rand.nextInt(10) + " ) ) ";
				i++;
			}
		}
		return expression.trim();
	}
	
	/**
	 * Purpose:	Generate a random operator
	 * In:non
	 * Out:	 an operator
	 * Pre:None
	 * Post:None
	 */
	public static char generateOp(){
		switch(rand.nextInt(5)){
		case 1:
			return '+';
		case 2:
			return '-';
		case 3:
			return '/';
		case 4: 
			return '*';
		case 0:
			return '^';
		default:
			return '+';
		}
	}
}

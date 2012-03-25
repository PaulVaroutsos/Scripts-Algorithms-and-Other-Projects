/**
 * This class stores an expression in the form of a binary tree.
 * 
 * Private methods:
 * 	createInFixTree(String) - recursively evaluates the string and stores it into the
 * 		BinaryTree
 * 
 * 	createInFixTree(String[]) - Iteratively evaluates the string and stores it into the 
 * 		BinaryTree.
 * 
 * 	findMiddle(String[], BinaryTree, int, int) - Finds the middle operator in an expression 
 * 		so it can be recursively checked by createInFixTree and find the operand for each
 * 		side of the operator
 * 
 * 	isOp(char) - Checks if the character is an operator
 * 
 * Public methods:
 * 	createPostfixTree(String) - Creates a tree if the expression is a valid postfix expression
 * 		and stores it into the BinaryTree
 * 
 * 	createPrefixTree(String) - Creates a tree if the expression is a valid prefix expression
 * 		and stores it into the BinaryTree
 * 
 * 	createInfixTree(string) - Creates a tree if the expression is a valid infix expression
 * 		and stores it into the BinaryTree
 * 
 * 	getExpressionAsTree() - Returns an expression in the form of a binary tree
 * 
 * 	getInfix() - Gets the infix form of the expression saved into the binary tree
 * 
 * 	getPrefix() - Gets the prefix form of the expression saved into the binary tree
 * 
 * 	getPostfix() - Gets the postfix form of the expression saved into the binary tree
 * 
 * 	evaluate() - Evaluates the expression saved into the binary tree
 */
import java.util.Stack;


public class Expression {
	
	//The expression as a binary tree
	BinaryTree expression;
	
	
	/**
	 * Purpose:	Creates a new expression object based on the input string
	 * 			and saves it into a binary tree
	 * 
	 * In:	expression - The expression as a string to save
	 * Out:	None
	 * Pre:	A valid prefix, postfix, or infix expression
	 * Post:None
	 * 
	 * @param expression
	 */
	public Expression(String expression){
		
		//Check to see if it is valid pre, post, or infix
		if( !( PostFix.isPostfix(expression) ||
				PreFix.isPrefix(expression) ||
				InFix.isInfix(expression))  ){
			
			//not valid throw exception
			throw new ExpressionException("Not a valid prefix, postfix, or infix expression.");
			
		//valid
		}else{
			//it is postfix
			if(PostFix.isPostfix(expression)){
				createPostfixTree(expression);
			//it is prefix
			}else if(PreFix.isPrefix(expression)){
				createPrefixTree(expression);
			}
			//it is valid, must be infix
			else{
				createInfixTree(expression);
			}
		}
	}

	/**
	 * Purpose:	This method is called to make a tree out of a postfix expression
	 * In: expr - the expression as a string
	 * Out:	None
	 * Pre:	None
	 * Post:None
	 * 
	 * @param expr  - the expression as a string
	 */
	public void createPostfixTree(String expr) {
		
		//The idea is to continually push onto the stack until you
		//get a operator. at that point you pop the top two items
		//in the stack and make a new binary tree out of the two
		//operands and an operator.  you then put the tree you just
		//made back onto the stack so it can be used as a possible
		//operand.  After you go through the entire array you are done
		//and the only item in the stack is your final expression as
		//a binary tree

		//split the string into an array
		String[] stringArray = expr.split(" ");
		
		
		Stack<BinaryTree> stack = new Stack<BinaryTree>();

		// token counter
		int i = 0;

		//Go through each element in the array
		while (i < stringArray.length) {

			String next = stringArray[i];

			//it is operator 
			//pop operands and put into tree
			if (isOperator(next.charAt(0))) {
				
				BinaryTree temp1 = stack.pop();
				BinaryTree temp2 = stack.pop();
				
				//make a binary tree and put it on the stack.
				stack.push(new BinaryTree(next, temp2, temp1));

			}

			// operand
			else {
				//push it onto the stack to find 2 operands
				stack.push(new BinaryTree(next));
			}
			i++;
		}

		//The stack holds the entire tree, pop it
		expression = stack.pop();
	}

	/**
	 * Purpose:	Returns a binary tree of the expression.
	 * In:	None
	 * Out:	the expression saved in the binary tree
	 * Pre:	None
	 * Post:None
	 * 
	 * @return the expression saved in the binary tree
	 */
	public BinaryTree getExpressionAsTree() {
		return expression;
	}

	/**
	 * Purpose:	Determines if the given character is a operator
	 * In:	c - the character to check
	 * Out:	true if c is an operator
	 * 		false otherwise
	 * Pre: None
	 * Post:None
	 * @param c - the character to check
	 * @return true if c is an operator
	 * 		   false otherwise
	 */
	private boolean isOperator(char c) {
		return c == '*' || c == '^' || c == '/' || c == '+' || c == '-';
	}

	/**
	 * Purpose:	Creates a tree out of the given prefix expression
	 * In: expr - the expression to turn into a tree
	 * Out:None
	 * Pre:None
	 * Post:None
	 * 
	 * @param expr  - the expression to turn into a tree
	 */
	public void createPrefixTree(String expr) {
		
		//This algorith is similar to postfix except we start with
		//array.length -1 and go to 0
		
		//The idea is to continually push onto the stack until you
		//get a operator. at that point you pop the top two items
		//in the stack and make a new binary tree out of the two
		//operands and an operator.  you then put the tree you just
		//made back onto the stack so it can be used as a possible
		//operand.  After you go through the entire array you are done
		//and the only item in the stack is your final expression as
		//a binary tree
		
		

		//split the expression into an array of chars
		String[] stringArray = expr.split(" ");
		
		Stack<BinaryTree> stack = new Stack<BinaryTree>();

		// token counter
		int i = stringArray.length - 1;

		while (i >= 0) {

			String next = stringArray[i];

			// operator
			if (isOperator(next.charAt(0))) {
				
				//push it onto the stack along with the top two stack items
				stack.push(new BinaryTree(next, stack.pop(), stack.pop()));

			}

			// operand
			else {
				//push it onto the stack
				stack.push(new BinaryTree(next));
			}
			
			i--;
		}

		expression = stack.pop();

	}

	/**
	 * Purpose:	This is the public method that gets the infix expression representation
	 * 			of the tree
	 * 			The string will be fully parenthesized
	 * 
	 * In:	None
	 * Out:	The string reperesentation of the tree in infix notation
	 * Pre:	None
	 * Post:None
	 * 	
	 */
	public String getInfix() {
		return getIn(expression, new StringBuffer(""));
	}

	/**
	 * Purpose:	Recursively traverses the tree to get the infix expression
	 * 			of the tree.
	 * 			This is essentially recursive inorder traverse
	 * 
	 * In:	Root - the start location to add leaves
	 * 		treeAsString - the string representation of the expression in infix form
	 * Out:	treeAsString - the string representation of the expression in infix form
	 * Pre:none
	 * Post:none
	 */
	private String getIn(BinaryTree theRoot, StringBuffer treeAsString) {

		//If the left and right children are null, just add the node
		if (theRoot.getLeftChild() == null && theRoot.getRightChild() == null) {
			treeAsString.append(theRoot.toString() + " ");
		}
		//go to the children
		else {
			//append a "("
			treeAsString.append("( ");
			
			// Get leftChild
			if (theRoot.getLeftChild() != null) {
				getIn(theRoot.getLeftChild(), treeAsString);
			}

			// Get node
			treeAsString.append(theRoot.getRoot().toString() + " ");

			// get rightChild
			if (theRoot.getRightChild() != null) {
				getIn(theRoot.getRightChild(), treeAsString);
			}
			//append a ")"
			treeAsString.append(") ");
		}
		
		// return
		return treeAsString.toString();
	}

	/**
	 * Purpose:	Iteratively traverses the tree to get the postfix expression
	 * 			of the tree.
	 * 			This is essentially postorder traverse iteratively
	 * 
	 * In:	none
	 * Out:	treeAsString - the string representation of the expression in postfix form
	 * Pre:none
	 * Post:none
	 */
	public String getPostfix() {
		// Stack that keeps track of where we go
		Stack<BinaryTree> traverseStack = new Stack<BinaryTree>();

		// This is where we want to start
		BinaryTree curr = expression;

		// The string to return
		String treeAsString = "";

		while (curr != null || !traverseStack.isEmpty()) {

			// If the 'node' is empty, there is no object there
			// We need to go up one level
			if (curr == null) {

				// Continue as long as the stack is not empty and the parent
				// node's right
				// reference is also null.
				while (!traverseStack.isEmpty()
						&& curr == traverseStack.peek().getRightChild()) {

					// Pop the stack to get the node above and add it to the
					// string
					curr = traverseStack.pop();
					treeAsString += curr.getRoot().toString() + " ";
				}
				// If the stack is empty, set curr to null. This will
				// end the loop and the method will return. We have
				// traversed the tree.
				if (traverseStack.isEmpty()) {
					curr = null;
				} else {
					// Stack not empty, get the right child.
					curr = traverseStack.peek().getRightChild();
				}
			}
			// The node is not empty, we need to continue going for the
			// Left child and push it onto the stack until we reach the
			// bottom.
			if (curr != null) {

				traverseStack.push(curr);
				curr = curr.getLeftChild();
			}

		}

		return treeAsString;
	}

	/**
	 * Purpose:	Iteratively traverses the tree to get the prefix expression
	 * 			of the tree.
	 * 			This is essentially preorder traverse iteratively
	 * 
	 * In:	none
	 * Out:	treeAsString - the string representation of the expression in prefix form
	 * Pre:none
	 * Post:none
	 */
	public String getPrefix() {
		// Stack that keeps track of where we go
		Stack<BinaryTree> traverseStack = new Stack<BinaryTree>();

		// This is where we want to start
		BinaryTree curr = expression;

		// The string to return
		String treeAsString = "";

		// PREORDER: ROOT > LEFT > RIGHT

		traverseStack.push(curr);

		BinaryTree currentTree;

		//while the stack is not empty
		while (!traverseStack.isEmpty()) {
			
			
			currentTree = traverseStack.pop();

			//get the right
			BinaryTree right = currentTree.getRightChild();

			//if not null, push onto the stack
			if (right != null) {
				traverseStack.push(right);
			}
			
			//do the same for the left
			BinaryTree left = currentTree.getLeftChild();

			if (left != null) {
				traverseStack.push(left);
			}

			//append the root
			treeAsString += currentTree.getRoot().toString() + " ";
		}
		
		
		return treeAsString;
	}

	/**
	 * Purpose:	Evaluate the expression stored in the tree,
	 * In:	None
	 * Out:	The value of the expression
	 * Pre:	None
	 * Post:None
	 * @return
	 */
	public double evaluate() {
		
		//By Default, convert it to finfix.
		//If for some reason it is not valid, return Double.NaN
		if (!InFix.isInfix(this.getInfix())) {
			return Double.NaN;
		}
		
		//Return its value by using hte infix class.
		return InFix.evaluateInfix(this.getInfix());
	}

	/**
	 * Purpose:	Creates a binary tree based on the expression
	 * 
	 * In:	exp - the expression as as string
	 * Out:	None
	 * Pre:	None
	 * Post:None
	 * 
	 * @param exp - the expression as a string
	 */
	private void createInfixTree(String exp) {
		
		String[] expArray = exp.split(" ");

		expression = createInfixTree(expArray, expression, 0,
				expArray.length - 1);

	}

	/**
	 * Purpose:	This method assists the public createInfixTree to create a
	 * 			binary tree out of the given expression
	 * 			Start and end represent the start and end of a sub expression
	 * 
	 * In:	stringArray - the string as an array
	 * 		tree - the location to add leaves to
	 * 		start -	the start location of the string array
	 * 		end - the end location of the string array
	 * Out:	BinaryTree - the expression in the form of a binary tree
	 * Pre:	None
	 * Post:None
	 * 
	 * @param stringArray - the string as an array
	 * @param tree - the location to add leaves to
	 * @param start -	the start location of the string array
	 * @param end - the end location of the string array
	 * @return the expression in the form of a binary tree
	 */
	private BinaryTree createInfixTree(String[] stringArray, BinaryTree tree,
			int start, int end) {

		// Get the middle location
		int middleOpLoc = findMiddle(stringArray, start, end);

		BinaryTree temp = null;

		// Middle location is an operator
		if (middleOpLoc != -1) {

			// create a new operator out of the stringlocation
			temp = new BinaryTree(stringArray[middleOpLoc], null, null);

			//attach the left subtree
			temp.attachLeftSubtree(createInfixTree(stringArray, temp,
					start + 1, middleOpLoc - 1));

			//attach the right subtree
			temp.attachRightSubtree(createInfixTree(stringArray, temp,
					middleOpLoc + 1, end - 1));
		}
		// middleOpLoc returned -1, we have a valid operand
		// This occurs when start == end.  We want to make a binary tree
		//out of this number.  We got a operand, return it as a BT
		else {
			temp = new BinaryTree(stringArray[start], null, null);
		}
		//return the whole tree
		return temp;
	}

	/**
	 * Purpose:	Finds the index of the middle operator of an expression
	 * 			This allows the recursive createInfixTree method to
	 * 			call recursive calls on both sides of an operator to get
	 * 			the operands.
	 * 
	 * 			Returns -2 if there is no valid middle op, the expression is
	 * 			then invalid 
	 * 
	 * 			Returns -1 if the expression is of size 1, i.e. start == end.
	 * 			This tells us that the string is a valid operand if it is not
	 * 			an operator.  return -1
	 * 
	 * In:		stringArray - The expression in the form of a string array
	 * 			start - the beginning of the expression or sub expression
	 * 			end - the end of the expression or sun expression
	 * Out: The index location in the array of the middle operator
	 * 		-2 if there is no middle operator
	 * 		-1 if it is a valid operand
	 * Pre: None
	 * Post None
	 * 
	 * @param stringArray - The expression in the form of a string array
	 * @param start the beginning of the expression or sub expression
	 * @param end the end of the expression or sub expression
	 * @return The index location in the array of the middle operator
	 */
	private int findMiddle(String[] stringArray, int start, int end) {

		// it is valid operand
		if (start == end) {
			if (!isOperator(stringArray[start].charAt(0))) {
				return -1;
			}
		}

		// For every '(' we find, Increase count by one. For every
		// ')' we find, decrease count by one. When the count
		// reaches zero, we have found the index of the middle operator
		// Which is start +1
		int parenCount = 0;
		start++;
		end--;

		// Stop when start and end are the same point
		while (!(start >= end)) {
			// We found '(' increase count and index
			if (stringArray[start].charAt(0) == '(') {
				parenCount++;
			} else if (stringArray[start].charAt(0) == ')') {
				parenCount--;
			}
			// The next position is the operator
			// If start is a number and start+1 is an operator (not a parent)
			// return

			// Parentheses count is 0
			else if (parenCount == 0) {

				// if stringArray[start] is a operator, return its location
				if (isOperator(stringArray[start].charAt(0)))
					return start;

				// if it is a number and the next string in the array is a
				// operand, then return the location of the operand (start +1)
				else if (isOperator(stringArray[start + 1].charAt(0))
						&& !isOperator(stringArray[start].charAt(0))) {
					return start + 1;
				}
			}

			// Increment start location
			start++;
		}

		// Invalid
		return -2;
	}
}

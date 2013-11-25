package trees;
import java.util.Stack;

/**
 * This class represents a BinaryTree.
 * 
 * Fields:
 * 	Object root - The data element in the root of the tree
 * 	BinaryTree leftChild - The left subtree of the Tree
 * 	BinartTree rightChild - The right subtree of the tree
 * 
 *  * Constructors:
 * 	BinaryTree() - Root, leftChild, rightChild are null
 * 	BinaryTree(Object) - Root becomes the parameter, and the left and
 * 		right children are null
 * 	BinaryTree(Object, BinaryTree, BinaryTree) - Root becomes the object,
 * 		left and right trees take on the value of the parameters
 * 
 * Private Methods:
 * 	postorderTraverse(BinaryTree,StringBuffer) - The private method used
 * 		to traverse the tree recursively and returning the result back to
 * 		the public method
 * 	preorderTraverse(BinaryTree, StringBuffer) - The private method used
 * 		to traverse the tree recursively and returning the result back to
 * 		the public method
 * 
 * Public methods:
 * 
 * 	attachLeft(Object) - This method attaches the object to the leftChild
 * 	attachRight(Object) - Attaches the object to the rightChild
 * 	attachLeftSubtree(BinaryTree) - Attaches the entire tree to leftChild
 *  attachRightSubtree(BinaryTree) - Attaches the entire tree to rightChild
 *  detachLeftSubtree() - Removes the entire left tree
 *  detachRightSubtree() - Removes the entire right tree
 * 	getLeftChild() - Returns the leftChild of the tree
 * 	getRightChild() - Returns the rightChild of the tree
 * 	getRoot() - Gets the root, or data item, of the tree
 * 	inorderTraverse() - Traverses the tree inorder
 * 	postorderTraverse() - Traverses the tree postorder
 * 	preorderTraverse() - Traverses the tree preoder
 * 	toString() - Prints the string representation of the root element
 * 
 * @author Paul Varoutsos
 * November 2008
 * 
 * Take Home Midterm
 *
 */

public class BinaryTree {
	
	//Object stored at the root
	private Object root;
	
	//The reference to the left leaf, which could also be a tree
	private BinaryTree leftChild;
	
	//The reference to the right leaf, which could also be a tree
	private BinaryTree rightChild;

	/**
	 *
	 * Purpose:	Default constructor, sets everything to null
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:New BinaryTree object is created
	 */
	public BinaryTree() {
		root = null;
		leftChild = null;
		rightChild = null;
	}

	/**
	 * Purpose:	Constructor, sets this.root to root and the
	 * 			left and right subtree are null
	 * 			This basically makes a leaf with no branches
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:New BinaryTree object is created
	 */
	public BinaryTree(Object root) {
		this.root = root;
		leftChild = null;
		rightChild = null;
		
	}
	
	/**
	 * Purpose:	Constructor, sets this.root to root and the
	 * 			left and right subtree become leftTree and rightTree
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:New BinaryTree object is created
	 */
	public BinaryTree(Object rootItem, BinaryTree leftTree, BinaryTree rightTree) {
		root = rootItem;
		leftChild = leftTree;
		rightChild = rightTree;
	}

	/**
	 * Purpose:	Returns whether or not the tree is empty
	 * In:	None
	 * Out:	True if the tree is empty, false otherwise
	 * Pre:	None
	 * Post:None
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Purpose:	Clears the data from the tree
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:Tree is empty
	 */
	public void makeEmpty() {
		root = null;
		leftChild = null;
		rightChild= null;
	}

	/**
	 * Purpose:	Returns the root of the tree
	 * In:	None
	 * Out:	the root object of the tree, the data item
	 * Pre:	None
	 * Post:None
	 */
	public Object getRoot() throws TreeException {

		if (root != null) {
			return root;
		} else
			throw new TreeException("Tree is empty");
	}
	
	/**
	 * Purpose:	This method sets the root item of the tree
	 * 
	 * In:	rootItem - The item to set as root
	 * Out:	None
	 * Pre:	None
	 * Post:The item is set as the root
	 * 
	 */
	public void setRootItem(Object rootItem){
		root = rootItem;
	}
	
	/**
	 * Purpose:	Retuns the leftChild of the tree
	 * In:	None
	 * Out:	The leftChild
	 * Pre:	None
	 * Post:None
	 */
	public BinaryTree getLeftChild(){
		if(isEmpty()) throw new TreeException("Tree is empty");
		return leftChild;
	}
	
	/**
	 * Purpose:	Returns the rightChild of the tree
	 * In:	None
	 * Out:	The rightChild
	 * Pre:	None
	 * Post:None
	 */
	public BinaryTree getRightChild(){
		if(isEmpty()) throw new TreeException("Tree is empty");
		return rightChild;
	}

	/**
	 * Purpose:	Attaches the item to the leftChild of the tree
	 * In:	newItem - The item to attach to the leftChild
	 * Out:	none
	 * Pre: The tree is not empty and no left tree
	 * Post:The item is added to the left of the tree
	 */
	public void attachLeft(Object newItem) {
		
		//Can only attach if the tree has a root and no right tree
		if (!isEmpty() && getLeftChild() == null) {
			leftChild = new BinaryTree(newItem,null,null);
		}
		else{
			throw new TreeException("Cannot attach left.  Item exists.");
		}

	}

	/**
	 * Purpose:	Attaches the item to the rightChild of the tree
	 * In:	newItem - The item to attach to the rightChild
	 * Out:	none
	 * Pre:	The tree is not empty and no right tree
	 * Post:The item is added to the right of the tree
	 */
	public void attachRight(Object newItem) {
		
		//Can only attach if the tree has a root and no right tree
		if (!isEmpty() && getRightChild() == null) {
			rightChild = new BinaryTree(newItem,null,null);
		}
		else{
			throw new TreeException("Cannot attach left.  Item exists.");
		}
	}

	/**
	 * Purpose:	Attaches the tree to the left of the tree
	 * In:	leftTree - The tree to attach to the leftChild
	 * Out:	none
	 * Pre:	Tree not empty
	 * Post:The tree is added to the left of the tree
	 */
	public void attachLeftSubtree(BinaryTree leftTree) throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot add left tree.  Tree is empty.");
			
		} else if (getLeftChild() != null) {
			throw new TreeException("Left tree alrady exists");
			
		} else {
			leftChild = leftTree;
			//leftTree.makeEmpty();
		}
	}

	/**
	 * Purpose:	Attaches the tree to the right of the tree
	 * In:	rightTree - The tree to attach to the leftChild
	 * Out:	none
	 * Pre:	tree not empty
	 * Post:The tree is added to the right of the tree
	 */
	public void attachRightSubtree(BinaryTree rightTree) throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot add right tree.  Tree is empty.");
			
		} else if (getRightChild() != null) {
			throw new TreeException("Right tree already exists");
			
		} else {
			
			rightChild = rightTree;
			//rightTree.makeEmpty();
		}
	}

	/**
	 * Purpose:	Removes the left tree
	 * In:	None
	 * Out:	The tree that was removed
	 * Pre: tree not empty
	 * Post:The leftTree is removed
	 */
	public BinaryTree detachLeftSubtree() throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot remove left tree.  Tree is empty");
		} else {
			
			BinaryTree leftTree = new BinaryTree(getLeftChild());
			leftChild = null;
			return leftTree;
		}
	}

	/**
	 * Purpose:	Removes the right tree
	 * In:	None
	 * Out:	The tree that was removed
	 * Pre:	tree not empty
	 * Post:The rightTree is removed
	 */
	public BinaryTree detachRightSubTree() throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot remove right tree.  Tree is empty.");
		} else {
			
			BinaryTree rightTree = new BinaryTree(getRightChild());
			rightChild = null;
			return rightTree;
		}
	}
	
	/**
	 * Purpose:	Iteratively traverses the tree inorder
	 * In:	None
	 * Out:	A string representation of the tree traversed inorder
	 * Pre:	None
	 * Post:None
	 */
	public String inOrderTraverse(){

		//Stack that keeps track of where we go
		Stack<BinaryTree> traverseStack = new Stack<BinaryTree>();
		
		//This is where we want to start
		BinaryTree curr = this;
		
		//When true, the string is returned
		Boolean done = false;
		
		//The string to return
		String treeAsString = "";
		
		//INORDER:  LEFT > ROOT > RIGHT

		while(!done){
			if(curr != null){
				
				//We need to get left first push it onto the stack
				traverseStack.push(curr);

				//Getting the left first
				curr = curr.getLeftChild();
			}
			else{
				
				//curr is null.  We checked left.
				if(!traverseStack.isEmpty()){
					
					//pop the stack to get the item
					curr = traverseStack.pop();

					//append the item
					treeAsString += curr.toString() + " ";

					//Check the right
					curr = curr.getRightChild();
				}
				//curr was null, the stack was empty, we visited all
				//of the 'nodes'
				else{
					done = true;
				}
			}
		}

		return treeAsString;
	}
	
	/**
	 * Purpose: Recursively traverses the tree.  This is the public method 
	 * 			of preorderTraverse.  It uses another private method which
	 * 			takes a BinaryTree and a string as a parameter to do the 
	 * 			actual recursion.
	 * In:	None
	 * Out:	A string representation of the tree traversed preorder
	 * Pre:	None
	 * Post:None
	 */
	public String preorderTraverse(){
		
		//the recursive call to the private method
		//StringBuffer is used because it is passed by reference
		//StringBuffer can be changed in recursive calls
		return preorderTraverse(this, new StringBuffer(""));
	}
	
	/**
	 * Purpose: Recursively traverses the tree.  This is the private method 
	 * 			of preorderTraverse.  It traverses the tree recursively and
	 * 			returns a string at each node it travels to and append it.
	 * 			StringBuffer is used so we can append objects to it in
	 * 			recursive calls.  
	 * In:	None
	 * Out:	A string representation of the tree traversed preorder
	 * Pre:	None
	 * Post:None
	 */
	private String preorderTraverse(BinaryTree curr, StringBuffer treeAsString){
		
		//Get node
		treeAsString.append(curr.getRoot().toString() + " ");
		
		//Get leftChild
		if(curr.getLeftChild() != null){
			preorderTraverse(curr.getLeftChild(), treeAsString);
		}
		
		//get rightChild
		if(curr.getRightChild() != null){
			preorderTraverse(curr.getRightChild(), treeAsString);
		}
		
		//return
		return treeAsString.toString();
	}
	
	/**
	 * Purpose: Recursively traverses the tree.  This is the public method 
	 * 			of postorderTraverse.  It uses another private method which
	 * 			takes a BinaryTree and a string as a parameter to do the 
	 * 			actual recursion.
	 * In:	None
	 * Out:	A string representation of the tree traversed postorder
	 * Pre:	None
	 * Post:None
	 */
	public String postorderTraverse(){
		
		//the recursive call to the private method
		//StringBuffer is used because it is passed by reference
		//StringBuffer can be changed in recursive calls
		return postorderTraverse(this, new StringBuffer(""));
	}

	/**
	 * Purpose: Recursively traverses the tree.  This is the private method 
	 * 			of preorderTraverse.  It traverses the tree recursively and
	 * 			returns a string at each node it travels to and appends it.
	 * 			StringBuffer is used so we can append objects to it in
	 * 			recursive calls.  
	 * In:	None
	 * Out:	A string representation of the tree traversed postorder
	 * Pre:	None
	 * Post:None
	 */
	private String postorderTraverse(BinaryTree curr, StringBuffer treeAsString){
		
		//get left
		if(curr.getLeftChild() != null){
			postorderTraverse(curr.getLeftChild(), treeAsString);
		}
		
		//get right
		if(curr.getRightChild() != null){
			postorderTraverse(curr.getRightChild(), treeAsString);
		}
		
		//get item
		treeAsString.append(curr.getRoot().toString() + " ");
		
		//return
		return treeAsString.toString();
	}
	
	/**
	 * Purpose: Returns a string of the node 
	 * In:	None
	 * Out:	A string representation of the item
	 * Pre:	None
	 * Post:None
	 */
	public String toString(){
		if(isEmpty()){
			return "Empty";
		}
		return root.toString();
	}
}

/**
 * This class represents a BinaryTree.
 * This version of the binary tree uses a class called TreeNode which represents
 * the root of the tree and the item stored at that location.
 * 
 * Fields:
 * 	TreeNode root - The data element in the root of the tree
 * 	BinaryTree leftChild - The left subtree of the Tree
 * 	BinartTree rightChild - The right subtree of the tree
 * 
 *  * Constructors:
 * 	BinaryTreeWithNodes() - Root, leftChild, rightChild are null
 * 	BinaryTreeWithNodes(TreeNode) - Root becomes the parameter, and the left and
 * 		right children are null
 * 	BinaryTreeWithNodes(Object, BinaryTree, BinaryTree) - Root becomes the object,
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
 * 	attachLeftSubtree(BinaryTreeWithNodes) - Attaches the entire tree to leftChild
 *  attachRightSubtree(BinaryTreeWithNodes) - Attaches the entire tree to rightChild
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
 */
import java.util.Stack;


public class BinaryTreeWithNodes {
	
	//Object stored as a treenode in the root of the tree
	private TreeNode root;
	
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
	public BinaryTreeWithNodes() {
		root = null;
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
	private BinaryTreeWithNodes(TreeNode newRoot) {
		root = newRoot;
	}

	/**
	 * Purpose:	Constructor, sets this.root to root and the
	 * 			left and right subtree become leftTree and rightTree
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:New BinaryTree object is created
	 */
	public BinaryTreeWithNodes(Object root) {
		this.root = new TreeNode(root, null, null);
	}
	
	/**
	 * Purpose:	Returns whether or not the tree is empty
	 * In:	None
	 * Out:	True if the tree is empty, false otherwise
	 * Pre:	None
	 * Post:None
	 */
	public BinaryTreeWithNodes(Object rootItem, BinaryTreeWithNodes leftTree, BinaryTreeWithNodes rightTree) {
		root = new TreeNode(rootItem);
		attachLeftSubtree(leftTree);
		attachRightSubtree(rightTree);
	}

	/**
	 * Purpose:	Clears the data from the tree
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:Tree is empty
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
			return root.getItem();
		} else
			throw new TreeException("Tree is empty");
	}


	/**
	 * Purpose:	Attaches the item to the leftChild of the tree
	 * In:	newItem - The item to attach to the leftChild
	 * Out:	none
	 * Pre: The tree is not empty and no left tree
	 * Post:The item is added to the left of the tree
	 */
	public void attachLeft(Object newItem) {
		if (!isEmpty() && root.getLeft() == null) {
			root.setLeft(new TreeNode(newItem, null, null));
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
		if (!isEmpty() && root.getRight() == null) {
			root.setRight(new TreeNode(newItem, null, null));
		}
	}

	/**
	 * Purpose:	Attaches the tree to the left of the tree
	 * In:	leftTree - The tree to attach to the leftChild
	 * Out:	none
	 * Pre:	Tree not empty
	 * Post:The tree is added to the left of the tree
	 */
	public void attachLeftSubtree(BinaryTreeWithNodes leftTree) throws TreeException {
		if (isEmpty()) {
			throw new TreeException("Cannot add left tree.  Tree is empty.");
		} else if (root.getLeft() != null) {
			throw new TreeException("Left tree alrady exists");
		} else {
			root.setLeft(leftTree.root);
			leftTree.makeEmpty();
		}
	}

	/**
	 * Purpose:	Attaches the tree to the right of the tree
	 * In:	rightTree - The tree to attach to the leftChild
	 * Out:	none
	 * Pre:	tree not empty
	 * Post:The tree is added to the right of the tree
	 */
	public void attachRightSubtree(BinaryTreeWithNodes rightTree) throws TreeException {
		if (isEmpty()) {
			throw new TreeException("Cannot add right tree.  Tree is empty.");
		} else if (root.getRight() != null) {
			throw new TreeException("Right tree already exists");
		} else {
			root.setRight(rightTree.root);
			rightTree.makeEmpty();
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
			BinaryTree leftTree = new BinaryTree(root.getLeft());
			root.setLeft(null);
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
			BinaryTree rightTree = new BinaryTree(root.getRight());
			root.setRight(null);
			return rightTree;
		}
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
	public void setRootItem(Object newRoot) {

		if (root != null) {
			root.setItem(newRoot);
		} else {
			root = new TreeNode(newRoot, null, null);
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

		Stack<TreeNode> traverseStack = new Stack<TreeNode>();
		TreeNode curr = root;
		Boolean done = false;
		String treeAsString = "";

		while(!done){
			if(curr != null){
				traverseStack.push(curr);

				curr = curr.getLeft();
			}
			else{
				if(!traverseStack.isEmpty()){
					curr = traverseStack.pop();

					treeAsString += curr.toString() + " ";

					curr = curr.getRight();
				}
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
		return preorderTraverse(root, "");
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
	private String preorderTraverse(TreeNode curr, String treeAsString){
		
		treeAsString += curr.getItem().toString();
		
		if(curr.getLeft() != null){
			preorderTraverse(curr.getLeft(), treeAsString);
		}
		
		if(curr.getRight() != null){
			preorderTraverse(curr.getRight(), treeAsString);
		}
		
		return treeAsString;
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
		return postorderTraverse(root, "");
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
	private String postorderTraverse(TreeNode curr, String treeAsString){
		
		
		if(curr.getLeft() != null){
			preorderTraverse(curr.getLeft(), treeAsString);
		}
		
		if(curr.getRight() != null){
			preorderTraverse(curr.getRight(), treeAsString);
		}
		
		treeAsString += curr.getItem().toString();
		
		return treeAsString;
	}
	
	/**
	 * Purpose: Returns a string of the node 
	 * In:	None
	 * Out:	A string representation of the item
	 * Pre:	None
	 * Post:None
	 */
	public String toString(){
		return root.getItem().toString();
	}

}
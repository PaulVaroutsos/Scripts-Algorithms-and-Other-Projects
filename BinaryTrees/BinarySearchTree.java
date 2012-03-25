import java.util.LinkedList;
import java.util.Stack;

/**
 * This class represents a BinarySearchTree.
 * This class contains a root of an object that implements the
 * KeyedItem interface .  This allows the tree to be sorted.  The getKey()
 * method from the KeyedItem interface returns a Comparable object
 * that allows the insert method, delete method, and search methods
 * traverse the tree by comparing nodes and correctly going to the 
 * next logical leaf.
 * 
 * Fields:
 * 	KeyedItem root - The data element in the root of the tree.  The root
 * 					 object implements KeyedItem interface.
 * 	BinarySearchTree leftChild - The left subtree of the Tree
 * 	BinartTree rightChild - The right subtree of the tree
 * 
 *  * Constructors:
 * 		BinarySearchTree() - Root, leftChild, rightChild are null
 * 
 * 		BinarySearchTree(Object) - Root becomes the parameter, and the left and
 * 			right children are null
 * 
 * 		BinarySearchTree(KeyedItem) - Root becomes the parameter, and the left and
 * 			right children are null
 * 
 * 		BinarySearchTree(KeyedItem, BinarySearchTree, BinarySearchTree) - Root becomes the KeyedItem,
 * 			left and right trees take on the value of the parameters
 * 
 * Private Methods:
 * 		postorderTraverse(BinarySearchTree,StringBuffer) - The private method used
 * 			to traverse the tree recursively and returning the result back to
 * 			the public method
 * 
 * 		preorderTraverse(BinarySearchTree, StringBuffer) - The private method used
 * 			to traverse the tree recursively and returning the result back to
 * 			the public method
 * 
 * 		insert(BinarySearchTree,KeyedItem) - Used by the public method insert(KeyedItem
 * 			and traversed the tree until it finds the appropriate location to insert
 * 			the new item
 * 
 * 		descendingOrder - Recursively traversed the tree in reverse order and creates a
 * 			linked list of the items in the order it traverses the tree
 * 			then returns the list back to the public method.
 * 
 * 		retrieve(BinarySearchTree, Comparable) - Recursively traverses the tree searching
 * 			for the item given by the parameter and returns it back to the public method
 * 			if it is found.  Throws a TreeException if not found.
 * 
 * 		deleteTree(BinarySearchTree) - Deletes the item in the node referenced by the
 * 			parameter.  This method is used by the publoc method deleteItem()
 * 
 * 		findLeftMost(BinarySearchTree) - Deletes the node that is the leftmost.  This
 * 			allows us to replace a tree that we want to delete by another tree and still
 * 			have a correctly formated tree.  This returns the item that was in the node
 * 
 *		deleteLeftMost(BinarySearchTree) - This deletes the item that we are replacing
 *			with a node what we want to delete.  This deletes the node that is the 
 *			leftmost descendant of the tree rooted at treeNode.  THis returns the
 *			subtree of the deleted node
 * 
 * Public methods:
 * 
 * 	BinarySearchTree specific methods:
 * 		insert(KeyedItem) - Inserts the KeyedItem into the binary tree based on its 
 * 			key value.  When inorder traversed, the tree will be traveled in increasing
 * 			order.
 * 
 * 		retrieve(Comparable) - Returns the item that contains the key given by the parameter
 * 			throws an exception if the item is not found.
 * 
 * 		delete(Comparable) - THis deletes the item in the tree that has the key given
 * 			by the parameter
 * 		
 * 		descendingOrder() - Traverses the tree in descending order.  This is almost
 * 			like a reverse inorder traverse.  The items will be traveled in descending
 * 			order.  This method returns a Linked List of the items in descending order
 * 
 * 		ascendingOrder() - Traverses the tree in increasing order.  This is a basic
 * 			inorder traverse of the tree.  This method returns a Linked List of items
 * 			in increasing order
 * 
 * 		deleteItem(Key) - Deletes an item from the tree that has the specified key. 
 * 			Also returns the root node of the resulting tree
 * 
 * 	BinaryTree similar methods:
 * 		attachLeft(KeyedItem) - This method attaches the KeyedItem to the leftChild
 * 		attachRight(KeyedItem) - Attaches the KeyedItem to the rightChild
 * 		attachLeftSubtree(BinarySearchTree) - Attaches the entire tree to leftChild
 *  	attachRightSubtree(BinarySearchTree) - Attaches the entire tree to rightChild
 *  	detachLeftSubtree() - Removes the entire left tree
 *  	detachRightSubtree() - Removes the entire right tree
 * 		getLeftChild() - Returns the leftChild of the tree
 * 		getRightChild() - Returns the rightChild of the tree
 * 		getRoot() - Gets the root, or data item, of the tree
 * 		inorderTraverse() - Traverses the tree inorder
 * 		postorderTraverse() - Traverses the tree postorder
 * 		preorderTraverse() - Traewrses the tree preoder
 * 		toString() - Prints the string representation of the root element
 * 
 * @author Paul Varoutsos
 * November 2008
 *
 */

public class BinarySearchTree {
	
	//item stored at the root
	private KeyedItem root;
	
	//The reference to the left leaf, which could also be a tree
	private BinarySearchTree leftChild;
	
	//The reference to the right leaf, which could also be a tree
	private BinarySearchTree rightChild;

	/**
	 * Purpose:	Default constructor, sets everything to null
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:New BinarySearchTree object is created
	 */
	public BinarySearchTree() {
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
	 * Post:New BinarySearchTree object is created
	 */
	public BinarySearchTree(KeyedItem root) {
		this.root = root;
		leftChild = null;
		rightChild = null;
		
	}
	
	/**
	 * Similar to the above constructor
	 * @param root - the item to set as root
	 */
	public BinarySearchTree(Object root){
		this.root = (KeyedItem)root;
		leftChild = null;
		rightChild = null;
	}
	
	/**
	 * Purpose:	Constructor, sets this.root to root and the
	 * 			left and right subtree become leftTree and rightTree
	 * In:	None
	 * Out:	None
	 * Pre:	None
	 * Post:New BinarySearchTree object is created
	 */
	public BinarySearchTree(KeyedItem rootItem, BinarySearchTree leftTree, BinarySearchTree rightTree) {
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
	public KeyedItem getRoot() throws TreeException {

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
	public void setRootItem(KeyedItem rootItem){
		root = rootItem;
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
		root = (KeyedItem)rootItem;
	}

	
	/**
	 * Purpose:	Returns the leftChild of the tree
	 * In:	None
	 * Out:	The leftChild
	 * Pre:	None
	 * Post:None
	 */
	public BinarySearchTree getLeftChild(){
		return leftChild;
	}
	
	/**
	 * Purpose:	Returns the rightChild of the tree
	 * In:	None
	 * Out:	The rightChild
	 * Pre:	None
	 * Post:None
	 */
	public BinarySearchTree getRightChild(){
		return rightChild;
	}

	/**
	 * Purpose:	Attaches the item to the leftChild of the tree
	 * In:	newItem - The item to attach to the leftChild
	 * Out:	none
	 * Pre: The tree is not empty and no left tree
	 * Post:The item is added to the left of the tree
	 */
	public void attachLeft(KeyedItem newItem) {
		
		//Can only attach if the tree has a root and no right tree
		if (!isEmpty() && getLeftChild() == null) {
			leftChild = new BinarySearchTree(newItem,null,null);
		}
		else{
			throw new TreeException("Cannot atttach left.  Item exists.");
		}

	}

	/**
	 * Purpose:	Attaches the item to the rightChild of the tree
	 * In:	newItem - The item to attach to the rightChild
	 * Out:	none
	 * Pre:	The tree is not empty and no right tree
	 * Post:The item is added to the right of the tree
	 */
	public void attachRight(KeyedItem newItem) {
		
		//Can only attach if the tree has a root and no right tree
		if (!isEmpty() && getRightChild() == null) {
			rightChild = new BinarySearchTree(newItem,null,null);
		}
		else{
			throw new TreeException("Cannot atttach right.  Item exists.");
		}
	}

	/**
	 * Purpose:	Attaches the tree to the left of the tree
	 * In:	leftTree - The tree to attach to the leftChild
	 * Out:	none
	 * Pre:	Tree not empty
	 * Post:The tree is added to the left of the tree
	 */
	protected void attachLeftSubtree(BinarySearchTree leftTree) throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot add left tree.  Tree is empty.");
			
		} else if (getLeftChild() != null) {
			throw new TreeException("Left tree alrady exists");

		} else {
			leftChild = leftTree;
		}
	}

	/**
	 * Purpose:	Attaches the tree to the right of the tree
	 * In:	rightTree - The tree to attach to the leftChild
	 * Out:	none
	 * Pre:	Tree not empty
	 * Post:The tree is added to the right of the tree
	 */
	protected void attachRightSubtree(BinarySearchTree rightTree) throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot add right tree.  Tree is empty.");
			
		} else if (getRightChild() != null) {
			throw new TreeException("Right tree already exists");
			
		} else {
			rightChild = rightTree;
		}
	}

	/**
	 * Purpose:	Removes the left tree
	 * In:	None
	 * Out:	The tree that was removed
	 * Pre:	Tree not empty
	 * Post:The leftTree is removed
	 */
	public BinarySearchTree detachLeftSubtree() throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot remove left tree.  Tree is empty");
		} else {
			
			BinarySearchTree leftTree = new BinarySearchTree(getLeftChild());
			leftChild = null;
			return leftTree;
		}
	}

	/**
	 * Purpose:	Removes the right tree
	 * In:	None
	 * Out:	The tree that was removed
	 * Pre:	Tree not empty
	 * Post:The rightTree is removed
	 */
	public BinarySearchTree detachRightSubTree() throws TreeException {
		
		if (isEmpty()) {
			throw new TreeException("Cannot remove right tree.  Tree is empty.");
		} else {
			
			BinarySearchTree rightTree = new BinarySearchTree(getRightChild());
			rightTree = null;
			return rightTree;
		}
	}
	
	/**
	 * Purpose:	Iteratively traverses the tree
	 * In:	None
	 * Out:	A string representation of the tree traversed inorder
	 * Pre:	None
	 * Post:None
	 */
	public String inOrderTraverse(){

		//Stack that keeps track of where we go
		Stack<BinarySearchTree> traverseStack = new Stack<BinarySearchTree>();
		
		//This is where we want to start
		BinarySearchTree curr = this;
		
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
	 * 			takes a BinarySearchTree and a string as a parameter to do the 
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
	private String preorderTraverse(BinarySearchTree curr, StringBuffer treeAsString){
		
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
	 * 			takes a BinarySearchTree and a string as a parameter to do the 
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
	private String postorderTraverse(BinarySearchTree curr, StringBuffer treeAsString){
		
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
		return root.toString();
	}
	
	/** 
	 * Purpose:	Inserts an item into the binary search tree based on the
	 * 			items key value.  The tree will be sorted if traversed
	 * 			inorder
	 * In:	KeyedItem - An object that implements KeyedItem.
	 * Out:	None
	 * Pre:	Item implements KeyedItem, so it can be compared
	 * Post:None
	 * 
	 * @param o - The item to add to the tree
	 */
	public void insert(KeyedItem o){
		insert(this,o);
	}
	
	
	/**
	 * Purpose:	This method helps the public insert method to recursivly traverse the tree
	 * 			and find the appropriate place to insert the item into the tree.
	 * 
	 * In:	currentNode - The start of the binarytree to begin searching for the appropriate
	 * 					  location to add
	 * 		newItem - the new item to add
	 * Out: the item that was added
	 * Pre:	None
	 * Post:None
	 *
	 * @param currentNode - The current node to begin searching for
	 * @param newItem - the new item to add
	 * @return The item that was added
	 */
	private BinarySearchTree insert(BinarySearchTree currentNode, KeyedItem newItem){
		
		//create the object to return
		BinarySearchTree item = null;
		
		
		try {
			
			//If curretnNode is null, just add the item
			if(currentNode == null){
				item = new BinarySearchTree(newItem,null,null);
				currentNode = item;
			}
			
			//this > newItem
			else if( newItem.getKey().compareTo( currentNode.getRoot().getKey() ) < 0)
			{
				if (currentNode.getLeftChild() == null )
				{
					currentNode.attachLeftSubtree(new BinarySearchTree(newItem,null,null));
				}
				else
				{
					insert(currentNode.getLeftChild(), newItem);
				}
			}
			
			//this < newItem
			else
			{
				if (currentNode.getRightChild() == null )
				{
					currentNode.attachRightSubtree(new BinarySearchTree(newItem,null,null));
				}
				else
				{
					insert(currentNode.getRightChild(), newItem);
				}
			}
			
		//this exception will be thrown when we try to insert in a
		//blank tree.  We wont be able to call getRoot() on a null
		//reference
		} catch (TreeException e) {
			currentNode.setRootItem(newItem);
			return this;
		}
		
		return item;
	}
	
	/**
	 * Purpose:	Creates a LinkedList of the items in descending order
	 * In:	None
	 * Out:	A linked list of the tree items in descending order
	 * Pre:	None
	 * Post:None
	 * 
	 * @return - A linked list of the tree items in descending order
	 */
	public LinkedList descendingOrder(){
		
		LinkedList treeAsList = new LinkedList();
		return descending(this, treeAsList);
	}
	
	/**
	 * Purpose:	Creates a LinkedList of the items in descending order.
	 * 			This is the private method that assists the public method
	 * 			to recursibly traverse the tree and add items to the list.
	 * In:	curr - the current node in thre tree where we are at
	 * 		treeAsList - the current list of items
	 * Out:	A linked list of the tree items in descending order
	 * Pre:	None
	 * Post:None
	 * 
	 * @return - A linked list of the tree items in descending order
	 */
	private LinkedList descending(BinarySearchTree curr, LinkedList treeAsList){
		
		//Has no leaves, just 1 item
		if(curr.getLeftChild() == null && curr.getRightChild() == null){
			treeAsList.add(curr);
		}
		else{
			//Get leftChild
			if(curr.getRightChild() != null){
				descending(curr.getRightChild(), treeAsList);
			}
		
			//Get node
			treeAsList.add(curr.getRoot());
		
			//get rightChild
			if(curr.getLeftChild() != null){
				descending(curr.getLeftChild(), treeAsList);
			}
		}
		
		//return
		return treeAsList;
	}
	
	/**
	 * Purpose:	Creates a LinkedList of the items in increasing order.
	 * 			This is a basic inorder traverse
	 * 			
	 * In:	None
	 * Out:	A linked list of the tree items in increasing order
	 * Pre:	None
	 * Post:None
	 * 
	 * @return - A linked list of the tree items in  increasing order
	 */
	public LinkedList ascendingOrder(){

		//Stack that keeps track of where we go
		Stack<BinarySearchTree> traverseStack = new Stack<BinarySearchTree>();
		
		//This is where we want to start
		BinarySearchTree curr = this;
		
		//When true, the string is returned
		Boolean done = false;
		
		//The string to return
		LinkedList treeAsList = new LinkedList();
		
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
					treeAsList.add(curr);

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

		return treeAsList;
	}
	
	/**
	 * Purpose:	This is the public method used to retrieve an item with the
	 * 			specified key.  Throws TreeException if it is not found
	 * 
	 * In:  key - The key to search for
	 * Out: The item with the specified key
	 * Pre:	None
	 * Post:None
	 * 
	 * @param key - The key to search for
	 * @returnThe item with the specified key
	 */
	public KeyedItem retrieve(Comparable key){
		return retrieve(this,key);
	}
	
	/**
	 * Purpose:	This is the private method used to retrieve an item with the
	 * 			specified key.  This method assists the public method in 
	 * 			recursivly traversing the tree to find the item with the
	 * 			specified key
	 * 
	 * In:  key - The key to search for
	 * 		currentNode - The current node location at which we are in the tree
	 * Out: The item with the specified key
	 * Pre:	None
	 * Post:None
	 * 
	 * @param key - The key to search for
	 * @returnThe item with the specified key
	 */
	private KeyedItem retrieve(BinarySearchTree currentNode, Comparable key){
		
		//item to return if found
		KeyedItem item = null;
		
		//Node is null, we are at a empty leaf.  Item not found
		if(currentNode == null){
			throw new TreeException("Key not found.  Tree is empty.");
		}
		//Check right tree if key is greater
		else if(currentNode.getRoot().getKey().compareTo(key) < 0){
			item = retrieve(currentNode.getRightChild(), key);
		}
		//Check left tree if key is less than
		else if(currentNode.getRoot().getKey().compareTo(key) > 0){
			item = retrieve(currentNode.getLeftChild(), key);
		}
		//Key is equal, found the item
		else if(currentNode.getRoot().getKey().compareTo(key) == 0){
			item = currentNode.getRoot();
		}
		
		return item;
	}
	
	/**
	 * Purpose:	Delete the item in the tree with the specified key.
	 * 			This method uses the deleteNode method to assist it
	 * 			in deleting the node
	 * 
	 * In:	key - key of the item to delete
	 * Out:	The root node of the resulting tree
	 * Pre:	Root is not null
	 * Post:None
	 */
	public BinarySearchTree deleteItem(BinarySearchTree curr, Comparable key) throws TreeException{
		
		BinarySearchTree temp = null;
		
		//Tree is empty, no item to delete
		if(root == null){
			throw new TreeException("Tree is empty.  Cannot delete key.");
		}
		//The key is equal to the root item. we need to delete it
		else if(root.getKey().compareTo(key) == 0){
			temp = deleteTree(this,key);
			return temp;
		}
		//The key is less than the root item,  recursive call til we find it
		else if(root.getKey().compareTo(key) > 0){
			temp = deleteItem(curr.getLeftChild(),key);
			curr.attachLeftSubtree(temp);
			return temp;
		}
		//The key is grater than the root item,  recursive call til we find it
		else{
			temp = deleteItem(curr.getRightChild(),key);
			curr.attachRightSubtree(curr);
			return temp;
		}
	}
	
	/**
	 * Purpose:	Deletes the item in the BST referenced by tree.
	 * 
	 * In:	tree - BST to start the search to delete the item
	 * 		key - the key to search for and delete
	 * Out:	The root node of the resulting tree
	 * Pre:	none
	 * Post:the item in the tree is replaced
	 * @return
	 */
	private BinarySearchTree deleteTree(BinarySearchTree tree, Comparable key){
		
		//If it is a leaf
		if(tree.getLeftChild() == null && tree.getRightChild() == null){
			return null;
		}
		//Tree has only 1 leaf
		else if( (tree.getLeftChild() == null && tree.getRightChild() != null) ||
				 (tree.getLeftChild() != null && tree.getRightChild() == null)){
			
			//Make a new tree out of the leaf and make it the new root
			if(tree.getLeftChild() != null){
				return tree.getLeftChild();
			}
			else{
				return tree.getRightChild();
			}
		}
		//Has two leaves.  This case is slightly more complicated
		else{
			//get the leftmost item in the right child subtree.  This becomes the 
			//new root.  This allows the BinarySearchTree to stay a valid 
			//BinarySearchTree
			KeyedItem replacementItem = findLeftMost(tree.getRightChild());
			
			//Delete the tree with that item so it can be moved to the new root
			BinarySearchTree replacementTree = deleteLeftMost(tree.getRightChild());
			
			//change the root to the new item
			tree.setRootItem(replacementItem);
			
			//Set the new roots right tree to the replacement tree
			tree.attachRightSubtree(replacementTree);
			return tree;
		}
	}
	
	/**
	 * Purpose:	FInds the item that is in the leftmost descendant of the tree rooted at tree
	 * 			This method is used by deleteTree.
	 * 
	 * In:	tree - The tree to start at and find the left most item
	 * Out:	the item at the leftmost leaf of tree
	 * Pre:	none
	 * Post:none
	 */
	private KeyedItem findLeftMost(BinarySearchTree tree){
		
		//return the root when we find that we reached the leftmost child
		if(tree.getLeftChild() == null){
			return tree.getRoot();
		}
		else{
			//keep going. has more left children
			return findLeftMost(tree.getLeftChild());
		}
	}
	
	/**
	 * Purpose:	Deletes the node that is the leftmost descendent of the
	 *  		tree rooted at tree.
	 *  
	 *  In:		tree - the root to begin at
	 *  Out:	the subtree of the deleted node
	 *  Pre:	None
	 *  Post:	none
	 * @param tree
	 * @return
	 */
	private BinarySearchTree deleteLeftMost(BinarySearchTree tree){
		
		if(tree.getLeftChild() == null){
			//this is the node we want.  No left child
			//Right child might exist
			
			return tree.getRightChild();
		}
		else{
			BinarySearchTree replacement = deleteLeftMost(tree.getLeftChild());
			tree.attachRightSubtree(replacement);
			return tree;
		}
	}
	
}
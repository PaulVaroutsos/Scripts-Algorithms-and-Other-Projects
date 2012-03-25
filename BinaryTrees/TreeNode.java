/**
 * This class represents a tree node object.  It is the root of a tree.
 * It contains an Object that is stored in the node and two references.
 * One to the left tree and one to the right tree.
 * 
 * @author Paul Varoutsos
 * November 2008
 *
 */
public class TreeNode {
	
	//Object stored in the node
	private Object item;
	
	//reference to the left tree
	private TreeNode left;
	
	//reference to the right tree.
	private TreeNode right;
	
	/**
	 * Constructor that sets the parameter newItem to the root
	 * 
	 * In:	newItem - the item to add to the root
	 * Out: a tree node object
	 * Pre:	None
	 * Post:A new TreeNode is created
	 * @param newItem - the item to add to the root
	 */
	public TreeNode(Object newItem){
		item = newItem;
		left = null;
		right = null;
	}
	
	/**
	 * Constructor
	 * This sets the root equal to newItem and the left tree reference to left
	 * and the right tree reference to right.
	 * 
	 * In:	newItem - The item to set as the root
	 * 		left - Thre tree to add to the left reference
	 * 		right - thre tree to add to the right reference
	 * Out:	A new tree node object
	 * Pre:	None
	 * Post:a new tree node object is created
	 * @param newItem - The item to set as the root
	 * @param left - Thre tree to add to the left reference
	 * @param right - thre tree to add to the right reference
	 */
	public TreeNode(Object newItem, TreeNode left, TreeNode right){
		item = newItem;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Purpose:	This gets the data item that is stored in the node
	 * 
	 * In:	None
	 * Out:	the root, or data item stored in the node
	 * Pre:	None
	 * Post:None
	 * @return the root
	 */
	public Object getItem(){
		return item;
	}
	
	/**
	 * Purpose:	This changes the root item to the parameter newItem
	 * 
	 * In:	newItem - The object to change the root reference to
	 * Out:	None
	 * Pre:	none
	 * Post:The root item becomes newItem
	 * 
	 * @param newItem the item to set the root to
	 */
	public void setItem(Object newItem){
		item = newItem;
	}
	
	/**
	 * Purpose:	Gets the reference to the left tree
	 * 
	 * In:	None
	 * Out:	The left reference of the node
	 * Pre:	None
	 * Post:None
	 * @return The left reference of the node
	 */
	public TreeNode getLeft(){
		return left;
	}
	
	/**
	 * Purpose:	Gets the reference to the right tree
	 * 
	 * In:	None
	 * Out:	The right reference of the node
	 * Pre:	None
	 * Post:None
	 * @return The right reference of the node
	 */
	public TreeNode getRight(){
		return right;
	}
	
	/**
	 * Purpose:	Sets the reference to the left tree
	 * 
	 * In:	newLeft - the tree that the left reference will point to
	 * Out:	none
	 * Pre:	None
	 * Post:None
	 * @param newLeft - the tree that the left reference will point to
	 */
	public void setLeft(TreeNode newLeft){
		left = newLeft;
	}
	
	/**
	 * Purpose:	Sets the reference to the right tree
	 * 
	 * In:	newLeft - the tree that the left reference will point to
	 * Out:	none
	 * Pre:	None
	 * Post:None
	 * @param newLeft - the tree that the left reference will point to
	 */
	public void setRight(TreeNode newRight){
		right = newRight;
	}
	
	/**
	 * Returns a string representation of the node
	 */
	public String toString(){
		return item.toString();
	}
}